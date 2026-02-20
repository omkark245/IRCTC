package org.ticket.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Train {

    private String trainId;
    private String trainNo;
    private List<List<Integer>> seats;
    private Map<String, String> stationTimes;
    private List<String> stations;

    public Train() {}

    public Train(String trainId, String trainNo, List<List<Integer>> seats, Map<String, String> stationTimes, List<String> stations) {
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.seats = seats;
        this.stationTimes = stationTimes;
        this.stations = stations;
    }

    @JsonProperty("train_id")
    public String getTrainId() {
        return trainId;
    }

    @JsonProperty("train_id")
    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    @JsonProperty("train_no")
    public String getTrainNo() {
        return trainNo;
    }

    @JsonProperty("train_no")
    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public List<List<Integer>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<Integer>> seats) {
        this.seats = seats;
    }

    @JsonProperty("stations_time")
    public Map<String, String> getStationTimes() {
        return stationTimes;
    }

    @JsonProperty("stations_time")
    public void setStationTimes(Map<String, String> stationTimes) {
        this.stationTimes = stationTimes;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    public String getTrainInfo() {
        return String.format("Train Id: %s Train No: %s", trainId, trainNo);
    }
}
