package org.example.project.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.project.dto.inventoryAsset.InventoryAssetDtoCreate;
import org.example.project.dto.inventoryAsset.InventoryAssetDtoUpdate;
import org.example.project.dto.others.InventoryDetail;
import org.example.project.dto.others.ScanAssetDtoCreate;
import org.example.project.model.Asset;
import org.example.project.model.CostCenter;
import org.example.project.model.Inventory;
import org.example.project.model.InventoryAsset;
import org.example.project.model.embedable.InventoryAssetId;
import org.example.project.repository.IAssetRepository;
import org.example.project.repository.ICostCenterRepository;
import org.example.project.repository.IInventoryAssetRepository;
import org.example.project.repository.IInventoryRepository;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseService;
import org.example.project.service.interfaces.IInventoryAssetService;
import org.example.project.utils.StatusMessages;
import org.example.project.utils.InventoryStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InventoryAssetService extends BaseService<InventoryAsset, InventoryAssetDtoCreate, InventoryAssetDtoUpdate> implements IInventoryAssetService {

    private final IInventoryAssetRepository inventoryAssetRepository;
    private final IInventoryRepository inventoryRepository;
    private final IAssetRepository assetRepository;
    private final ICostCenterRepository costCenterRepository;

    public InventoryAssetService(IInventoryAssetRepository inventoryAssetRepository, IInventoryRepository inventoryRepository,
                                 IAssetRepository assetRepository, ICostCenterRepository costCenterService) {
        super(inventoryAssetRepository);
        this.inventoryAssetRepository = inventoryAssetRepository;
        this.inventoryRepository = inventoryRepository;
        this.assetRepository = assetRepository;
        this.costCenterRepository = costCenterService;
    }

    @Override
    @Operation(
            summary = "Map InventoryAssetDtoCreate to InventoryAsset",
            description = "Converts an InventoryAssetDtoCreate object into an InventoryAsset entity.",
            responses = @ApiResponse(responseCode = "200", description = "Successfully mapped DTO to entity")
    )
    public Result<InventoryAsset> mapToModel(InventoryAssetDtoCreate dto) {
        Result<InventoryAsset> result = new Result<>();
        result.setSuccess(true);

        Inventory inventory = inventoryRepository.findById(dto.getInventoryId());
        if(inventory == null) {
            result.entityNotFound(StatusMessages.INVENTORY_NOT_FOUND);
        }

        Asset asset = assetRepository.findById(dto.getAssetId());
        if(asset == null) {
            result.entityNotFound(StatusMessages.ASSET_NOT_FOUND);
        }

        CostCenter costCenterInitial = costCenterRepository.findById(dto.getCostCenterInitialId());

        if (costCenterInitial == null) {
            return result.entityNotFound(StatusMessages.COST_CENTER_INITIAL_NOT_FOUND);
        }

        CostCenter costCenterFinal = costCenterRepository.findById(dto.getCostCenterFinalId());

        if (costCenterFinal == null) {
            return result.entityNotFound(StatusMessages.COST_CENTER_FINAL_NOT_FOUND);
        }

        if(!result.isSuccess())
            return result;

        return result.entityFound(
            InventoryAsset.builder()
                    .inventory(inventory)
                    .asset(asset)
                    .costCenterInitial(costCenterInitial)
                    .costCenterFinal(costCenterFinal)
                    .quantityInitial(dto.getQuantityInitial())
                    .quantityFinal(dto.getQuantityFinal())
                    .build()
        );
    }

    @Override
    @Operation(
            summary = "Map InventoryAsset to InventoryAssetDtoCreate",
            description = "Converts an InventoryAsset entity into an InventoryAssetDtoCreate object.",
            responses = @ApiResponse(responseCode = "200", description = "Successfully mapped entity to DTO")
    )
    public InventoryAssetDtoCreate mapToDto(InventoryAsset inventoryAsset) {
        return InventoryAssetDtoCreate.builder()
                .inventoryId(inventoryAsset.getInventory() != null ? inventoryAsset.getInventory().getId() : null)
                .assetId(inventoryAsset.getAsset() != null ? inventoryAsset.getAsset().getId() : null)
                .costCenterInitialId(inventoryAsset.getCostCenterInitial() != null ? inventoryAsset.getCostCenterInitial().getId() : null)
                .costCenterFinalId(inventoryAsset.getCostCenterFinal() != null ? inventoryAsset.getCostCenterFinal().getId() : null)
                .quantityInitial(inventoryAsset.getQuantityInitial())
                .quantityFinal(inventoryAsset.getQuantityFinal())
            .build();
    }

    @Override
    @Operation(
            summary = "Update InventoryAsset from DTO",
            description = "Updates an existing InventoryAsset entity using an InventoryAssetDtoUpdate object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated the entity"),
                    @ApiResponse(responseCode = "404", description = "Related entities not found")
            }
    )
    public Result<InventoryAsset> updateFromDto(InventoryAsset inventoryAsset, InventoryAssetDtoUpdate dto) {
        Result<InventoryAsset> result = new Result<>();

        if(dto.getInventoryId() != null) {
            var inventory = inventoryRepository.findById(dto.getInventoryId());
            if(inventory != null) {
                inventoryAsset.setInventory(inventory);
            }
            else {
                result.entityNotFound("Inventory not found!");
            }
        }

        if(dto.getAssetId() != null) {
            var asset = assetRepository.findById(dto.getAssetId());
            if(asset != null) {
                inventoryAsset.setAsset(asset);
            }
            else {
                result.entityNotFound("Asset not found!");
            }
        }

        if(dto.getCostCenterInitialId() != null) {
            var costCenterInitial = costCenterRepository.findById(dto.getCostCenterInitialId());
            if(costCenterInitial != null) {
                inventoryAsset.setCostCenterInitial(costCenterInitial);
            }
            else {
                result.entityNotFound("CostCenterInitial not found!");
            }
        }

        if(dto.getCostCenterFinalId() != null) {
            var costCenterFinal = costCenterRepository.findById(dto.getCostCenterFinalId());
            if(costCenterFinal != null) {
                inventoryAsset.setCostCenterFinal(costCenterFinal);
            }
            else {
                result.entityNotFound("CostCenterFinal not found!");
            }
        }

        return result;
    }

        @Operation(
            summary = "Find an entity by ID",
            description = "Finds and returns an entity by its unique identifier.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Entity found"),
                    @ApiResponse(responseCode = "404", description = "Entity not found")
            }
    )
    public InventoryAsset findById(@Parameter(description = "ID of the entity") InventoryAssetId id) {
        return inventoryAssetRepository.findById(id);
    }

    @Operation(
            summary = "Insert assets into a new inventory",
            description = "Adds assets from the previous inventory or directly from the company's asset list to the new inventory.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully inserted assets into inventory"),
                    @ApiResponse(responseCode = "404", description = "Previous inventory or company assets not found")
            }
    )
    @Override
    public Result<Integer> insertAssetsIntoNewInventory(Long newInventoryId, Long companyId) {
        Result<Integer> result = new Result<>();
        Long lastInventoryId = inventoryRepository.findLastIdByCompanyId(companyId);

        List<InventoryAsset> oldInventoryAssets
                = inventoryAssetRepository.findAllByInventoryIdAndCompanyId(lastInventoryId, companyId);

        if(oldInventoryAssets != null && !oldInventoryAssets.isEmpty()) {
            insertAssetsFromInventoryAsset(newInventoryId, oldInventoryAssets);
            return result.entityFound(oldInventoryAssets.size());
        }
        else {
            List<Asset> assets = assetRepository.findAllByCompanyIdAndIsDeletedFalse(companyId);
            insertAssetsFromAsset(newInventoryId, assets);
            return result.entityFound(assets.size());
        }
    }

    private void insertAssetsFromInventoryAsset(Long inventoryId, List<InventoryAsset> assets) {
        for(InventoryAsset asset : assets) {
            create(
                    InventoryAsset.builder()
                        .inventory(inventoryRepository.findById(inventoryId))
                        .asset(asset.getAsset())
                        .costCenterInitial(asset.getCostCenterFinal())
                        .costCenterFinal(null)
                        .quantityInitial(asset.getQuantityFinal())
                        .quantityFinal(null)
                        .build()
            );
        }
    }

    private void insertAssetsFromAsset(Long inventoryId, List<Asset> assets) {
        for (Asset asset : assets) {
            create(
                    InventoryAsset.builder()
                        .inventory(inventoryRepository.findById(inventoryId))
                        .asset(asset)
                        .costCenterInitial(asset.getCostCenter())
                        .costCenterFinal(null)
                        .quantityInitial(0.0)
                        .quantityFinal(null)
                        .build()
            );
        }
    }

    @Operation(
            summary = "Retrieve inventory detail",
            description = "Fetches detailed information about an inventory, including its status and scanned assets.",
            responses = @ApiResponse(responseCode = "200", description = "Successfully retrieved inventory details")
    )
    @Override
    public Result<InventoryDetail> getInventoryDetail(Long inventoryId) {
        Result<InventoryDetail> result = new Result<>();
        return result.entityFound(getDetail(inventoryId));
    }

    private InventoryDetail getDetail(Long inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId);
        if (dateBefore(inventory.getStartDate()))
            return InventoryDetail
                    .builder()
                    .status(InventoryStatus.NOT_STARTED)
                    .build();

        return
                InventoryDetail
                    .builder()
                        .status(dateAfter(new Date(), inventory.getEndDate()) ? InventoryStatus.FINISHED : InventoryStatus.NOT_FINISHED)
                        .assetsScanned(inventoryAssetRepository.countAllAssetsScannedByInventoryId(inventoryId))
                        .finishedCostCenters(inventoryAssetRepository.countAllAssetsScannedByInventoryId(inventoryId))
                        .valueLost(inventoryAssetRepository.calculateTotalDifferenceValue(inventoryId))
                        .differences(inventoryAssetRepository.findByInventoryIdWithDifferences(inventoryId))
                        .build();
    }

    @Operation(
            summary = "Set scanned assets for inventory",
            description = "Marks assets as scanned in the given inventory and updates their details.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully scanned and updated assets"),
                    @ApiResponse(responseCode = "404", description = "Inventory or assets not found")
            }
    )
    @Override
    public Result<Boolean> setAssetsScanned(Long inventoryId, List<ScanAssetDtoCreate> scannedAssets) {
        Result<Boolean> result = new Result<>();
        result.setSuccess(true);

        if(inventoryRepository.findById(inventoryId) == null) {
            return result.entityNotFound("Inventory not found!");
        }
        List<InventoryAsset> inventoryAssets = new ArrayList<>();

        for (ScanAssetDtoCreate asset : scannedAssets) {
            InventoryAsset inventoryAsset = inventoryAssetRepository.findByInventoryIdAndAssetId(inventoryId, asset.getAssetId());
            if(inventoryAsset == null) {
                result.entityNotFound(asset.getAssetId() + " assetId not found!");
                continue;
            }

            inventoryAsset.setQuantityFinal(asset.getQuantity());
            inventoryAsset.setCostCenterFinal(costCenterRepository.findById(asset.getCostCenterId()));
            inventoryAsset.setModifiedAt(new Date());

            inventoryAssets.add(inventoryAsset);
        }

        if(result.isSuccess()) {
            inventoryAssetRepository.saveAll(inventoryAssets);
            result.setMessage(scannedAssets.size() + " assets modified!");
        }

        return result;
    }
}
