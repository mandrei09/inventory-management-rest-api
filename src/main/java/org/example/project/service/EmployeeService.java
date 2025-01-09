package org.example.project.service;

import org.example.project.dto.employee.EmployeeDtoCreate;
import org.example.project.dto.employee.EmployeeDtoUpdate;
import org.example.project.model.Employee;
import org.example.project.repository.ICompanyRepository;
import org.example.project.repository.IEmployeeRepository;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseService;
import org.example.project.service.interfaces.IAssetService;
import org.example.project.service.interfaces.IEmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService extends BaseService<Employee, EmployeeDtoCreate, EmployeeDtoUpdate> implements IEmployeeService {

    private final IEmployeeRepository employeeRepository;
    private final ICompanyRepository companyRepository;

    public EmployeeService(IEmployeeRepository employeeRepository, ICompanyRepository companyRepository) {
        super(employeeRepository);
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAllByIsDeletedFalse().toList();
    }

    @Override
    public Employee mapToModel(EmployeeDtoCreate dto) {
        return Employee.builder()
            .internalCode(trimStringOrNull(dto.getInternalCode()))
            .name(trimStringOrNull(dto.getName()))
            .lastName(trimStringOrNull(dto.getLastName()))
            .email(trimStringOrNull(dto.getEmail()))
            .manager(employeeRepository.findById(dto.getManagerId()))
            .birthDate(dto.getBirthDate())
            .build();
    }

    @Override
    public EmployeeDtoCreate mapToDto(Employee employee) {
        return EmployeeDtoCreate.builder()
            .internalCode(employee.getInternalCode())
            .name(employee.getName())
            .lastName(employee.getLastName())
            .email(employee.getEmail())
            .managerId(employee.getManager() != null ? employee.getManager().getId() : null)
            .companyId(employee.getCompany() != null ? employee.getCompany().getId() : null)
            .birthDate(employee.getBirthDate())
            .build();
    }

    @Override
    public Result<Employee> updateFromDto(Employee employee, EmployeeDtoUpdate dto) {
        Result<Employee> result = new Result<>();

        if(dto.getInternalCode() != null)
            employee.setInternalCode(dto.getInternalCode().trim());

        if(dto.getName() != null)
            employee.setName(dto.getName().trim());

        if(dto.getLastName() != null)
            employee.setLastName(dto.getLastName().trim());

        if(dto.getEmail() != null)
            employee.setEmail(dto.getEmail().trim());

        if(dto.getManagerId() != null) {
            var manager = employeeRepository.findById(dto.getManagerId());
            if(manager != null) {
                employee.setManager(manager);
            }
            else {
                result.entityNotFound("Manager not found!");
            }
        }

        if(dto.getCompanyId() != null) {
            var company = companyRepository.findById(dto.getCompanyId());
            if(company != null) {
                employee.setCompany(company);
            }
            else {
                result.entityNotFound("Company not found!");
            }
        }

        if(dto.getBirthDate() != null){
            employee.setBirthDate(employee.getBirthDate());
        }

        return result;
    }
}
