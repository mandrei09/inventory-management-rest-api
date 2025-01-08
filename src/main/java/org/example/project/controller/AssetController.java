package org.example.project.controller;

import org.example.project.controller.generic.BaseEntityController;
import org.example.project.dto.asset.AssetDtoCreate;
import org.example.project.dto.asset.AssetDtoUpdate;
import org.example.project.dto.inventory.InventoryDtoCreate;
import org.example.project.dto.inventory.InventoryDtoUpdate;
import org.example.project.model.Asset;
import org.example.project.model.Inventory;
import org.example.project.service.AssetService;
import org.example.project.service.InventoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assets")
@Validated
public class AssetController extends BaseEntityController<Asset, AssetDtoCreate, AssetDtoUpdate> {
    public AssetController(AssetService assetService) {
        super(assetService);
    }
}
