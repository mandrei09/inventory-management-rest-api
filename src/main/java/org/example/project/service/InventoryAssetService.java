package org.example.project.service;

import org.example.project.dto.inventoryAsset.InventoryAssetDtoCreate;
import org.example.project.dto.inventoryAsset.InventoryAssetDtoUpdate;
import org.example.project.dto.others.InventoryDetail;
import org.example.project.dto.others.ScanAssetDtoCreate;
import org.example.project.model.Asset;
import org.example.project.model.Inventory;
import org.example.project.model.InventoryAsset;
import org.example.project.repository.IInventoryAssetRepository;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseService;
import org.example.project.service.interfaces.IAssetService;
import org.example.project.service.interfaces.ICostCenterService;
import org.example.project.service.interfaces.IInventoryAssetService;
import org.example.project.service.interfaces.IInventoryService;
import org.example.project.utils.InventoryStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryAssetService extends BaseService<InventoryAsset, InventoryAssetDtoCreate, InventoryAssetDtoUpdate> implements IInventoryAssetService {

    private final IInventoryAssetRepository inventoryAssetRepository;
    private final IInventoryService inventoryService;
    private final IAssetService assetService;
    private final ICostCenterService costCenterService;

    public InventoryAssetService(IInventoryAssetRepository inventoryAssetRepository, IInventoryService inventoryService,
                                 IAssetService assetService, ICostCenterService costCenterService) {
        super(inventoryAssetRepository);
        this.inventoryAssetRepository = inventoryAssetRepository;
        this.inventoryService = inventoryService;
        this.assetService = assetService;
        this.costCenterService = costCenterService;
    }

    @Override
    public InventoryAsset mapToModel(InventoryAssetDtoCreate dto) {
        return InventoryAsset.builder()
                .inventory(inventoryService.findById(dto.getInventoryId()))
                .asset(assetService.findById(dto.getAssetId()))
                .costCenterInitial(costCenterService.findById(dto.getCostCenterInitialId()))
                .costCenterFinal(costCenterService.findById(dto.getCostCenterFinalId()))
                .quantityInitial(dto.getQuantityInitial())
                .quantityFinal(dto.getQuantityFinal())
            .build();
    }

    @Override
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
    public Result<InventoryAsset> updateFromDto(InventoryAsset inventoryAsset, InventoryAssetDtoUpdate dto) {
        Result<InventoryAsset> result = new Result<>();

        if(dto.getInventoryId() != null) {
            var inventory = inventoryService.findById(dto.getInventoryId());
            if(inventory != null) {
                inventoryAsset.setInventory(inventory);
            }
            else {
                result.entityNotFound("Inventory not found!");
            }
        }

        if(dto.getAssetId() != null) {
            var asset = assetService.findById(dto.getAssetId());
            if(asset != null) {
                inventoryAsset.setAsset(asset);
            }
            else {
                result.entityNotFound("Asset not found!");
            }
        }

        if(dto.getCostCenterInitialId() != null) {
            var costCenterInitial = costCenterService.findById(dto.getCostCenterInitialId());
            if(costCenterInitial != null) {
                inventoryAsset.setCostCenterInitial(costCenterInitial);
            }
            else {
                result.entityNotFound("CostCenterInitial not found!");
            }
        }

        if(dto.getCostCenterFinalId() != null) {
            var costCenterFinal = costCenterService.findById(dto.getCostCenterFinalId());
            if(costCenterFinal != null) {
                inventoryAsset.setCostCenterFinal(costCenterFinal);
            }
            else {
                result.entityNotFound("CostCenterFinal not found!");
            }
        }

        return result;
    }

    @Override
    public Result<Integer> insertAssetsIntoNewInventory(Long newInventoryId, Long companyId) {
        Result<Integer> result = new Result<>();
        Long lastInventoryId = inventoryService.getLastInventoryIdByCompanyId(companyId);

        List<InventoryAsset> oldInventoryAssets
                = inventoryAssetRepository.findAllByInventoryIdAndCompanyId(lastInventoryId, companyId);

        if(oldInventoryAssets != null && !oldInventoryAssets.isEmpty()) {
            insertAssetsFromInventoryAsset(newInventoryId, oldInventoryAssets);
            return result.entityFound(oldInventoryAssets.size());
        }
        else {
            List<Asset> assets = assetService.findAllByCompanyId(companyId);
            insertAssetsFromAsset(newInventoryId, assets);
            return result.entityFound(assets.size());
        }
    }

    private void insertAssetsFromInventoryAsset(Long inventoryId, List<InventoryAsset> assets) {
        for(InventoryAsset asset : assets) {
            create(
                    InventoryAsset.builder()
                        .inventory(inventoryService.findById(inventoryId))
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
                        .inventory(inventoryService.findById(inventoryId))
                        .asset(asset)
                        .costCenterInitial(asset.getCostCenter())
                        .costCenterFinal(null)
                        .quantityInitial(0.0)
                        .quantityFinal(null)
                        .build()
            );
        }
    }

    @Override
    public Result<InventoryDetail> getInventoryDetail(Long inventoryId) {
        Result<InventoryDetail> result = new Result<>();
        return result.entityFound(getDetail(inventoryId));
    }

    private InventoryDetail getDetail(Long inventoryId) {
        Inventory inventory = inventoryService.findById(inventoryId);
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

    @Override
    public Result<Boolean> setAssetsScanned(Long inventoryId, List<ScanAssetDtoCreate> scannedAssets) {
        Result<Boolean> result = new Result<Boolean>();
        result.setSuccess(true);

        if(inventoryService.findById(inventoryId) == null) {
            return result.entityNotFound("Inventory not found!");
        }
        List<InventoryAsset> inventoryAssets = new ArrayList<>();

        for (ScanAssetDtoCreate asset : scannedAssets) {
            InventoryAsset inventoryAsset = inventoryAssetRepository.findByInventoryIdAnAssetId(inventoryId, asset.getAssetId());
            if(inventoryAsset == null) {
                result.entityNotFound(asset.getAssetId() + " assetId not found!");
                continue;
            }

            inventoryAsset.setQuantityFinal(asset.getQuantity());
            inventoryAsset.setCostCenterFinal(costCenterService.findById(asset.getCostCenterId()));
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
