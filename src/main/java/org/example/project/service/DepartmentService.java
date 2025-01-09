package org.example.project.service;

import org.example.project.dto.department.DepartmentDtoCreate;
import org.example.project.dto.department.DepartmentDtoUpdate;
import org.example.project.model.Department;
import org.example.project.repository.ICompanyRepository;
import org.example.project.repository.IDepartmentRepository;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseService;
import org.example.project.service.interfaces.IAssetService;
import org.example.project.service.interfaces.IDepartmentService;
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
    public Department mapToModel(DepartmentDtoCreate dto) {
        return Department.builder()
                .company(companyRepository.findById(dto.getCompanyId()))
                .code(dto.getCode().trim())
                .name(dto.getName().trim())
            .build();
    }

    @Override
    public DepartmentDtoCreate mapToDto(Department department) {
        return DepartmentDtoCreate.builder()
                .code(trimStringOrNull(department.getCode()))
                .name(trimStringOrNull(department.getName()))
                .companyId(department.getCompany() != null ? department.getCompany().getId() : null)
            .build();
    }

    @Override
    public Result<Department> updateFromDto(Department department, DepartmentDtoUpdate dto) {
        Result<Department> result = new Result<>();

        if(dto.getCode() != null) {
            department.setCode(dto.getCode().trim());
        }
        if(dto.getName() != null) {
            department.setName(dto.getName().trim());
        }
        if(dto.getCompanyId() != null) {
            var company = companyRepository.findById(dto.getCompanyId());
            if(company != null) {
                department.setCompany(company);
            }
            else {
                result.entityNotFound("Company not found!");
            }
        }

        return result;
    }
}
