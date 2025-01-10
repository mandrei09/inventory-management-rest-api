package org.example.project.service;

import org.example.project.dto.inventoryAsset.InventoryAssetDtoCreate;
import org.example.project.dto.inventoryAsset.InventoryAssetDtoUpdate;
import org.example.project.model.Asset;
import org.example.project.model.CostCenter;
import org.example.project.model.Inventory;
import org.example.project.model.InventoryAsset;
import org.example.project.model.embedable.InventoryAssetId;
import org.example.project.repository.IAssetRepository;
import org.example.project.repository.ICostCenterRepository;
import org.example.project.repository.IInventoryAssetRepository;
import org.example.project.repository.IInventoryRepository;
import org.example.project.result.Result;
import org.example.project.utils.StatusMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryAssetServiceTest {

    @InjectMocks
    private InventoryAssetService inventoryAssetService;

    @Mock
    private IInventoryAssetRepository inventoryAssetRepository;

    @Mock
    private IInventoryRepository inventoryRepository;

    @Mock
    private IAssetRepository assetRepository;

    @Mock
    private ICostCenterRepository costCenterRepository;

    private InventoryAssetDtoCreate dtoCreate;
    private InventoryAssetDtoUpdate dtoUpdate;
    private Inventory inventory;
    private Asset asset;
    private CostCenter costCenterInitial;
    private CostCenter costCenterFinal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock objects for DTOs and entities
        dtoCreate = new InventoryAssetDtoCreate();
        dtoCreate.setInventoryId(1L);
        dtoCreate.setAssetId(1L);
        dtoCreate.setCostCenterInitialId(1L);
        dtoCreate.setCostCenterFinalId(2L);
        dtoCreate.setQuantityInitial(10.0);
        dtoCreate.setQuantityFinal(5.0);

        dtoUpdate = new InventoryAssetDtoUpdate();
        dtoUpdate.setInventoryId(1L);
        dtoUpdate.setAssetId(1L);
        dtoUpdate.setCostCenterInitialId(1L);
        dtoUpdate.setCostCenterFinalId(2L);
        dtoUpdate.setQuantityInitial(20.0);
        dtoUpdate.setQuantityFinal(10.0);

        inventory = new Inventory();
        inventory.setId(1L);

        asset = new Asset();
        asset.setId(1L);

        costCenterInitial = new CostCenter();
        costCenterInitial.setId(1L);

        costCenterFinal = new CostCenter();
        costCenterFinal.setId(2L);
    }

    @Test
    void testMapToModel_Success() {
        // Arrange
        when(inventoryRepository.findById(1L)).thenReturn(inventory);
        when(assetRepository.findById(1L)).thenReturn(asset);
        when(costCenterRepository.findById(1L)).thenReturn(costCenterInitial);
        when(costCenterRepository.findById(2L)).thenReturn(costCenterFinal);

        // Act
        Result<InventoryAsset> result = inventoryAssetService.mapToModel(dtoCreate);

        // Assert
        assertTrue(result.isSuccess());
        assertNotNull(result.getObject());
        assertEquals(1L, result.getObject().getInventory().getId());
        assertEquals(1L, result.getObject().getAsset().getId());
        assertEquals(1L, result.getObject().getCostCenterInitial().getId());
        assertEquals(2L, result.getObject().getCostCenterFinal().getId());
    }

    @Test
    void testMapToModel_InventoryNotFound() {
        // Arrange
        when(inventoryRepository.findById(1L)).thenReturn(null);

        // Act
        Result<InventoryAsset> result = inventoryAssetService.mapToModel(dtoCreate);

        // Assert
        assertFalse(result.isSuccess());
        assertNotNull(result.getMessages());
    }

    @Test
    void testMapToDto_Success() {
        // Arrange
        InventoryAsset inventoryAsset = new InventoryAsset();
        inventoryAsset.setInventory(inventory);
        inventoryAsset.setAsset(asset);
        inventoryAsset.setCostCenterInitial(costCenterInitial);
        inventoryAsset.setCostCenterFinal(costCenterFinal);
        inventoryAsset.setQuantityInitial(10.0);
        inventoryAsset.setQuantityFinal(5.0);

        // Act
        InventoryAssetDtoCreate resultDto = inventoryAssetService.mapToDto(inventoryAsset);

        // Assert
        assertEquals(1L, resultDto.getInventoryId());
        assertEquals(1L, resultDto.getAssetId());
        assertEquals(1L, resultDto.getCostCenterInitialId());
        assertEquals(2L, resultDto.getCostCenterFinalId());
        assertEquals(10.0, resultDto.getQuantityInitial());
        assertEquals(5.0, resultDto.getQuantityFinal());
    }

    @Test
    void testUpdateFromDto_Success() {
        // Arrange
        InventoryAsset inventoryAsset = new InventoryAsset();
        when(inventoryRepository.findById(1L)).thenReturn(inventory);
        when(assetRepository.findById(1L)).thenReturn(asset);
        when(costCenterRepository.findById(1L)).thenReturn(costCenterInitial);
        when(costCenterRepository.findById(2L)).thenReturn(costCenterFinal);

        // Act
        Result<InventoryAsset> result = inventoryAssetService.updateFromDto(inventoryAsset, dtoUpdate);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals(20.0, inventoryAsset.getQuantityInitial());
        assertEquals(10.0, inventoryAsset.getQuantityFinal());
        assertEquals(costCenterInitial, inventoryAsset.getCostCenterInitial());
        assertEquals(costCenterFinal, inventoryAsset.getCostCenterFinal());
    }

    @Test
    void testUpdateFromDto_EntityNotFound() {
        // Arrange
        InventoryAsset inventoryAsset = new InventoryAsset();
        when(inventoryRepository.findById(1L)).thenReturn(null);

        // Act
        Result<InventoryAsset> result = inventoryAssetService.updateFromDto(inventoryAsset, dtoUpdate);

        // Assert
        assertFalse(result.isSuccess());
        assertNotNull(result.getMessages());
    }

    @Test
    void testInsertAssetsIntoNewInventory_Success() {
        // Arrange
        List<InventoryAsset> inventoryAssets = new ArrayList<>();
        inventoryAssets.add(new InventoryAsset());
        when(inventoryRepository.findLastIdByCompanyId(1L)).thenReturn(1L);
        when(inventoryAssetRepository.findAllByInventoryIdAndCompanyId(1L, 1L)).thenReturn(inventoryAssets);

        // Act
        Result<Integer> result = inventoryAssetService.insertAssetsIntoNewInventory(2L, 1L);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals(inventoryAssets.size(), result.getObject());
    }
}
