package org.example.project.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.project.dto.company.CompanyDtoCreate;
import org.example.project.dto.company.CompanyDtoUpdate;
import org.example.project.model.Company;
import org.example.project.repository.ICompanyRepository;
import org.example.project.repository.IEmployeeRepository;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseService;
import org.example.project.service.interfaces.ICompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService extends BaseService<Company, CompanyDtoCreate, CompanyDtoUpdate> implements ICompanyService {

    private final ICompanyRepository companyRepository;
    private final IEmployeeRepository employeeRepository;

    public CompanyService(ICompanyRepository companyRepository, IEmployeeRepository employeeRepository) {
        super(companyRepository);
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Operation(
            summary = "Map CompanyDtoCreate to Company",
            description = "Maps a CompanyDtoCreate object to a Company model object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Company successfully mapped")
            }
    )
    public Company mapToModel(CompanyDtoCreate dto) {
        return Company.builder()
                .manager(employeeRepository.findById(dto.getManagerId()))
                .code(dto.getCode().trim())
                .name(dto.getName().trim())
                .build();
    }

    @Override
    @Operation(
            summary = "Map Company to CompanyDtoCreate",
            description = "Maps a Company model object to a CompanyDtoCreate object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Company successfully mapped to DTO")
            }
    )
    public CompanyDtoCreate mapToDto(Company company) {
        return CompanyDtoCreate.builder()
                .code(trimStringOrNull(company.getCode()))
                .name(trimStringOrNull(company.getName()))
                .managerId(company.getManager() != null ? company.getManager().getId() : null)
                .build();
    }

    @Override
    @Operation(
            summary = "Update Company from DTO",
            description = "Updates a Company entity based on the provided CompanyDtoUpdate object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Company successfully updated"),
                    @ApiResponse(responseCode = "404", description = "Manager not found")
            }
    )
    public Result<Company> updateFromDto(Company company, CompanyDtoUpdate dto) {
        Result<Company> result = new Result<>();

        if(dto.getCode() != null) {
            company.setCode(dto.getCode().trim());
        }
        if(dto.getName() != null) {
            company.setName(dto.getName().trim());
        }
        if(dto.getManagerId() != null) {
            var manager = employeeRepository.findById(dto.getManagerId());
            if(manager != null) {
                company.setManager(manager);
            }
            else {
                result.entityNotFound("Manager not found!");
            }
        }

        return result;
    }
}
