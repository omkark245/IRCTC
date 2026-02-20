package org.ticket;

import org.ticket.entities.Train;
import org.ticket.entities.User;
import org.ticket.entities.Ticket;
import org.ticket.service.UserBookingService;
import org.ticket.service.TrainService;
import org.ticket.util.UserServiceUtil;

import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) {
        System.out.println("Running train Booking System");
        Scanner sc = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;
        TrainService trainService;
        try {
            userBookingService = new UserBookingService();
            trainService = new TrainService();
        } catch (IOException e) {
            System.out.println("There is something wrong with initializing services");
            e.printStackTrace();
            return;
        }

        while (option != 7) {
            System.out.println("\nChoose options:");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Booking");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a seat");
            System.out.println("6. Cancel my booking");
            System.out.println("7. Exit");

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
