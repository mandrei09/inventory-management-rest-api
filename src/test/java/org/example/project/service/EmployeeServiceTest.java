package org.example.project.service;

import org.example.project.dto.employee.EmployeeDtoCreate;
import org.example.project.dto.employee.EmployeeDtoUpdate;
import org.example.project.model.Company;
import org.example.project.model.Employee;
import org.example.project.repository.ICompanyRepository;
import org.example.project.repository.IEmployeeRepository;
import org.example.project.result.Result;
import org.example.project.utils.StatusMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("h2")
public class EmployeeServiceTest {

    @Mock
    private IEmployeeRepository employeeRepository;

    @Mock
    private ICompanyRepository companyRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testGetAllEmployees() {
        // Arrange
        List<Employee> employees = List.of(new Employee(), new Employee());
        when(employeeRepository.findAllByIsDeletedFalse()).thenReturn(employees);

        // Act
        List<Employee> result = employeeService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(employeeRepository).findAllByIsDeletedFalse();
    }

    @Test
    void testMapToModelWithSuccess() {
        // Arrange
        EmployeeDtoCreate employeeDto = EmployeeDtoCreate.builder()
                .internalCode("E001")
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .managerId(1L)
                .companyId(1L)
                .birthDate(new Date())
                .build();

        Company company = new Company();
        company.setId(1L);
        Employee manager = new Employee();
        manager.setId(1L);

        when(employeeRepository.findById(1L)).thenReturn(manager);
        when(companyRepository.findById(1L)).thenReturn(company);

        // Act
        Result<Employee> result = employeeService.mapToModel(employeeDto);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals(employeeDto.getInternalCode(), result.getObject().getInternalCode());
        assertEquals(employeeDto.getName(), result.getObject().getName());
        assertEquals(employeeDto.getLastName(), result.getObject().getLastName());
        assertEquals(manager, result.getObject().getManager());
        assertEquals(company, result.getObject().getCompany());
        verify(employeeRepository).findById(1L);
        verify(companyRepository).findById(1L);
    }

    @Test
    void testMapToModelWithManagerNotFound() {
        // Arrange
        EmployeeDtoCreate employeeDto = EmployeeDtoCreate.builder()
                .internalCode("E001")
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .managerId(999L) // Invalid manager ID
                .companyId(1L)
                .birthDate(new Date())
                .build();

        when(employeeRepository.findById(999L)).thenReturn(null); // Manager not found

        // Act
        Result<Employee> result = employeeService.mapToModel(employeeDto);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(String.format("Id: %d, Message: %s", 999L, StatusMessages.MANAGER_NOT_FOUND), result.getMessages().get(0));
        verify(employeeRepository).findById(999L);
    }

    @Test
    void testMapToModelWithCompanyNotFound() {
        // Arrange
        EmployeeDtoCreate employeeDto = EmployeeDtoCreate.builder()
                .internalCode("E001")
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .managerId(1L)
                .companyId(999L) // Invalid company ID
                .birthDate(new Date())
                .build();

        when(employeeRepository.findById(1L)).thenReturn(new Employee()); // Valid manager
        when(companyRepository.findById(999L)).thenReturn(null); // Company not found

        // Act
        Result<Employee> result = employeeService.mapToModel(employeeDto);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(String.format("Id: %d, Message: %s", 999L, StatusMessages.COMPANY_NOT_FOUND), result.getMessages().get(0));
        verify(employeeRepository).findById(1L);
        verify(companyRepository).findById(999L);
    }

    @Test
    void testMapToDtoWithSuccess() {
        // Arrange
        Employee employee = new Employee();
        employee.setInternalCode("E001");
        employee.setName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setBirthDate(new Date());

        Employee manager = new Employee();
        manager.setId(1L);
        employee.setManager(manager);

        Company company = new Company();
        company.setId(1L);
        employee.setCompany(company);

        // Act
        EmployeeDtoCreate resultDto = employeeService.mapToDto(employee);

        // Assert
        assertEquals(employee.getInternalCode(), resultDto.getInternalCode());
        assertEquals(employee.getName(), resultDto.getName());
        assertEquals(employee.getLastName(), resultDto.getLastName());
        assertEquals(employee.getEmail(), resultDto.getEmail());
        assertEquals(manager.getId(), resultDto.getManagerId());
        assertEquals(company.getId(), resultDto.getCompanyId());
        assertEquals(employee.getBirthDate(), resultDto.getBirthDate());
    }

    @Test
    void testUpdateFromDtoWithSuccess() {
        // Arrange
        Employee employee = new Employee();
        employee.setInternalCode("E001");
        employee.setName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");

        EmployeeDtoUpdate employeeDto = new EmployeeDtoUpdate();
        employeeDto.setInternalCode("E002");
        employeeDto.setName("John Updated");
        employeeDto.setLastName("Doe Updated");
        employeeDto.setEmail("john.updated@example.com");
        employeeDto.setManagerId(1L);
        employeeDto.setCompanyId(1L);
        employeeDto.setBirthDate(new Date());

        Company company = new Company();
        company.setId(1L);
        Employee manager = new Employee();
        manager.setId(1L);

        when(employeeRepository.findById(1L)).thenReturn(manager);
        when(companyRepository.findById(1L)).thenReturn(company);

        // Act
        Result<Employee> result = employeeService.updateFromDto(employee, employeeDto);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals(employeeDto.getInternalCode(), employee.getInternalCode());
        assertEquals(employeeDto.getName(), employee.getName());
        assertEquals(employeeDto.getLastName(), employee.getLastName());
        assertEquals(employeeDto.getEmail(), employee.getEmail());
        assertEquals(manager, employee.getManager());
        assertEquals(company, employee.getCompany());
        assertEquals(employeeDto.getBirthDate(), employee.getBirthDate());
        verify(employeeRepository).findById(1L);
        verify(companyRepository).findById(1L);
    }

    @Test
    void testUpdateFromDtoWithManagerNotFound() {
        // Arrange
        Employee employee = new Employee();
        employee.setInternalCode("E001");

        EmployeeDtoUpdate employeeDto = new EmployeeDtoUpdate();
        employeeDto.setManagerId(999L); // Invalid manager ID

        when(employeeRepository.findById(999L)).thenReturn(null); // Manager not found

        // Act
        Result<Employee> result = employeeService.updateFromDto(employee, employeeDto);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(String.format("Id: %d, Message: %s", 999L, StatusMessages.MANAGER_NOT_FOUND), result.getMessages().get(0));
    }

    @Test
    void testUpdateFromDtoWithCompanyNotFound() {
        // Arrange
        Employee employee = new Employee();
        employee.setInternalCode("E001");

        EmployeeDtoUpdate employeeDto = new EmployeeDtoUpdate();
        employeeDto.setCompanyId(999L); // Invalid company ID

        when(companyRepository.findById(999L)).thenReturn(null); // Company not found

        // Act
        Result<Employee> result = employeeService.updateFromDto(employee, employeeDto);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(String.format("Id: %d, Message: %s", 999L, StatusMessages.COMPANY_NOT_FOUND), result.getMessages().get(0));
    }
}
