package com.example.springdatarepo.repo;

import com.example.springdatarepo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {


}
