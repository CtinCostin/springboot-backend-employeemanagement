package com.george.springbootbackendemployeemanagement.controller;

import com.george.springbootbackendemployeemanagement.exception.ResourceNotFoundException;
import com.george.springbootbackendemployeemanagement.model.Employee;
import com.george.springbootbackendemployeemanagement.repository.EmployeeRepository;
import com.george.springbootbackendemployeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(value = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id : " + id + " is not existing in the Database"));

        return ResponseEntity.ok(employee);
    }

    @GetMapping("employees/filterByName")
    public ResponseEntity<List<Employee>> getEmployeeByName(@RequestParam String firstName) {
        List<Employee> employeeList = employeeService.getEmployeeByName(firstName);
        return ResponseEntity.ok(employeeList);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id : " + id + " is not existing in the Database"));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());

        Employee updatedEmployee = employeeRepository.save(employee);

        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id : " + id + " is not existing in the Database"));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted!", Boolean.TRUE);
        return ResponseEntity.ok(response);

    }

}
