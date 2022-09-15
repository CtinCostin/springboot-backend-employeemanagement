package com.george.springbootbackendemployeemanagement.service;

import com.george.springbootbackendemployeemanagement.exception.ResourceNotFoundException;
import com.george.springbootbackendemployeemanagement.model.Employee;
import com.george.springbootbackendemployeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getEmployeeByName(String firstName) {
        List<Employee> employees = employeeRepository.findByFirstName(firstName);
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("Employee with name " + firstName + " is not existing!");
        }
        return employees;
    }
}
