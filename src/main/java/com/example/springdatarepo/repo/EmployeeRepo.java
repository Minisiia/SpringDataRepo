package com.example.springdatarepo.repo;

import com.example.springdatarepo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Integer> {
    @Modifying
    @Query("DELETE FROM Employee e WHERE e.name like %?1%")
    void deleteByPartOfName(String partName);

    void deleteByNameContaining(String partialName);

    @Override
    void deleteAllInBatch(Iterable<Employee>employees);
}
