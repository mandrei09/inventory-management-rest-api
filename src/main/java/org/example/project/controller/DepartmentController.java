package org.example.project.controller;

import org.example.project.controller.generic.BaseController;
import org.example.project.dto.department.DepartmentDtoCreate;
import org.example.project.dto.department.DepartmentDtoUpdate;
import org.example.project.model.Department;
import org.example.project.service.DepartmentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
@Validated
public class DepartmentController extends BaseController<Department, DepartmentDtoCreate, DepartmentDtoUpdate> {

    public DepartmentController(DepartmentService departmentService) {
        super(departmentService);
    }
}
