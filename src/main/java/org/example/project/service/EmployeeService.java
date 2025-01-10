package org.example.project.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.project.dto.employee.EmployeeDtoCreate;
import org.example.project.dto.employee.EmployeeDtoUpdate;
import org.example.project.model.Company;
import org.example.project.model.Employee;
import org.example.project.repository.ICompanyRepository;
import org.example.project.repository.IEmployeeRepository;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseService;
import org.example.project.service.interfaces.IEmployeeService;
import org.example.project.utils.StatusMessages;
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

    @Operation(
            summary = "Retrieve all employees",
            description = "Fetches all employees that are not marked as deleted.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved employees")
            }
    )
    public List<Employee> getAll() {
        return employeeRepository.findAllByIsDeletedFalse();
    }

    @Override
    @Operation(
            summary = "Map EmployeeDtoCreate to Employee",
            description = "Converts an EmployeeDtoCreate object into an Employee entity.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully mapped DTO to entity"),
                    @ApiResponse(responseCode = "404", description = StatusMessages.MANAGER_NOT_FOUND)
            }
    )
    public Result<Employee> mapToModel(EmployeeDtoCreate dto) {
        Result<Employee> result = new Result<>();
        result.setSuccess(true);

        Employee manager = null; Company company = null;

        manager = employeeRepository.findById(dto.getManagerId());

        if(dto.getManagerId() != null) {
            if (manager == null) {
                result.entityNotFound(dto.getManagerId(), StatusMessages.MANAGER_NOT_FOUND);
            }
        }

        if(dto.getCompanyId() != null) {
            company = companyRepository.findById(dto.getCompanyId());

            if (company == null) {
                result.entityNotFound(dto.getCompanyId(), StatusMessages.COMPANY_NOT_FOUND);
            }
        }

        if(!result.isSuccess())
            return result;

        return result.entityFound(
            Employee.builder()
                    .internalCode(trimStringOrNull(dto.getInternalCode()))
                    .name(trimStringOrNull(dto.getName()))
                    .lastName(trimStringOrNull(dto.getLastName()))
                    .email(trimStringOrNull(dto.getEmail()))
                    .manager(manager)
                    .company(company)
                    .birthDate(dto.getBirthDate())
                    .build()
            );
    }

    @Override
    @Operation(
            summary = "Map Employee to EmployeeDtoCreate",
            description = "Converts an Employee entity into an EmployeeDtoCreate object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully mapped entity to DTO")
            }
    )
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
    @Operation(
            summary = "Update Employee from DTO",
            description = "Updates an existing Employee entity with data from an EmployeeDtoUpdate object.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated the employee"),
                    @ApiResponse(responseCode = "404", description = "Manager or company not found")
            }
    )
    public Result<Employee> updateFromDto(Employee employee, EmployeeDtoUpdate dto) {
        Result<Employee> result = new Result<>();

        if (dto.getInternalCode() != null)
            employee.setInternalCode(dto.getInternalCode().trim());

        if (dto.getName() != null)
            employee.setName(dto.getName().trim());

        if (dto.getLastName() != null)
            employee.setLastName(dto.getLastName().trim());

        if (dto.getEmail() != null)
            employee.setEmail(dto.getEmail().trim());

        if (dto.getManagerId() != null) {
            var manager = employeeRepository.findById(dto.getManagerId());
            if (manager != null) {
                employee.setManager(manager);
            } else {
                result.entityNotFound(dto.getManagerId(), StatusMessages.MANAGER_NOT_FOUND);
            }
        }

        if (dto.getCompanyId() != null) {
            var company = companyRepository.findById(dto.getCompanyId());

            if (company != null) {
                employee.setCompany(company);
            } else {
                result.entityNotFound(dto.getCompanyId(), StatusMessages.COMPANY_NOT_FOUND);
            }
        }

        if (dto.getBirthDate() != null) {
            employee.setBirthDate(dto.getBirthDate());
        }

        if(result.getMessages() == null) result.entityFound(employee);
        return result;
    }
}
