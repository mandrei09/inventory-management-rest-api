package org.example.project.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.project.dto.department.DepartmentDtoCreate;
import org.example.project.dto.department.DepartmentDtoUpdate;
import org.example.project.model.Company;
import org.example.project.model.Department;
import org.example.project.repository.ICompanyRepository;
import org.example.project.repository.IDepartmentRepository;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseService;
import org.example.project.service.interfaces.IDepartmentService;
import org.example.project.utils.StatusMessages;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService extends BaseService<Department, DepartmentDtoCreate, DepartmentDtoUpdate> implements IDepartmentService {

    private final IDepartmentRepository departmentRepository;
    private final ICompanyRepository companyRepository;

    public DepartmentService(IDepartmentRepository departmentRepository, ICompanyRepository companyRepository) {
        super(departmentRepository);
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    @Operation(
            summary = "Map DepartmentDtoCreate to Department",
            description = "Maps a DepartmentDtoCreate object to a Department model object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Department successfully mapped")
            }
    )
    public Result<Department> mapToModel(DepartmentDtoCreate dto) {
        Result<Department> result = new Result<>();

        Company company = companyRepository.findById(dto.getCompanyId());

        if (company == null) {
            return result.entityNotFound(StatusMessages.COMPANY_NOT_FOUND);
        }

        return result.entityFound(
            Department.builder()
                .company(company)
                .code(dto.getCode().trim())
                .name(dto.getName().trim())
                .build()
        );
    }

    @Override
    @Operation(
            summary = "Map Department to DepartmentDtoCreate",
            description = "Maps a Department model object to a DepartmentDtoCreate object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Department successfully mapped to DTO")
            }
    )
    public DepartmentDtoCreate mapToDto(Department department) {
        return DepartmentDtoCreate.builder()
                .code(trimStringOrNull(department.getCode()))
                .name(trimStringOrNull(department.getName()))
                .companyId(department.getCompany() != null ? department.getCompany().getId() : null)
                .build();
    }

    @Override
    @Operation(
            summary = "Update Department from DTO",
            description = "Updates a Department entity based on the provided DepartmentDtoUpdate object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Department successfully updated"),
                    @ApiResponse(responseCode = "404", description = "Company not found")
            }
    )
    public Result<Department> updateFromDto(Department department, DepartmentDtoUpdate dto) {
        Result<Department> result = new Result<>();

        if (dto.getCode() != null) {
            department.setCode(dto.getCode().trim());
        }
        if (dto.getName() != null) {
            department.setName(dto.getName().trim());
        }
        if (dto.getCompanyId() != null) {
            var company = companyRepository.findById(dto.getCompanyId());
            if (company != null) {
                department.setCompany(company);
            } else {
                result.entityNotFound("Company not found!");
            }
        }

        return result;
    }
}
