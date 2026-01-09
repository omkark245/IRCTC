package org.ticket.entities;

import sun.security.krb5.internal.Ticket;

import java.util.*;

public class user {
    private String name;
    private String Password;
    private String hashPassword;
    private static List<Ticket> ticketBooked;
    private  String userId;


    public user(String name,String Password, String hashPassword,List<Ticket>ticketBooked, String userId)
    {
        this.name=name;
        this.Password=Password;
        this.hashPassword=hashPassword;
        this.ticketBooked=ticketBooked;
        this.userId=userId;
    }
    public user(){}

    public static String[] getNames() {
        return this.name;
    }

    public static Object getPassword() {
        return this.Password;
    }

    public String getHashPassword()
    {
        return  this.hashPassword;
    }
    public static List<Ticket> getTicketBooked()
    {
        return ticketBooked;

    }

    public static void printTickets()
    {
        for(int i=0;i<ticketBooked.size();i++)
            System.out.println(ticketBooked.get(i).getTicketInfo());
    }

    public   String getUserId()
    {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public void setTicketBooked(List<Ticket> ticketBooked) {
        this.ticketBooked = ticketBooked;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
