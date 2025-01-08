package org.example.project.service;

import org.example.project.dto.costCenter.CostCenterDtoCreate;
import org.example.project.dto.costCenter.CostCenterDtoUpdate;
import org.example.project.dto.division.DivisionDtoCreate;
import org.example.project.dto.division.DivisionDtoUpdate;
import org.example.project.model.CostCenter;
import org.example.project.model.Division;
import org.example.project.repository.ICostCenterRepository;
import org.example.project.repository.IDepartmentRepository;
import org.example.project.repository.IDivisionRepository;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseEntityService;
import org.springframework.stereotype.Service;

@Service
public class CostCenterService extends BaseEntityService<CostCenter, CostCenterDtoCreate, CostCenterDtoUpdate> {

    private final ICostCenterRepository costCenterRepository;
    private final IDivisionRepository divisionRepository;

    public CostCenterService(ICostCenterRepository costCenterRepository, IDivisionRepository divisionRepository) {
        super(costCenterRepository);
        this.costCenterRepository = costCenterRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    public CostCenter mapToModel(CostCenterDtoCreate dto) {
        return CostCenter.builder()
                .division((divisionRepository.findById(dto.getDivisionId()).orElse(null)))
                .code(dto.getCode().trim())
                .name(dto.getName().trim())
            .build();
    }

    @Override
    public CostCenterDtoCreate mapToDto(CostCenter costCenter) {
        return CostCenterDtoCreate.builder()
                .code(trimStringOrNull(costCenter.getCode()))
                .name(trimStringOrNull(costCenter.getName()))
                .divisionId(costCenter.getDivision() != null ? costCenter.getDivision().getId() : null)
            .build();
    }

    @Override
    public Result<CostCenter> updateFromDto(CostCenter costCenter, CostCenterDtoUpdate dto) {
        Result<CostCenter> result = new Result<>();

        if(dto.getCode() != null) {
            costCenter.setCode(dto.getCode().trim());
        }
        if(dto.getName() != null) {
            costCenter.setName(dto.getName().trim());
        }
        if(dto.getDivisionId() != null) {
            var division = divisionRepository.findById(dto.getDivisionId()).orElse(null);
            if(division != null) {
                costCenter.setDivision(division);
            }
            else {
                result.entityNotFound("Division");
            }
        }

        return result;
    }
}
