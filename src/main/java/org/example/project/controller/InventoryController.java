package org.example.project.controller;

import org.example.project.controller.generic.BaseEntityController;
import org.example.project.dto.costCenter.CostCenterDtoCreate;
import org.example.project.dto.costCenter.CostCenterDtoUpdate;
import org.example.project.dto.inventory.InventoryDtoCreate;
import org.example.project.dto.inventory.InventoryDtoUpdate;
import org.example.project.model.CostCenter;
import org.example.project.model.Inventory;
import org.example.project.service.CostCenterService;
import org.example.project.service.InventoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventories")
@Validated
public class InventoryController extends BaseEntityController<Inventory, InventoryDtoCreate, InventoryDtoUpdate> {
    public InventoryController(InventoryService inventoryService) {
        super(inventoryService);
    }
}
