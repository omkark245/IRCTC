package org.ticket.entities;

import java.util.Date;


public class ticket {

    private String ticketId;
    private  String userId;
    private String source;
    private String destination;
    private Date dateofTravel;
    private train train;

    public ticket(String ticketId, String userId, String source, String destination, Date dateofTravel, train train) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.dateofTravel = dateofTravel;
        this.train = train;
    }
    public  String getTicketInfo()
    {
        return String.format("Ticket Id : %s  belogs to user %s from %s to %s on %S",ticketId,userId,source,destination,dateofTravel);
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDateofTravel() {
        return dateofTravel;
    }

    public void setDateofTravel(Date dateofTravel) {
        this.dateofTravel = dateofTravel;
    }

    public train getTrain() {
        return train;
    }

    public void setTrain(train train) {
        this.train = train;
    }
}
