package org.example.project.controller;

import org.example.project.controller.generic.BaseEntityController;
import org.example.project.dto.CompanyDto;
import org.example.project.model.Company;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@Validated
public class CompanyController extends BaseEntityController<Company, CompanyDto> {

}
