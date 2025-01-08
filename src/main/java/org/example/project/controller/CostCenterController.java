package org.example.project.controller;

import org.example.project.controller.generic.BaseEntityController;
import org.example.project.dto.costCenter.CostCenterDtoCreate;
import org.example.project.dto.costCenter.CostCenterDtoUpdate;
import org.example.project.dto.division.DivisionDtoCreate;
import org.example.project.dto.division.DivisionDtoUpdate;
import org.example.project.model.CostCenter;
import org.example.project.model.Division;
import org.example.project.service.CostCenterService;
import org.example.project.service.DivisionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/costcenters")
@Validated
public class CostCenterController extends BaseEntityController<CostCenter, CostCenterDtoCreate, CostCenterDtoUpdate> {
    public CostCenterController(CostCenterService costCenterService) {
        super(costCenterService);
    }
}
