package org.example.project.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Division Management", description = "APIs for managing divisions")
public class DivisionController extends BaseController<Division, DivisionDtoCreate, DivisionDtoUpdate> {
    public DivisionController(DivisionService divisionService) {
        super(divisionService);
    }
}
