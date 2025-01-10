package org.example.project.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.project.dto.costCenter.CostCenterDtoCreate;
import org.example.project.dto.costCenter.CostCenterDtoUpdate;
import org.example.project.model.CostCenter;
import org.example.project.model.Division;
import org.example.project.repository.IDivisionRepository;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseService;
import org.example.project.service.interfaces.ICostCenterService;
import org.example.project.utils.StatusMessages;
import org.springframework.stereotype.Service;

@Service
public class CostCenterService extends BaseService<CostCenter, CostCenterDtoCreate, CostCenterDtoUpdate> implements ICostCenterService {

    private final org.example.project.repository.ICostCenterRepository costCenterRepository;
    private final IDivisionRepository divisionRepository;

    public CostCenterService(org.example.project.repository.ICostCenterRepository costCenterRepository, IDivisionRepository divisionRepository) {
        super(costCenterRepository);
        this.costCenterRepository = costCenterRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    @Operation(
            summary = "Map CostCenterDtoCreate to CostCenter",
            description = "Maps a CostCenterDtoCreate object to a CostCenter model object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "CostCenter successfully mapped")
            }
    )
    public Result<CostCenter> mapToModel(CostCenterDtoCreate dto) {
        Result<CostCenter> result = new Result<>();

        Division division = divisionRepository.findById(dto.getDivisionId());

        if (division == null) {
            return result.entityNotFound(StatusMessages.DIVISION_NOT_FOUND);
        }

        return result.entityFound(
                CostCenter.builder()
                        .division(division)
                        .code(dto.getCode().trim())
                        .name(dto.getName().trim())
                        .build()
        );
    }

    @Override
    @Operation(
            summary = "Map CostCenter to CostCenterDtoCreate",
            description = "Maps a CostCenter model object to a CostCenterDtoCreate object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "CostCenter successfully mapped to DTO")
            }
    )
    public CostCenterDtoCreate mapToDto(CostCenter costCenter) {
        return CostCenterDtoCreate.builder()
                .code(trimStringOrNull(costCenter.getCode()))
                .name(trimStringOrNull(costCenter.getName()))
                .divisionId(costCenter.getDivision() != null ? costCenter.getDivision().getId() : null)
                .build();
    }

    @Override
    @Operation(
            summary = "Update CostCenter from DTO",
            description = "Updates a CostCenter entity based on the provided CostCenterDtoUpdate object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "CostCenter successfully updated"),
                    @ApiResponse(responseCode = "404", description = "Division not found")
            }
    )
    public Result<CostCenter> updateFromDto(CostCenter costCenter, CostCenterDtoUpdate dto) {
        Result<CostCenter> result = new Result<>();

        if (dto.getCode() != null) {
            costCenter.setCode(dto.getCode().trim());
        }
        if (dto.getName() != null) {
            costCenter.setName(dto.getName().trim());
        }
        if (dto.getDivisionId() != null) {
            var division = divisionRepository.findById(dto.getDivisionId());
            if (division != null) {
                costCenter.setDivision(division);
            } else {
                result.entityNotFound("Division not found!");
            }
        }

        return result;
    }
}