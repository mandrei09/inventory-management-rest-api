package org.example.project.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.project.dto.division.DivisionDtoCreate;
import org.example.project.dto.division.DivisionDtoUpdate;
import org.example.project.model.Division;
import org.example.project.repository.IDepartmentRepository;
import org.example.project.repository.IDivisionRepository;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseService;
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
    @Operation(
            summary = "Map DivisionDtoCreate to Division",
            description = "Maps a DivisionDtoCreate object to a Division entity.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Division successfully mapped"),
                    @ApiResponse(responseCode = "404", description = "Department not found")
            }
    )
    public Division mapToModel(DivisionDtoCreate dto) {
        return Division.builder()
                .department(departmentRepository.findById(dto.getDepartmentId()))
                .code(dto.getCode().trim())
                .name(dto.getName().trim())
                .build();
    }

    @Override
    @Operation(
            summary = "Map Division to DivisionDtoCreate",
            description = "Maps a Division entity to a DivisionDtoCreate object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Division successfully mapped to DTO")
            }
    )
    public DivisionDtoCreate mapToDto(Division division) {
        return DivisionDtoCreate.builder()
                .code(trimStringOrNull(division.getCode()))
                .name(trimStringOrNull(division.getName()))
                .departmentId(division.getDepartment() != null ? division.getDepartment().getId() : null)
                .build();
    }

    @Override
    @Operation(
            summary = "Update Division from DTO",
            description = "Updates a Division entity based on the provided DivisionDtoUpdate object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Division successfully updated"),
                    @ApiResponse(responseCode = "404", description = "Department not found")
            }
    )
    public Result<Division> updateFromDto(Division division, DivisionDtoUpdate dto) {
        Result<Division> result = new Result<>();

        if (dto.getCode() != null) {
            division.setCode(dto.getCode().trim());
        }
        if (dto.getName() != null) {
            division.setName(dto.getName().trim());
        }
        if (dto.getDepartmentId() != null) {
            var department = departmentRepository.findById(dto.getDepartmentId());
            if (department != null) {
                division.setDepartment(department);
            } else {
                result.entityNotFound("Department not found!");
            }
        }

        return result;
    }
}
