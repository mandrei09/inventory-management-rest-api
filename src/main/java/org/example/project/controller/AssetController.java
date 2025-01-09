package org.example.project.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.project.controller.generic.BaseController;
import org.example.project.dto.asset.AssetDtoCreate;
import org.example.project.dto.asset.AssetDtoUpdate;
import org.example.project.model.Asset;
import org.example.project.service.AssetService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assets")
@Validated
@Tag(name = "Asset Management", description = "APIs for managing assets")
public class AssetController extends BaseController<Asset, AssetDtoCreate, AssetDtoUpdate> {
    public AssetController(AssetService assetService) {
        super(assetService);
    }
}
