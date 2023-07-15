package com.example.springdatarepo.service;

import com.example.springdatarepo.model.Employee;
import com.example.springdatarepo.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    public Page<Employee> getEmployees(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return employeeRepo.findAll(pageable);
    }

}
