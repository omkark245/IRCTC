package org.ticket;

import org.ticket.entities.Train;
import org.ticket.entities.User;
import org.ticket.entities.Ticket;
import org.ticket.entities.Employee;
import org.ticket.service.UserBookingService;
import org.ticket.service.TrainService;
import org.ticket.service.EmployeeService;
import org.ticket.util.UserServiceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class App {
    public static void main(String[] args) {
        System.out.println("Running train Booking System");
        Scanner sc = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;
        TrainService trainService;
        EmployeeService employeeService;
        try {
            userBookingService = new UserBookingService();
            trainService = new TrainService();
            employeeService = new EmployeeService();
        } catch (IOException e) {
            System.out.println("There is something wrong with initializing services");
            e.printStackTrace();
            return;
        }

        while (option != 8) {
            System.out.println("\nChoose options:");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Booking");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a seat");
            System.out.println("6. Cancel my booking");
            System.out.println("7. Employee Management (Admin)");
            System.out.println("8. Exit");

            try {
                option = sc.nextInt();
                sc.nextLine(); // consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
                continue;
            }

            switch (option) {
                case 1:
                    System.out.println("Enter name:");
                    String nameToSignUp = sc.nextLine();
                    System.out.println("Enter password:");
                    String passwordToSignUp = sc.nextLine();
                    User newUser = new User(nameToSignUp, passwordToSignUp, UserServiceUtil.hashPassword(passwordToSignUp), new ArrayList<>(), UUID.randomUUID().toString());
                    userBookingService.signup(newUser);
                    System.out.println("Signed up successfully!");
                    break;
                case 2:
                    System.out.println("Enter name:");
                    String nameToLogin = sc.nextLine();
                    System.out.println("Enter password:");
                    String passwordToLogin = sc.nextLine();
                    User userToLogin = new User();
                    userToLogin.setName(nameToLogin);
                    userToLogin.setPassword(passwordToLogin);
                    userBookingService.setUser(userToLogin);
                    if (userBookingService.loginUser()) {
                        System.out.println("Login successful!");
                    } else {
                        System.out.println("Login failed.");
                    }
                    break;
                case 3:
                    System.out.println("Fetching your bookings:");
                    userBookingService.fetchBooking();
                    break;
                case 4:
                    System.out.println("Enter source:");
                    String source = sc.nextLine();
                    System.out.println("Enter destination:");
                    String dest = sc.nextLine();
                    List<Train> trains = trainService.searchTrains(source, dest);
                    if (trains.isEmpty()) {
                        System.out.println("No trains found.");
                    } else {
                        for (Train t : trains) {
                            System.out.println(t.getTrainId() + " : " + t.getTrainNo());
                            System.out.println("Stations: " + t.getStations());
                        }
                    }
                    break;
                case 5:
                    if (userBookingService.getUser() == null) {
                        System.out.println("Please login first.");
                        break;
                    }
                    System.out.println("Available trains:");
                    for (Train t : trainService.getTrainList()) {
                        System.out.println(t.getTrainId() + " : " + t.getTrainNo());
                    }
                    System.out.println("Select a train ID:");
                    String trainId = sc.nextLine();
                    Optional<Train> selectedTrain = trainService.getTrainList().stream().filter(t -> t.getTrainId().equals(trainId)).findFirst();
                    if (selectedTrain.isPresent()) {
                        System.out.println("Enter source for booking:");
                        String bookSource = sc.nextLine();
                        System.out.println("Enter destination for booking:");
                        String bookDest = sc.nextLine();
                        System.out.println("Booking seat on " + selectedTrain.get().getTrainId());
                        Ticket ticket = new Ticket(UUID.randomUUID().toString(), userBookingService.getUser().getUserId(), bookSource, bookDest, new Date(), selectedTrain.get());
                        if (userBookingService.getUser().getTicketBooked() == null) {
                            userBookingService.getUser().setTicketBooked(new ArrayList<>());
                        }
                        userBookingService.getUser().getTicketBooked().add(ticket);
                        try {
                            userBookingService.saveUserListToFile();
                        } catch (IOException e) {
                            System.out.println("Booking save failed.");
                        }
                        System.out.println("Booked successfully!");
                    } else {
                        System.out.println("Train not found.");
                    }
                    break;
                case 6:
                    System.out.println("Enter ticket ID to cancel:");
                    String ticketId = sc.nextLine();
                    if (userBookingService.cancelBooking(ticketId)) {
                        System.out.println("Cancelled successfully!");
                    } else {
                        System.out.println("Cancellation failed.");
                    }
                    break;
                case 7:
                    System.out.println("Enter Admin Password:");
                    String adminPassword = sc.nextLine();
                    if (!"admin123".equals(adminPassword)) {
                        System.out.println("Invalid Admin Password.");
                        break;
                    }
                    System.out.println("Employee Management:");
                    System.out.println("1. Add Employee");
                    System.out.println("2. View Employees");
                    int adminOption = sc.nextInt();
                    sc.nextLine();
                    if (adminOption == 1) {
                        System.out.println("Enter name:");
                        String empName = sc.nextLine();
                        System.out.println("Enter salary:");
                        double salary = sc.nextDouble();
                        sc.nextLine();
                        System.out.println("Enter description:");
                        String desc = sc.nextLine();
                        System.out.println("Enter address:");
                        String address = sc.nextLine();
                        System.out.println("Enter information:");
                        String info = sc.nextLine();
                        Employee emp = new Employee(UUID.randomUUID().toString(), empName, salary, desc, address, info);
                        try {
                            employeeService.addEmployee(emp);
                            System.out.println("Employee added successfully!");
                        } catch (IOException e) {
                            System.out.println("Failed to add employee.");
                        }
                    } else if (adminOption == 2) {
                        List<Employee> employees = employeeService.getEmployeeList();
                        if (employees.isEmpty()) {
                            System.out.println("No employees found.");
                        } else {
                            for (Employee e : employees) {
                                System.out.println("ID: " + e.getId() + ", Name: " + e.getName() + ", Salary: " + e.getSalary());
                                System.out.println("Description: " + e.getDescription() + ", Address: " + e.getAddress());
                                System.out.println("Information: " + e.getInformation());
                                System.out.println("---------------------------");
                            }
                        }
                    }
                    break;
                case 8:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public String getGreeting() {
        return "Hello World!";
    }
}
