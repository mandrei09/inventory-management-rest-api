package org.example.project.service;

import org.example.project.dto.division.DivisionDtoCreate;
import org.example.project.dto.division.DivisionDtoUpdate;
import org.example.project.model.Division;
import org.example.project.repository.IDepartmentRepository;
import org.example.project.repository.IDivisionRepository;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseService;
import org.example.project.service.interfaces.IAssetService;
import org.example.project.service.interfaces.IDivisionService;
import org.springframework.stereotype.Service;

@Service
public class DivisionService extends BaseService<Division, DivisionDtoCreate, DivisionDtoUpdate> implements IDivisionService {

    private final IDivisionRepository divisionRepository;
    private final IDepartmentRepository departmentRepository;

    public DivisionService(IDivisionRepository divisionRepository, IDepartmentRepository departmentRepository) {
        super(divisionRepository);
        this.divisionRepository = divisionRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Division mapToModel(DivisionDtoCreate dto) {
        return Division.builder()
                .department(departmentRepository.findById(dto.getDepartmentId()))
                .code(dto.getCode().trim())
                .name(dto.getName().trim())
            .build();
    }

    @Override
    public DivisionDtoCreate mapToDto(Division division) {
        return DivisionDtoCreate.builder()
                .code(trimStringOrNull(division.getCode()))
                .name(trimStringOrNull(division.getName()))
                .departmentId(division.getDepartment() != null ? division.getDepartment().getId() : null)
            .build();
    }

    @Override
    public Result<Division> updateFromDto(Division division, DivisionDtoUpdate dto) {
        Result<Division> result = new Result<>();

        if(dto.getCode() != null) {
            division.setCode(dto.getCode().trim());
        }
        if(dto.getName() != null) {
            division.setName(dto.getName().trim());
        }
        if(dto.getDepartmentId() != null) {
            var department = departmentRepository.findById(dto.getDepartmentId());
            if(department != null) {
                division.setDepartment(department);
            }
            else {
                result.entityNotFound("Department not found!");
            }
        }

        return result;
    }
}
