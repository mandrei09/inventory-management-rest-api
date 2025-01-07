package org.example.project.service;

import org.example.project.dto.EmployeeDto;
import org.example.project.model.Employee;
import org.example.project.repository.EmployeeRepository;
import org.example.project.service.generic.BaseEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService extends BaseEntityService<Employee, EmployeeDto> {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        super(employeeRepository);
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAllByIsDeletedFalse().toList();
    }

    @Override
    public Employee mapToModel(EmployeeDto dto) {
        return Employee.builder()
            .internalCode(trimString(dto.getInternalCode()))
            .name(trimString(dto.getName()))
            .lastName(trimString(dto.getLastName()))
            .email(trimString(dto.getEmail()))
            .manager(employeeRepository.findById(dto.getManagerId()))
            .birthDate(dto.getBirthDate())
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
