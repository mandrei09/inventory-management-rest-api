package org.example.project.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.project.controller.generic.BaseController;
import org.example.project.dto.company.CompanyDtoCreate;
import org.example.project.dto.company.CompanyDtoUpdate;
import org.example.project.model.Company;
import org.example.project.service.CompanyService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
@Validated
@Tag(name = "Company Management", description = "APIs for managing companies")
public class CompanyController extends BaseController<Company, CompanyDtoCreate, CompanyDtoUpdate> {

    public CompanyController(CompanyService companyService) {
        super(companyService);
    }
}
