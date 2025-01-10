package org.example.project.service;

import org.example.project.dto.division.DivisionDtoCreate;
import org.example.project.dto.division.DivisionDtoUpdate;
import org.example.project.model.Department;
import org.example.project.model.Division;
import org.example.project.repository.IDepartmentRepository;
import org.example.project.repository.IDivisionRepository;
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
public class DivisionServiceTest {

    @Mock
    private IDivisionRepository divisionRepository;

    @Mock
    private IDepartmentRepository departmentRepository;

    @InjectMocks
    private DivisionService divisionService;

    @Test
    void testMapToModelWithSuccess() {
        // Arrange
        var divisionDto = DivisionDtoCreate.builder()
                .code("D001")
                .name("Division 1")
                .departmentId(1L)
                .build();

        var department = new Department();
        department.setId(1L);
        when(departmentRepository.findById(1L)).thenReturn(department);

        // Act
        Result<Division> result = divisionService.mapToModel(divisionDto);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals(divisionDto.getCode(), result.getObject().getCode());
        assertEquals(divisionDto.getName(), result.getObject().getName());
        assertEquals(department, result.getObject().getDepartment());
        verify(departmentRepository).findById(1L);
    }

    @Test
    void testMapToModelWithDepartmentNotFound() {
        // Arrange
        var divisionDto = DivisionDtoCreate.builder()
                .code("D001")
                .name("Division 1")
                .departmentId(999L)
                .build();

        when(departmentRepository.findById(999L)).thenReturn(null);

        // Act
        Result<Division> result = divisionService.mapToModel(divisionDto);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(String.format("Id: %d, Message: %s", 999L, StatusMessages.DEPARTMENT_NOT_FOUND), result.getMessages().get(0));
        verify(departmentRepository).findById(999L);
    }

    @Test
    void testMapToDtoWithSuccess() {
        // Arrange
        var division = new Division();
        division.setCode("D001");
        division.setName("Division 1");
        var department = new Department();
        department.setId(1L);
        division.setDepartment(department);

        // Act
        DivisionDtoCreate resultDto = divisionService.mapToDto(division);

        // Assert
        assertEquals(division.getCode(), resultDto.getCode());
        assertEquals(division.getName(), resultDto.getName());
        assertEquals(department.getId(), resultDto.getDepartmentId());
    }

    @Test
    void testUpdateFromDtoWithSuccess() {
        // Arrange
        var division = new Division();
        division.setCode("D001");
        division.setName("Division 1");

        var divisionDto = new DivisionDtoUpdate();
        divisionDto.setCode("D002");
        divisionDto.setName("Updated Division");
        divisionDto.setDepartmentId(1L);

        var department = new Department();
        department.setId(1L);
        when(departmentRepository.findById(1L)).thenReturn(department);

        // Act
        Result<Division> result = divisionService.updateFromDto(division, divisionDto);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("D002", division.getCode());
        assertEquals("Updated Division", division.getName());
        assertEquals(department, division.getDepartment());
        verify(departmentRepository).findById(1L);
    }

    @Test
    void testUpdateFromDtoWithDepartmentNotFound() {
        // Arrange
        var division = new Division();
        division.setCode("D001");
        division.setName("Division 1");

        var divisionDto = new DivisionDtoUpdate();
        divisionDto.setCode("D002");
        divisionDto.setName("Updated Division");
        divisionDto.setDepartmentId(999L);

        when(departmentRepository.findById(999L)).thenReturn(null);

        // Act
        Result<Division> result = divisionService.updateFromDto(division, divisionDto);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(String.format("Id: %d, Message: %s", 999L, StatusMessages.DEPARTMENT_NOT_FOUND), result.getMessages().get(0));
    }
}
