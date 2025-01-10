package org.example.project.service;

import org.example.project.dto.department.DepartmentDtoCreate;
import org.example.project.dto.department.DepartmentDtoUpdate;
import org.example.project.model.Company;
import org.example.project.model.Department;
import org.example.project.repository.ICompanyRepository;
import org.example.project.repository.IDepartmentRepository;
import org.example.project.result.Result;
import org.example.project.utils.StatusMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class DepartmentServiceTest {

    @Mock
    private IDepartmentRepository departmentRepository;

    @Mock
    private ICompanyRepository companyRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void testMapToModelWithSuccess() {
        // Arrange
        var departmentDto = DepartmentDtoCreate.builder()
                .code("D001")
                .name("Department 1")
                .companyId(1L)
                .build();

        var company = new Company();
        company.setId(1L);
        when(companyRepository.findById(1L)).thenReturn(company);

        // Act
        Result<Department> result = departmentService.mapToModel(departmentDto);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals(departmentDto.getCode(), result.getObject().getCode());
        assertEquals(departmentDto.getName(), result.getObject().getName());
        assertEquals(company, result.getObject().getCompany());
        verify(companyRepository).findById(1L);
    }

    @Test
    void testMapToModelWithCompanyNotFound() {
        // Arrange
        var departmentDto = DepartmentDtoCreate.builder()
                .code("D001")
                .name("Department 1")
                .companyId(999L)
                .build();

        when(companyRepository.findById(999L)).thenReturn(null);

        // Act
        Result<Department> result = departmentService.mapToModel(departmentDto);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(String.format("Id: %d, Message: %s", 999L, StatusMessages.COMPANY_NOT_FOUND), result.getMessages().get(0));
        verify(companyRepository).findById(999L);
    }

    @Test
    void testMapToDtoWithSuccess() {
        // Arrange
        var department = new Department();
        department.setCode("D001");
        department.setName("Department 1");
        var company = new Company();
        company.setId(1L);
        department.setCompany(company);

        // Act
        DepartmentDtoCreate resultDto = departmentService.mapToDto(department);

        // Assert
        assertEquals(department.getCode(), resultDto.getCode());
        assertEquals(department.getName(), resultDto.getName());
        assertEquals(company.getId(), resultDto.getCompanyId());
    }

    @Test
    void testUpdateFromDtoWithSuccess() {
        // Arrange
        var department = new Department();
        department.setCode("D001");
        department.setName("Department 1");

        var departmentDto = new DepartmentDtoUpdate();
        departmentDto.setCode("D002");
        departmentDto.setName("Updated Department");
        departmentDto.setCompanyId(1L);

        var company = new Company();
        company.setId(1L);
        when(companyRepository.findById(1L)).thenReturn(company);

        // Act
        Result<Department> result = departmentService.updateFromDto(department, departmentDto);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("D002", department.getCode());
        assertEquals("Updated Department", department.getName());
        assertEquals(company, department.getCompany());
        verify(companyRepository).findById(1L);
    }

    @Test
    void testUpdateFromDtoWithCompanyNotFound() {
        // Arrange
        var department = new Department();
        department.setCode("D001");
        department.setName("Department 1");

        var departmentDto = new DepartmentDtoUpdate();
        departmentDto.setCode("D002");
        departmentDto.setName("Updated Department");
        departmentDto.setCompanyId(999L);

        when(companyRepository.findById(999L)).thenReturn(null);

        // Act
        Result<Department> result = departmentService.updateFromDto(department, departmentDto);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(String.format("Id: %d, Message: %s", 999L, StatusMessages.COMPANY_NOT_FOUND), result.getMessages().get(0));
    }
}
