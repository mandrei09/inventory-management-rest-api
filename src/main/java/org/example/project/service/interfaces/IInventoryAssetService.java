package org.example.project.service.interfaces;

import org.example.project.dto.inventoryAsset.InventoryAssetDtoCreate;
import org.example.project.dto.inventoryAsset.InventoryAssetDtoUpdate;
import org.example.project.dto.others.InventoryDetail;
import org.example.project.dto.others.ScanAssetDtoCreate;
import org.example.project.model.InventoryAsset;
import org.example.project.result.Result;
import org.example.project.service.generic.IBaseService;

import java.util.List;

public interface IInventoryAssetService extends IBaseService<InventoryAsset, InventoryAssetDtoCreate, InventoryAssetDtoUpdate> {
    Result<Integer> insertAssetsIntoNewInventory(Long newInventoryId, Long companyId);
    Result<InventoryDetail> getInventoryDetail(Long inventoryId);
    Result<Boolean> setAssetsScanned(Long inventoryId, List<ScanAssetDtoCreate> scannedAssets);
}
