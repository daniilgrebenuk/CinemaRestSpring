package com.daniilgrebenuk.cinemarestspring.service.impl

import com.daniilgrebenuk.cinemarestspring.dtos.ConfirmationDto
import com.daniilgrebenuk.cinemarestspring.dtos.ReservationDto
import com.daniilgrebenuk.cinemarestspring.dtos.SeatDto
import com.daniilgrebenuk.cinemarestspring.exception.InvalidReservationException
import com.daniilgrebenuk.cinemarestspring.model.*
import com.daniilgrebenuk.cinemarestspring.repository.*
import com.daniilgrebenuk.cinemarestspring.service.ReservationService
import com.daniilgrebenuk.cinemarestspring.util.DtoConverter
import com.daniilgrebenuk.cinemarestspring.util.GlobalConstants
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReservationServiceImpl(
    private val scheduleRepository: ScheduleRepository,
    private val ticketRepository: TicketRepository,
    private val ticketTypeRepository: TicketTypeRepository,
    private val seatRepository: SeatRepository,
    private val reservationRepository: ReservationRepository,
    private val dtoConverter: DtoConverter,
) : ReservationService {

    override fun reserveTickets(reservationDto: ReservationDto): ConfirmationDto {
        verifyTicketsSize(reservationDto)

        val schedule = findScheduleByReservationDtoAndVerifyTime(reservationDto)

        val availableSeats = seatRepository.findAllAvailableSeatsByIdSchedule(schedule.idSchedule)
        val reservedSeats = reservationDto.tickets.map { it.seat }.toSet()
        verifySeats(
            seatsToReserve = reservedSeats,
            availableSeats = availableSeats.map { dtoConverter.seatDtoFromSeat(it) }.toSet(),
            allHallSeats = findSeatsByHall(schedule.hall).map { dtoConverter.seatDtoFromSeat(it) }.toSet()
        )
        val ticketTypes = findAllTicketTypesAndVerifyInReservation(reservationDto)

        val reservation = createReservationByReservationDtoAndSchedule(reservationDto, schedule)
        var totalPrice = 0.0
        reservationDto.tickets.forEach { ticketDto ->
            ticketRepository.save(
                Ticket().apply {
                    this.seat = availableSeats.first { it.seatRow == ticketDto.seat.seatRow && it.seatNumber == ticketDto.seat.seatNumber }
                    this.ticketType = ticketTypes.first { it.type == ticketDto.ticketType }.also { totalPrice += it.price }
                    this.reservation = reservation
                }
            )
        }
        return ConfirmationDto(totalPrice, calculateExpirationDate(schedule))
    }

    private fun calculateExpirationDate(schedule: Schedule): LocalDateTime {
        val endOfReservation = schedule.time.minusMinutes(15)

        return LocalDateTime
            .now()
            .plusHours(GlobalConstants.EXPIRATION_TIME_IN_HOURS)
            .let {
                if (it < endOfReservation)
                    it
                else
                    endOfReservation
            }
    }

    private fun verifyTicketsSize(reservationDto: ReservationDto) {
        if (reservationDto.tickets.size == 0) {
            throw InvalidReservationException("You must select at least one seat!")
        }
    }

    private fun createReservationByReservationDtoAndSchedule(reservationDto: ReservationDto, schedule: Schedule): Reservation {
        return reservationRepository.save(Reservation().apply {
            this.schedule = schedule
            this.customerName = reservationDto.customerName
            this.customerSurname = reservationDto.customerSurname
        })
    }

    private fun verifyTime(schedule: Schedule) {
        if (schedule.time < LocalDateTime.now().plusMinutes(15)) {
            throw InvalidReservationException("Tickets can only be purchased 15 minutes before the start!")
        }
    }

    private fun findAllTicketTypesAndVerifyInReservation(reservationDto: ReservationDto): List<TicketType> {
        val ticketTypeErrors = ArrayList<String>()
        return ticketTypeRepository.findAll().also { ticketTypes ->
            reservationDto.tickets.map { it.ticketType }.forEach { ticketType ->
                if (ticketTypes.none { it.type == ticketType }) {
                    ticketTypeErrors += ticketType
                }
            }
            if (ticketTypeErrors.size != 0) {
                throw InvalidReservationException("Invalid ticket types specified: $ticketTypeErrors")
            }
        }
    }

    private fun findScheduleByReservationDtoAndVerifyTime(reservationDto: ReservationDto): Schedule {
        return scheduleRepository.findById(reservationDto.idSchedule)
            .orElseThrow { InvalidReservationException("There is no schedule suitable for this reservation!") }
            .also { verifyTime(it) }
    }

    private fun verifySeats(seatsToReserve: Set<SeatDto>, availableSeats: Set<SeatDto>, allHallSeats: Set<SeatDto>) {
        verifyThatAvailableSeatsContainsSeatsToReserve(seatsToReserve, availableSeats)

        val availableAfterPurchase = availableSeats - seatsToReserve
        if (hasEmptySeatBetweenTwoReserved((allHallSeats - availableAfterPurchase).toList())) {
            throw InvalidReservationException("You cannot leave an available seat between two reserved seats!")
        }
    }

    private fun verifyThatAvailableSeatsContainsSeatsToReserve(seatsToReserve: Set<SeatDto>, availableSeats: Set<SeatDto>) {
        val errorSeats = ArrayList<SeatDto>()
        seatsToReserve.forEach {
            if (it !in availableSeats)
                errorSeats += it
        }
        if (errorSeats.size != 0) {
            throw InvalidReservationException("Input seats are already reserved: $errorSeats")
        }
    }

    private fun hasEmptySeatBetweenTwoReserved(reservedSeats: List<SeatDto>): Boolean {
        reservedSeats
            .sortedWith(compareBy({ it.seatRow }, { it.seatNumber }))
            .take(reservedSeats.size - 1)
            .forEachIndexed { index, seat ->
                if (seat.seatRow == reservedSeats[index + 1].seatRow && seat.seatNumber + 2 == reservedSeats[index + 1].seatNumber) {
                    return true
                }
            }
        return false
    }

    private fun findSeatsByHall(hall: Hall): List<Seat> {
        return seatRepository.findAllByHallIdHall(hall.idHall).also {
            if (it.isEmpty()) {
                throw InvalidReservationException("Hall with id: \"${hall.idHall}\" doesn't exist!")
            }
        }
    }

}
