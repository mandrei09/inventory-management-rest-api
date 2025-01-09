package org.example.project.controller;

import org.example.project.controller.generic.BaseController;
import org.example.project.dto.division.DivisionDtoCreate;
import org.example.project.dto.division.DivisionDtoUpdate;
import org.example.project.model.Division;
import org.example.project.service.DivisionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/divisions")
@Validated
public class DivisionController extends BaseController<Division, DivisionDtoCreate, DivisionDtoUpdate> {
    public DivisionController(DivisionService divisionService) {
        super(divisionService);
    }
}
