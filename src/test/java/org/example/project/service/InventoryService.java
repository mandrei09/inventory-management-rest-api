package org.example.project.service;

import org.example.project.dto.inventory.InventoryDtoCreate;
import org.example.project.dto.inventory.InventoryDtoUpdate;
import org.example.project.model.Company;
import org.example.project.model.Inventory;
import org.example.project.repository.ICompanyRepository;
import org.example.project.repository.IInventoryRepository;
import org.example.project.result.Result;
import org.example.project.utils.StatusMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    @InjectMocks
    private InventoryService inventoryService;

    @Mock
    private IInventoryRepository inventoryRepository;

    @Mock
    private ICompanyRepository companyRepository;

    private InventoryDtoCreate dtoCreate;
    private InventoryDtoUpdate dtoUpdate;
    private Company company;
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        company = new Company();
        company.setId(1L);

        inventory = new Inventory();
        inventory.setId(1L);
        inventory.setCompany(company);
        inventory.setCode("INV001");
        inventory.setName("Test Inventory");
        inventory.setInfo("Some info");

        dtoCreate = InventoryDtoCreate.builder()
                .companyId(1L)
                .code("INV001")
                .name("Test Inventory")
                .info("Some info")
                .startDate(null)
                .endDate(null)
                .build();

        dtoUpdate = InventoryDtoUpdate.builder()
                .code("INV002")
                .name("Updated Inventory")
                .info("Updated info")
                .companyId(2L)
                .build();
    }

    @Test
    void testMapToModel_Success() {
        // Arrange
        when(companyRepository.findById(1L)).thenReturn(company);

        // Act
        Result<Inventory> result = inventoryService.mapToModel(dtoCreate);

        // Assert
        assertTrue(result.isSuccess());
        assertNotNull(result.getObject());
        assertEquals("INV001", result.getObject().getCode());
        assertEquals("Test Inventory", result.getObject().getName());
        assertEquals("Some info", result.getObject().getInfo());
        assertEquals(1L, result.getObject().getCompany().getId());
    }

    @Test
    void testMapToModel_CompanyNotFound() {
        // Arrange
        when(companyRepository.findById(1L)).thenReturn(null);

        // Act
        Result<Inventory> result = inventoryService.mapToModel(dtoCreate);

        // Assert
        assertFalse(result.isSuccess());
        assertNotNull(result.getMessages());
    }

    @Test
    void testMapToDto_Success() {
        // Act
        InventoryDtoCreate resultDto = inventoryService.mapToDto(inventory);

        // Assert
        assertNotNull(resultDto);
        assertEquals(1L, resultDto.getCompanyId());
        assertEquals("INV001", resultDto.getCode());
        assertEquals("Test Inventory", resultDto.getName());
        assertEquals("Some info", resultDto.getInfo());
    }

    @Test
    void testUpdateFromDto_Success() {
        // Arrange
        Company newCompany = new Company();
        newCompany.setId(2L);
        when(companyRepository.findById(2L)).thenReturn(newCompany);

        // Act
        Result<Inventory> result = inventoryService.updateFromDto(inventory, dtoUpdate);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("INV002", inventory.getCode());
        assertEquals("Updated Inventory", inventory.getName());
        assertEquals("Updated info", inventory.getInfo());
        assertEquals(2L, inventory.getCompany().getId());
    }

    @Test
    void testUpdateFromDto_CompanyNotFound() {
        // Arrange
        when(companyRepository.findById(2L)).thenReturn(null);

        // Act
        Result<Inventory> result = inventoryService.updateFromDto(inventory, dtoUpdate);

        // Assert
        assertFalse(result.isSuccess());
        assertNotNull(result.getMessages());
    }
}
