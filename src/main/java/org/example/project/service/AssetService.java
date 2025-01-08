package org.example.project.service;

import org.example.project.dto.asset.AssetDtoCreate;
import org.example.project.dto.asset.AssetDtoUpdate;
import org.example.project.dto.inventory.InventoryDtoCreate;
import org.example.project.dto.inventory.InventoryDtoUpdate;
import org.example.project.model.Asset;
import org.example.project.model.Inventory;
import org.example.project.repository.IAssetRepository;
import org.example.project.repository.ICompanyRepository;
import org.example.project.repository.ICostCenterRepository;
import org.example.project.repository.IInventoryRepository;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseEntityService;
import org.springframework.stereotype.Service;

@Service
public class AssetService extends BaseEntityService<Asset, AssetDtoCreate, AssetDtoUpdate> {

    private final IAssetRepository assetRepository;
    private final ICostCenterRepository costCenterRepository;

    public AssetService(IAssetRepository assetRepository, ICostCenterRepository costCenterRepository) {
        super(assetRepository);
        this.assetRepository = assetRepository;
        this.costCenterRepository = costCenterRepository;
    }

    @Override
    public Asset mapToModel(AssetDtoCreate dto) {
        return Asset.builder()
                .costCenter((costCenterRepository.findById(dto.getCostCenterId()).orElse(null)))
                .code(dto.getCode().trim())
                .name(dto.getName().trim())
                .value(dto.getValue())
                .acquisitionDate(dto.getAcquisitionDate())
            .build();
    }

    @Override
    public AssetDtoCreate mapToDto(Asset asset) {
        return AssetDtoCreate.builder()
                .costCenterId(asset.getCostCenter() != null ? asset.getCostCenter().getId() : null)
                .code(asset.getCode().trim())
                .name(asset.getName().trim())
                .value(asset.getValue())
                .acquisitionDate(asset.getAcquisitionDate())
            .build();
    }

    @Override
    public Result<Asset> updateFromDto(Asset asset, AssetDtoUpdate dto) {
        Result<Asset> result = new Result<>();

        if(dto.getCode() != null) {
            asset.setCode(dto.getCode().trim());
        }

        if(dto.getName() != null) {
            asset.setName(dto.getName().trim());
        }

        if(dto.getValue() != null) {
            asset.setValue(asset.getValue());
        }

        if(dto.getAcquisitionDate() != null) {
            asset.setAcquisitionDate(asset.getAcquisitionDate());
        }

        if(dto.getCostCenterId() != null) {
            var costCenter = costCenterRepository.findById(dto.getCostCenterId()).orElse(null);
            if(costCenter != null) {
                asset.setCostCenter(costCenter);
            }
            else {
                result.entityNotFound("Cost center");
            }
        }

        return result;
    }
}
