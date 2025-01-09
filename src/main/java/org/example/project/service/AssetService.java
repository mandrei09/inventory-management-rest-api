package org.example.project.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.project.dto.asset.AssetDtoCreate;
import org.example.project.dto.asset.AssetDtoUpdate;
import org.example.project.model.Asset;
import org.example.project.model.CostCenter;
import org.example.project.model.Employee;
import org.example.project.repository.IAssetRepository;
import org.example.project.repository.ICostCenterRepository;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseService;
import org.example.project.service.interfaces.IAssetService;
import org.example.project.utils.ErrorCodes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService extends BaseService<Asset, AssetDtoCreate, AssetDtoUpdate> implements IAssetService {

    private final IAssetRepository assetRepository;
    private final ICostCenterRepository costCenterRepository;

    public AssetService(IAssetRepository assetRepository, ICostCenterRepository costCenterRepository) {
        super(assetRepository);
        this.assetRepository = assetRepository;
        this.costCenterRepository = costCenterRepository;
    }

    @Override
    @Operation(
            summary = "Map AssetDtoCreate to Asset",
            description = "Maps an AssetDtoCreate object to an Asset model object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Asset successfully mapped")
            }
    )
    public Result<Asset> mapToModel(AssetDtoCreate dto) {
        Result<Asset> result = new Result<>();
        CostCenter costCenter = costCenterRepository.findById(dto.getCostCenterId());

        if (costCenter == null) {
            return result.entityNotFound(ErrorCodes.COST_CENTER_NOT_FOUND);
        }

        return result.entityFound(
            Asset.builder()
                .costCenter(costCenter)
                .code(dto.getCode().trim())
                .name(dto.getName().trim())
                .value(dto.getValue())
                .acquisitionDate(dto.getAcquisitionDate())
                .build()
        );
    }

    @Override
    @Operation(
            summary = "Map Asset to AssetDtoCreate",
            description = "Maps an Asset model object to an AssetDtoCreate object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Asset successfully mapped to DTO")
            }
    )
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
    @Operation(
            summary = "Update Asset from DTO",
            description = "Updates an Asset entity based on the provided AssetDtoUpdate object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Asset successfully updated"),
                    @ApiResponse(responseCode = "404", description = "Cost center not found")
            }
    )
    public Result<Asset> updateFromDto(Asset asset, AssetDtoUpdate dto) {
        Result<Asset> result = new Result<>();

        if(dto.getCode() != null) {
            asset.setCode(dto.getCode().trim());
        }

        if(dto.getName() != null) {
            asset.setName(dto.getName().trim());
        }

        if(dto.getValue() != null) {
            asset.setValue(dto.getValue());
        }

        if(dto.getAcquisitionDate() != null) {
            asset.setAcquisitionDate(dto.getAcquisitionDate());
        }

        if(dto.getCostCenterId() != null) {
            var costCenter = costCenterRepository.findById(dto.getCostCenterId());
            if(costCenter != null) {
                asset.setCostCenter(costCenter);
            }
            else {
                result.entityNotFound("Cost center not found!");
            }
        }

        return result;
    }

    @Override
    @Operation(
            summary = "Find all Assets by Company ID",
            description = "Retrieves a list of assets associated with a specific company, excluding deleted assets.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of assets found"),
                    @ApiResponse(responseCode = "404", description = "No assets found for the given company ID")
            }
    )
    public List<Asset> findAllByCompanyId(@Parameter(description = "Company ID to find assets by") Long companyId) {
        return assetRepository.findAllByCompanyIdAndIsDeletedFalse(companyId);
    }
}
