package org.example.project.controller;

import org.example.project.controller.generic.BaseEntityController;
import org.example.project.dto.company.CompanyDtoCreate;
import org.example.project.dto.company.CompanyDtoUpdate;
import org.example.project.dto.department.DepartmentDtoCreate;
import org.example.project.dto.department.DepartmentDtoUpdate;
import org.example.project.model.Company;
import org.example.project.model.Department;
import org.example.project.service.CompanyService;
import org.example.project.service.DepartmentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
@Validated
public class DepartmentController extends BaseEntityController<Department, DepartmentDtoCreate, DepartmentDtoUpdate> {

    public DepartmentController(DepartmentService departmentService) {
        super(departmentService);
    }
}
