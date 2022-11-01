# Db model

![image](https://user-images.githubusercontent.com/84989172/199333161-72dfec93-d6cf-4bb1-aafe-d54704cdabe8.png)

# How to launch the application

The project is written in java 17 so you will need this version or higher to run

## With locally installed maven

You need to run the buildAndRun.sh script or enter the `mvn clean install spring-boot:run` command in the terminal

## Another way to run the application

1. Launch a project in IntelliJ IDEA
2. Make sure Java 17 is selected in the project structure
![image](https://user-images.githubusercontent.com/84989172/199335580-69ef2b8f-a389-43c0-a959-b7d89e13ca6b.png)
3. Open the maven tab and click on `Execute Maven Goal`                                             
![image](https://user-images.githubusercontent.com/84989172/199336050-0e55ba28-d83e-4394-b69d-5edd7ac14976.png)
4. Enter the command `mvn clean install spring-boot:run`
![image](https://user-images.githubusercontent.com/84989172/199336747-6da67c3c-256b-4395-aa45-baf6ffaf905e.png)

# How to test the program

## End-To-End Tests
The program has several tests End-To-End. They run every time you use the run command shown above.

## IntelliJ IDEA http client
There are two .http files in the httpTest folder. After launching the application, you can run requests and see the responses from the server.
![image](https://user-images.githubusercontent.com/84989172/199353477-223fe0e1-2e54-403d-bba7-515ad2c699d3.png)
