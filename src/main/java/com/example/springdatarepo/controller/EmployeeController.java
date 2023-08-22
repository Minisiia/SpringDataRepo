package com.example.springdatarepo.controller;

import com.example.springdatarepo.model.Employee;
import com.example.springdatarepo.service.EmployeeService;
import com.example.springdatarepo.service.EmployeeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    private EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public Iterable<Employee> getAllUsers() {
        // This returns a JSON or XML with the users
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee getUser(@PathVariable("id") int id) {
        // This returns a JSON or XML with the users
        return employeeService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Employee employee,
                                             BindingResult bindingResult) throws Exception {
        checkBindingResult(bindingResult);
        employeeService.save(employee);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateTask(@PathVariable("id") int id,
                                                 @RequestBody @Valid Employee employee,
                                                 BindingResult bindingResult) throws Exception {
        checkBindingResult(bindingResult);
        employeeService.update(id,employee);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") int id) {
        employeeService.delete(id);
    }

    private void checkBindingResult(BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new Exception(errorMessage.toString());
        }
    }


//
//            //http://localhost:8080/paging?pageNumber=0&pageSize=2&sortBy=name
//    @GetMapping("/paging")
//    public Iterable<Employee> get1pageAllUsers(@RequestParam int pageNumber,
//                                                             @RequestParam int pageSize,
//                                                             @RequestParam String sortBy) {
//        // This returns a JSON or XML with the users
//        return employeeService.getEmployees(pageNumber,pageSize, sortBy);
//
//            //http://localhost:8080/paging/0/2/name
//
////        @GetMapping("/paging/{pageNumber}/{pageSize}/{sortBy}")
////    public @ResponseBody Iterable<Employee> get1pageAllUsers(@PathVariable int pageNumber,
////                                                             @PathVariable int pageSize,
////                                                             @PathVariable String sortBy) {
////        // This returns a JSON or XML with the users
////        System.out.println(employeeService.getEmployees(pageNumber,pageSize, sortBy));
////        return employeeService.getEmployees(pageNumber,pageSize, sortBy);
//    }
//
//    //http://localhost:8080/batch-save
////  [
////    {
////        "name": "John Lol",
////            "position": "Manager",
////            "phone": "123-456-1111"
////    },
////    {
////        "name": "Jane Lol",
////            "position": "Developer",
////            "phone": "987-654-1111"
////    }
////]
//    @PostMapping("/batch-save")
//    public void batchSaveEmployees(@RequestBody List<Employee> employees) {
//        employeeService.performBatchSave(employees);
//    }
//
//    //http://localhost:8080/delete?partialName=Lol
//    @DeleteMapping("/delete")
//    public void deleteEmployeesByPartialName(@RequestParam String partialName) {
//        employeeService.deleteByNameContaining(partialName);
//    }
//
//    //http://localhost:8080/delete-in-batch
//    @DeleteMapping("/delete-in-batch")
//    public void deleteBatch(@RequestBody Iterable<Employee> entities) {
//        employeeService.deleteInBatch(entities);
//    }

}
