package org.example.project.service;

import org.example.project.dto.CompanyDto;
import org.example.project.dto.EmployeeDto;
import org.example.project.model.Company;
import org.example.project.model.Employee;
import org.example.project.repository.CompanyRepository;
import org.example.project.repository.EmployeeRepository;
import org.example.project.service.generic.BaseEntityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService extends BaseEntityService<Company, CompanyDto> {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        super(companyRepository);
        this.companyRepository = companyRepository;
    }

    public List<Company> getAll() {
        return companyRepository.findAllByIsDeletedFalse().toList();
    }

    @Override
    public Company mapToModel(CompanyDto dto) {
        return Company.builder()
                .manager(dto.getManager())

            .build();
    }

    @Override
    public EmployeeDto mapToDto(Employee employee) {
        return EmployeeDto.builder()
            .internalCode(employee.getInternalCode())
            .name(employee.getName())
            .lastName(employee.getLastName())
            .email(employee.getEmail())
            .manager(employee.getManager())
            .birthDate(employee.getBirthDate())
            .build();
    }

    @Override
    public void updateFromDto(Employee employee, EmployeeDto dto) {
        employee.setInternalCode(trimString(dto.getInternalCode()));
        employee.setName(trimString(dto.getName()));
        employee.setLastName(trimString(dto.getLastName()));
        employee.setEmail(trimString(dto.getEmail()));
        employee.setManager(dto.getManager());
        employee.setBirthDate(employee.getBirthDate());
    }
}
