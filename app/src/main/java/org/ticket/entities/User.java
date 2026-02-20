package org.ticket.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String name;
    @com.fasterxml.jackson.annotation.JsonIgnore
    private String password;
    private String hashPassword;
    private List<Ticket> ticketBooked;
    private String userId;

    public User(String name, String password, String hashPassword, List<Ticket> ticketBooked, String userId) {
        this.name = name;
        this.password = password;
        this.hashPassword = hashPassword;
        this.ticketBooked = ticketBooked;
        this.userId = userId;
    }

    public User() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    @JsonProperty("tickects_booked")
    public List<Ticket> getTicketBooked() {
        return ticketBooked;
    }

    @JsonProperty("tickects_booked")
    public void setTicketBooked(List<Ticket> ticketBooked) {
        this.ticketBooked = ticketBooked;
    }

    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void printTickets() {
        if (ticketBooked != null) {
            for (Ticket ticket : ticketBooked) {
                System.out.println(ticket.getTicketInfo());
            }
        }
    }
}
