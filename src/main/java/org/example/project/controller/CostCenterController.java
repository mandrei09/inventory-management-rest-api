package org.example.project.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.project.controller.generic.BaseController;
import org.example.project.dto.costCenter.CostCenterDtoCreate;
import org.example.project.dto.costCenter.CostCenterDtoUpdate;
import org.example.project.model.CostCenter;
import org.example.project.service.CostCenterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/costcenters")
@Validated
@Tag(name = "Cost Center Management", description = "APIs for managing cost centers")
public class CostCenterController extends BaseController<CostCenter, CostCenterDtoCreate, CostCenterDtoUpdate> {
    public CostCenterController(CostCenterService costCenterService) {
        super(costCenterService);
    }
}
