package org.example.project.service;

import org.example.project.dto.department.DepartmentDtoCreate;
import org.example.project.dto.department.DepartmentDtoUpdate;
import org.example.project.dto.division.DivisionDtoCreate;
import org.example.project.dto.division.DivisionDtoUpdate;
import org.example.project.model.Department;
import org.example.project.model.Division;
import org.example.project.repository.ICompanyRepository;
import org.example.project.repository.IDepartmentRepository;
import org.example.project.repository.IDivisionRepository;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseEntityService;
import org.springframework.stereotype.Service;

@Service
public class DivisionService extends BaseEntityService<Division, DivisionDtoCreate, DivisionDtoUpdate> {

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
                .department((departmentRepository.findById(dto.getDepartmentId()).orElse(null)))
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
            var department = departmentRepository.findById(dto.getDepartmentId()).orElse(null);
            if(department != null) {
                division.setDepartment(department);
            }
            else {
                result.entityNotFound("Department");
            }
        }

        return result;
    }
}
