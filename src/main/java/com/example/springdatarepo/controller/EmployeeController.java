package com.example.springdatarepo.controller;

import com.example.springdatarepo.model.Employee;
import com.example.springdatarepo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Employee> getAllUsers() {
        // This returns a JSON or XML with the users
        return employeeService.findAll();
    }

            //http://localhost:8080/paging?pageNumber=0&pageSize=2&sortBy=name
    @GetMapping("/paging")
    public @ResponseBody Iterable<Employee> get1pageAllUsers(@RequestParam int pageNumber,
                                                             @RequestParam int pageSize,
                                                             @RequestParam String sortBy) {
        // This returns a JSON or XML with the users
        System.out.println(employeeService.getEmployees(pageNumber,pageSize, sortBy));
        return employeeService.getEmployees(pageNumber,pageSize, sortBy);

            //http://localhost:8080/paging/0/2/name

//        @GetMapping("/paging/{pageNumber}/{pageSize}/{sortBy}")
//    public @ResponseBody Iterable<Employee> get1pageAllUsers(@PathVariable int pageNumber,
//                                                             @PathVariable int pageSize,
//                                                             @PathVariable String sortBy) {
//        // This returns a JSON or XML with the users
//        System.out.println(employeeService.getEmployees(pageNumber,pageSize, sortBy));
//        return employeeService.getEmployees(pageNumber,pageSize, sortBy);
    }

}
