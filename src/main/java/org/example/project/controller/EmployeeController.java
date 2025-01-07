package org.example.project.controller;

import org.example.project.controller.generic.BaseEntityController;
import org.example.project.dto.EmployeeDto;
import org.example.project.model.Employee;
import org.example.project.service.EmployeeService;
import org.example.project.service.generic.BaseEntityService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@Validated
public class EmployeeController extends BaseEntityController<Employee, EmployeeDto> {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        super(employeeService);
        this.employeeService = employeeService;
    }
}
