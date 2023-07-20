package com.example.springdatarepo.controller;

import com.example.springdatarepo.model.Employee;
import com.example.springdatarepo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private ObjectMapper objectMapper = new ObjectMapper();


    /*
    метод jsonPath - извлечение определенных полей из JSON-ответа и проверка их значений.
    jsonPath("$[0].name")- путь к первому элементу массива (в JSON массивы индексируются с 0) и далее до поля "name".
    value("John Lol"), мы проверяем, что значение поля "name" действительно равно "John Lol".
    */
    @Test
    public void testGetAllUsers() throws Exception {
        Employee john = new Employee(1,"John Lol", "Manager", "123-456-1111");
        Employee jane = new Employee(2,"Jane Lol", "Developer", "987-654-1111");
        List<Employee> sampleEmployees = Arrays.asList(john, jane);

        when(employeeService.findAll()).thenReturn(sampleEmployees);

        mockMvc.perform(get("/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Lol"))
                .andExpect(jsonPath("$[0].position").value("Manager"))
                .andExpect(jsonPath("$[0].phone").value("123-456-1111"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane Lol"))
                .andExpect(jsonPath("$[1].position").value("Developer"))
                .andExpect(jsonPath("$[1].phone").value("987-654-1111"))
                .andDo(print());

        verify(employeeService, times(1)).findAll();
    }

    /*
    JSONPath
    $ - корневой элемент JSON-объекта. Он указывает, что поиск начинается с самого верхнего уровня JSON.
    content - ключ объекта JSON. В вашем случае, content является ключом, который содержит массив с данными о сотрудниках.
    [0] - индекс элемента массива JSON. В данном случае, [0] указывает на первый элемент в массиве content.
    name - ключ объекта JSON, который находится внутри первого элемента массива.
     */
    @Test
    public void testGet1pageAllUsers() throws Exception {
        int pageNumber = 0;
        int pageSize = 2;
        String sortBy = "name";

        // Создаем несколько тестовых сотрудников
        Employee john = new Employee(1, "John Doe", "Manager", "123-456-1111");
        Employee jane = new Employee(2, "Jane Smith", "Developer", "987-654-1111");
        List<Employee> employees = Arrays.asList(john, jane);

        // Создаем объект Pageable
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // Создаем объект PageImpl<Employee> на основе списка сотрудников и объекта Pageable
        Page<Employee> page = new PageImpl<>(employees, pageable, employees.size());

        // Мокаем метод employeeService.getEmployees(pageNumber, pageSize, sortBy) и возвращаем объект Page
        when(employeeService.getEmployees(pageNumber, pageSize, sortBy)).thenReturn(page);

        // Выполняем GET-запрос с параметрами pageNumber, pageSize и sortBy
        mockMvc.perform(get("/paging")
                        .param("pageNumber", String.valueOf(pageNumber))
                        .param("pageSize", String.valueOf(pageSize))
                        .param("sortBy", sortBy))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.content[0].name").value("John Doe"))
                .andExpect(jsonPath("$.content[0].position").value("Manager"))
                .andExpect(jsonPath("$.content[0].phone").value("123-456-1111"))
                .andExpect(jsonPath("$.content[1].name").value("Jane Smith"))
                .andExpect(jsonPath("$.content[1].position").value("Developer"))
                .andExpect(jsonPath("$.content[1].phone").value("987-654-1111"));

        // Проверяем, что метод employeeService.getEmployees был вызван с правильными параметрами
        verify(employeeService, times(1)).getEmployees(pageNumber, pageSize, sortBy);
    }

    @Test
    public void testBatchSaveEmployees() throws Exception {
        // Создаем тестовые сотрудники
        Employee john = new Employee(1,"John Doe", "Manager", "123-456-1111");
        Employee jane = new Employee(2,"Jane Smith", "Developer", "987-654-1111");
        List<Employee> employees = Arrays.asList(john, jane);

        // Выполняем POST-запрос с данными сотрудников в формате JSON
        mockMvc.perform(post("/batch-save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(employees)))
                .andExpect(status().isOk())
                .andDo(print());

        // Проверяем, что метод employeeService.performBatchSave был вызван с правильными данными
        verify(employeeService, times(1)).performBatchSave(employees);
    }

    @Test
    public void testBatchDeleteEmployees() throws Exception {
        // Создаем тестовые сотрудники
        Employee john = new Employee(1,"John Doe", "Manager", "123-456-1111");
        Employee jane = new Employee(2,"Jane Smith", "Developer", "987-654-1111");
        List<Employee> employees = Arrays.asList(john, jane);

        // Выполняем POST-запрос с данными сотрудников в формате JSON
        mockMvc.perform(delete("/delete-in-batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(employees)))
                .andExpect(status().isOk())
                .andDo(print());

        // Проверяем, что метод employeeService.performBatchSave был вызван с правильными данными
        verify(employeeService, times(1)).deleteInBatch(employees);
    }

}