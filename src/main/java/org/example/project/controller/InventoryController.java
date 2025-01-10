package org.example.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.project.controller.generic.BaseController;
import org.example.project.dto.inventory.InventoryDtoCreate;
import org.example.project.dto.inventory.InventoryDtoUpdate;
import org.example.project.dto.others.InventoryDetail;
import org.example.project.dto.others.ScanAssetDtoCreate;
import org.example.project.model.Inventory;
import org.example.project.result.Result;
import org.example.project.service.InventoryAssetService;
import org.example.project.service.InventoryService;
import org.example.project.utils.StatusMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/inventories")
@Validated
@Tag(name = "Inventory Management", description = "APIs for managing inventories and asset scanning.")

public class InventoryController extends BaseController<Inventory, InventoryDtoCreate, InventoryDtoUpdate> {
    private final InventoryService inventoryService;
    private final InventoryAssetService inventoryAssetService;

    public InventoryController(InventoryService inventoryService, InventoryAssetService inventoryAssetService) {
        super(inventoryService);
        this.inventoryService = inventoryService;
        this.inventoryAssetService = inventoryAssetService;
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid InventoryDtoCreate inventoryDto) {
        Result<Inventory> result = inventoryService.create(inventoryDto);

        if (result != null && result.isSuccess()) {
            Result<Integer> assetsInserted =
                    inventoryAssetService.insertAssetsIntoNewInventory(result.getObject().getId(), inventoryDto.getCompanyId());

            if(assetsInserted.isSuccess()) {
                var uri = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(result.getObject().getId())
                        .toUri();

                return ResponseEntity.created(uri).body(result);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(assetsInserted.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result != null ? result.getMessage() : StatusMessages.UNKNOWN_ERROR);
    }

    @Operation(summary = "Get inventory details", description = "Retrieve detailed information about an active inventory and its associated assets.")
    @GetMapping("/detail/{inventoryId}")
    public ResponseEntity<Result<InventoryDetail>> inventoryDetail(@PathVariable Long inventoryId) {
        return ResponseEntity.ok().body(inventoryAssetService.getInventoryDetail(inventoryId));
    }

    @Operation(summary = "Set scanned assets for an inventory", description = "Update the scanned status of assets in an active inventory.")
    @PutMapping("/setassetsscanned/{inventoryId}")
    public ResponseEntity<?> setAssetsScanned(@PathVariable Long inventoryId, @RequestBody @Valid List<ScanAssetDtoCreate> inventoryDto) {
        return ResponseEntity.ok().body(inventoryAssetService.setAssetsScanned(inventoryId, inventoryDto));
    }
}
