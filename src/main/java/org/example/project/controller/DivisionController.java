package org.example.project.controller;

import org.example.project.controller.generic.BaseEntityController;
import org.example.project.dto.department.DepartmentDtoCreate;
import org.example.project.dto.department.DepartmentDtoUpdate;
import org.example.project.dto.division.DivisionDtoCreate;
import org.example.project.dto.division.DivisionDtoUpdate;
import org.example.project.model.Department;
import org.example.project.model.Division;
import org.example.project.service.DepartmentService;
import org.example.project.service.DivisionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/divisions")
@Validated
public class DivisionController extends BaseEntityController<Division, DivisionDtoCreate, DivisionDtoUpdate> {
    public DivisionController(DivisionService divisionService) {
        super(divisionService);
    }
}
