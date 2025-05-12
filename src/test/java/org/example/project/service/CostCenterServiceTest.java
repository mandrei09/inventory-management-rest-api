package org.example.project.service;

import org.example.project.dto.costCenter.CostCenterDtoCreate;
import org.example.project.dto.costCenter.CostCenterDtoUpdate;
import org.example.project.model.CostCenter;
import org.example.project.model.Division;
import org.example.project.repository.IDivisionRepository;
import org.example.project.result.Result;
import org.example.project.utils.StatusMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("h2")
public class CostCenterServiceTest {

    @Mock
    private org.example.project.repository.ICostCenterRepository costCenterRepository;

    @Mock
    private IDivisionRepository divisionRepository;

    @InjectMocks
    private CostCenterService costCenterService;

    @Test
    void testMapToModelWithSuccess() {
        // Arrange
        var costCenterDto = CostCenterDtoCreate.builder()
                .code("CC001")
                .name("Cost Center 1")
                .divisionId(1L)
                .build();

        var division = new Division();
        division.setId(1L);
        when(divisionRepository.findById(1L)).thenReturn(division);

        // Act
        Result<CostCenter> result = costCenterService.mapToModel(costCenterDto);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals(costCenterDto.getCode(), result.getObject().getCode());
        assertEquals(costCenterDto.getName(), result.getObject().getName());
        assertEquals(division, result.getObject().getDivision());
        verify(divisionRepository).findById(1L);
    }

    @Test
    void testMapToModelWithDivisionNotFound() {
        // Arrange
        var costCenterDto = CostCenterDtoCreate.builder()
                .code("CC001")
                .name("Cost Center 1")
                .divisionId(999L)
                .build();

        when(divisionRepository.findById(999L)).thenReturn(null);

        // Act
        Result<CostCenter> result = costCenterService.mapToModel(costCenterDto);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(String.format("Id: %d, Message: %s", 999L, StatusMessages.DIVISION_NOT_FOUND), result.getMessages().get(0));
        verify(divisionRepository).findById(999L);
    }

    @Test
    void testMapToDtoWithSuccess() {
        // Arrange
        var costCenter = new CostCenter();
        costCenter.setCode("CC001");
        costCenter.setName("Cost Center 1");
        var division = new Division();
        division.setId(1L);
        costCenter.setDivision(division);

        // Act
        CostCenterDtoCreate resultDto = costCenterService.mapToDto(costCenter);

        // Assert
        assertEquals(costCenter.getCode(), resultDto.getCode());
        assertEquals(costCenter.getName(), resultDto.getName());
        assertEquals(division.getId(), resultDto.getDivisionId());
    }

    @Test
    void testUpdateFromDtoWithSuccess() {
        // Arrange
        var costCenter = new CostCenter();
        costCenter.setCode("CC001");
        costCenter.setName("Cost Center 1");

        var costCenterDto = new CostCenterDtoUpdate();
        costCenterDto.setCode("CC002");
        costCenterDto.setName("Updated Cost Center");
        costCenterDto.setDivisionId(1L);

        var division = new Division();
        division.setId(1L);
        when(divisionRepository.findById(1L)).thenReturn(division);

        // Act
        Result<CostCenter> result = costCenterService.updateFromDto(costCenter, costCenterDto);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("CC002", costCenter.getCode());
        assertEquals("Updated Cost Center", costCenter.getName());
        assertEquals(division, costCenter.getDivision());
        verify(divisionRepository).findById(1L);
    }

    @Test
    void testUpdateFromDtoWithDivisionNotFound() {
        // Arrange
        var costCenter = new CostCenter();
        costCenter.setCode("CC001");
        costCenter.setName("Cost Center 1");

        var costCenterDto = new CostCenterDtoUpdate();
        costCenterDto.setCode("CC002");
        costCenterDto.setName("Updated Cost Center");
        costCenterDto.setDivisionId(999L);

        when(divisionRepository.findById(999L)).thenReturn(null);

        // Act
        Result<CostCenter> result = costCenterService.updateFromDto(costCenter, costCenterDto);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(String.format("Id: %d, Message: %s", 999L, StatusMessages.DIVISION_NOT_FOUND), result.getMessages().get(0));
    }
}
