package org.ticket.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ticket.entities.Employee;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private List<Employee> employeeList;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String EMPLOYEES_PATH = "app/src/main/java/org/ticket/localdb/employee.json";
    private static final String EMPLOYEES_PATH_SUB = "src/main/java/org/ticket/localdb/employee.json";

    public EmployeeService() throws IOException {
        loadEmployeeList();
    }

    private File getEmployeeFile() {
        File file = new File(EMPLOYEES_PATH);
        if (file.getParentFile().exists()) {
            return file;
        }
        return new File(EMPLOYEES_PATH_SUB);
    }

    public void loadEmployeeList() throws IOException {
        File employeeFile = getEmployeeFile();
        if (!employeeFile.exists()) {
            employeeList = new ArrayList<>();
        } else {
            employeeList = objectMapper.readValue(employeeFile, new TypeReference<List<Employee>>() {});
        }
    }

    public void addEmployee(Employee employee) throws IOException {
        employeeList.add(employee);
        saveEmployeeListToFile();
    }

    public void saveEmployeeListToFile() throws IOException {
        File employeeFile = getEmployeeFile();
        employeeFile.getParentFile().mkdirs();
        objectMapper.writeValue(employeeFile, employeeList);
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }
}
