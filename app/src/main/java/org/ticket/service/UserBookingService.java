package org.ticket.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ticket.entities.User;
import org.ticket.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user;
    private List<User> userList;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String USERS_PATH = "app/src/main/java/org/ticket/localdb/user.json";

    public UserBookingService(User user) throws IOException {
        this.user = user;
        loadUserList();
    }

    public UserBookingService() throws IOException {
        loadUserList();
    }

    public void loadUserList() throws IOException {
        File usersFile = new File(USERS_PATH);
        if (!usersFile.exists()) {
            userList = new ArrayList<>();
        } else {
            userList = objectMapper.readValue(usersFile, new TypeReference<List<User>>() {});
        }
    }

    public Boolean loginUser() {
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashPassword());
        }).findFirst();
        if (foundUser.isPresent()) {
            this.user = foundUser.get();
            return true;
        }
        return false;
    }

    public Boolean signup(User user1) {
        try {
            userList.add(user1);
            saveUserListToFile();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public void saveUserListToFile() throws IOException {
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public void fetchBooking() {
        if (user != null) {
            user.printTickets();
        } else {
            System.out.println("No user logged in.");
        }
    }

    public Boolean cancelBooking(String ticketId) {
        if (user == null || user.getTicketBooked() == null) {
            return false;
        }
        boolean removed = user.getTicketBooked().removeIf(ticket -> ticket.getTicketId().equals(ticketId));
        if (removed) {
            try {
                saveUserListToFile();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    public List<User> getUserList() {
        return userList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
