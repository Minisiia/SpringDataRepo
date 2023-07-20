package com.example.springdatarepo.repo;

import com.example.springdatarepo.model.Employee;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepoTest {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Test
    public void testDeleteAllInBatch() {
        // Создаем тестовых сотрудников
        Employee john = new Employee(1,"John Doe", "Manager", "123-456-1111");
        Employee jane = new Employee(2,"Jane Smith", "Developer", "987-654-1111");
        List<Employee> employees = Arrays.asList(john, jane);


        // Проверяем, что сотрудники сохранены в базу данных
        assertEquals(9, employeeRepo.findAll().size());

        // Вызываем метод deleteAllInBatch с теми же id, что и у сотрудников
        employeeRepo.deleteAllInBatch(employees);

        List<Employee> listAfterDelete = employeeRepo.findAll();

        // Проверяем, что сотрудники были удалены из базы данных
        assertEquals(7, listAfterDelete.size());
        System.out.println(listAfterDelete);

        Assumptions.assumeTrue(listAfterDelete.size() == 7);
    }

}