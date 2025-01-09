package org.example.project.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.project.controller.generic.BaseController;
import org.example.project.dto.employee.EmployeeDtoCreate;
import org.example.project.dto.employee.EmployeeDtoUpdate;
import org.example.project.model.Employee;
import org.example.project.service.EmployeeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
@Validated
@Tag(name = "Employees Management", description = "APIs for managing employees")
public class EmployeeController extends BaseController<Employee, EmployeeDtoCreate, EmployeeDtoUpdate> {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        super(employeeService);
        this.employeeService = employeeService;
    }
}
