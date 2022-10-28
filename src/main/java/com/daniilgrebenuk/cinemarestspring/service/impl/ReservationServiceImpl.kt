package com.daniilgrebenuk.cinemarestspring.service.impl

import com.daniilgrebenuk.cinemarestspring.dtos.OrderDto
import com.daniilgrebenuk.cinemarestspring.dtos.SeatDto
import com.daniilgrebenuk.cinemarestspring.exception.InvalidOrderException
import com.daniilgrebenuk.cinemarestspring.model.Order
import com.daniilgrebenuk.cinemarestspring.model.Schedule
import com.daniilgrebenuk.cinemarestspring.model.Ticket
import com.daniilgrebenuk.cinemarestspring.model.TicketType
import com.daniilgrebenuk.cinemarestspring.repository.*
import com.daniilgrebenuk.cinemarestspring.service.ReservationService
import com.daniilgrebenuk.cinemarestspring.util.DtoConverter
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random.Default.nextBytes
import kotlin.random.Random.Default.nextLong

@Service
class ReservationServiceImpl(
    private val scheduleRepository: ScheduleRepository,
    private val ticketRepository: TicketRepository,
    private val ticketTypeRepository: TicketTypeRepository,
    private val seatRepository: SeatRepository,
    private val orderRepository: OrderRepository,
    private val dtoConverter: DtoConverter
) : ReservationService {

    override fun reserveTickets(orderDto: OrderDto) {
        verifyTime(orderDto)
        val schedule = findScheduleByOrderDto(orderDto)
        val availableSeats = seatRepository.findAllAvailableSeatsByIdSchedule(schedule.idSchedule)
        val orderedSeats = orderDto.tickets.map { it.seat }
        verifySeats(seatsToReserve = orderedSeats, availableSeats = availableSeats.map { dtoConverter.seatDtoFromSeat(it) })
        val ticketTypes = findAllTicketTypesAndVerifyTicketTypeInOrder(orderDto)

        val order = createOrderByOrderDtoAndSchedule(orderDto, schedule)
        val encoder = Base64.getEncoder()
        orderDto.tickets.forEach { ticketDto ->
            val ticket = Ticket().apply {
                this.seat = availableSeats.first { it.seatRow == ticketDto.seat.seatRow && it.seatNumber == ticketDto.seat.seatNumber }
                this.ticketType = ticketTypes.first { it.type == ticketDto.ticketType }
                this.order = order
                this.uniqueCode = encoder.encodeToString(nextBytes(15)).take(30)
            }
            ticketRepository.save(ticket)
        }
    }

    private fun createOrderByOrderDtoAndSchedule(orderDto: OrderDto, schedule: Schedule): Order {
        return orderRepository.save(Order().apply {
            this.schedule = schedule
            this.customerName = orderDto.customerName
            this.customerSurname = orderDto.customerSurname
        })
    }

    private fun verifyTime(orderDto: OrderDto) {
        if (orderDto.dateAndTime < LocalDateTime.now().plusMinutes(15)){
            throw InvalidOrderException("Tickets can only be purchased 15 minutes before the start!")
        }
    }

    private fun findAllTicketTypesAndVerifyTicketTypeInOrder(orderDto: OrderDto): List<TicketType> {
        val ticketTypeErrors = ArrayList<String>()
        return ticketTypeRepository.findAll().also { ticketTypes ->
            orderDto.tickets.map { it.ticketType }.forEach { ticketType ->
                if (ticketTypes.none { it.type == ticketType }) {
                    ticketTypeErrors += ticketType
                }
            }
            if (ticketTypeErrors.size != 0) {
                throw InvalidOrderException("Invalid ticket types specified: $ticketTypeErrors")
            }
        }
    }

    private fun findScheduleByOrderDto(orderDto: OrderDto): Schedule {
        return scheduleRepository.findScheduleByMovie_TitleAndHall_NameAndTime(
            orderDto.movieTitle,
            orderDto.hallName,
            orderDto.dateAndTime
        ).orElseThrow { InvalidOrderException("There is no schedule suitable for this order!") }
    }

    private fun verifySeats(seatsToReserve: List<SeatDto>, availableSeats: List<SeatDto>) {
        verifyThatAvailableSeatsContainsSeatsToReserve(seatsToReserve, availableSeats)

        val listForVerifyEmptySeatsAfterReservation = availableSeats.filter { it !in seatsToReserve }
        if (hasEmptySeatBetweenTwoReserved(listForVerifyEmptySeatsAfterReservation)) {
            throw InvalidOrderException("You cannot leave an available seat between two reserved seats!")
        }
    }

    private fun verifyThatAvailableSeatsContainsSeatsToReserve(seatsToReserve: List<SeatDto>, availableSeats: List<SeatDto>) {
        val errorSeats = ArrayList<SeatDto>()
        seatsToReserve.forEach {
            if (it !in availableSeats)
                errorSeats += it
        }
        if (errorSeats.size != 0) {
            throw InvalidOrderException("Input seats are already reserved: $errorSeats")
        }
    }

    private fun hasEmptySeatBetweenTwoReserved(seats: List<SeatDto>): Boolean {
        seats
            .sortedWith(compareBy(SeatDto::seatRow, SeatDto::seatNumber))
            .take(seats.size - 1)
            .forEachIndexed { index, seat ->
                if (seat.seatRow == seats[index + 1].seatRow && seat.seatNumber + 2 == seats[index + 1].seatNumber) {
                    return true
                }
            }
        return false
    }

    /*private fun findTicketTypeByOrderDto(orderDto: OrderDto): TicketType {
        return ticketTypeRepository.findTicketTypeByType(orderDto.ticketType)
            .orElseThrow { InvalidOrderException("Invalid ticket type specified: ${orderDto.ticketType}") }
    }*/

    /*private fun findSeatsByOrderDto(orderDto: OrderDto): List<Seat> {
        return seatRepository.findAllByHallName(orderDto.hallName).also {
            if (it.isEmpty()) {
                throw InvalidOrderException("Invalid hall name specified: ${orderDto.hallName}")
            }
        }
    }*/
}
