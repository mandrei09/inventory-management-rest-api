package org.example.project.service;

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

    public List<Company> getAll() {
        return companyRepository.findAllByIsDeletedFalse().toList();
    }

    @Override
    public Company mapToModel(CompanyDtoCreate dto) {
        return Company.builder()
                .manager(employeeRepository.findById(dto.getManagerId()))
                .code(dto.getCode().trim())
                .name(dto.getName().trim())
            .build();
    }

    @Override
    public CompanyDtoCreate mapToDto(Company company) {
        return CompanyDtoCreate.builder()
                .code(trimStringOrNull(company.getCode()))
                .name(trimStringOrNull(company.getName()))
                .managerId(company.getManager() != null ? company.getManager().getId() : null)
            .build();
    }

    @Override
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
