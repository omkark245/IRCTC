package org.ticket.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {
    private String id;
    private String name;
    private Double salary;
    private String description;
    private String address;
    private String information;

    public Employee() {}

    public Employee(String id, String name, Double salary, String description, String address, String information) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.description = description;
        this.address = address;
        this.information = information;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("salay")
    public Double getSalary() {
        return salary;
    }

    @JsonProperty("salay")
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @JsonProperty("desc")
    public String getDescription() {
        return description;
    }

    @JsonProperty("desc")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("addres")
    public String getAddress() {
        return address;
    }

    @JsonProperty("addres")
    public void setAddress(String address) {
        this.address = address;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
