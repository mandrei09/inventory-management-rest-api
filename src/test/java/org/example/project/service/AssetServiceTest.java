package org.example.project.service;

import org.example.project.dto.asset.AssetDtoCreate;
import org.example.project.dto.asset.AssetDtoUpdate;
import org.example.project.model.Asset;
import org.example.project.model.CostCenter;
import org.example.project.repository.IAssetRepository;
import org.example.project.repository.ICostCenterRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("h2")
public class AssetServiceTest {

    @Mock
    private IAssetRepository assetRepository;

    @Mock
    private ICostCenterRepository costCenterRepository;

    @InjectMocks
    private AssetService assetService;

    @Test
    void testMapToModelWithSuccess() {
        var assetDto = AssetDtoCreate.builder()
                .code("A001")
                .name("Asset 1")
                .value(1000.0)
                .acquisitionDate(new Date())  // Corrected to Date
                .costCenterId(1L)
                .build();

        var costCenter = new CostCenter();
        costCenter.setId(1L);
        when(costCenterRepository.findById(1L)).thenReturn(costCenter);

        Result<Asset> result = assetService.mapToModel(assetDto);

        assertEquals(true, result.isSuccess());
        assertEquals(assetDto.getCode(), result.getObject().getCode());
        assertEquals(assetDto.getName(), result.getObject().getName());
        assertEquals(assetDto.getValue(), result.getObject().getValue());
        assertEquals(assetDto.getAcquisitionDate(), result.getObject().getAcquisitionDate());
        assertEquals(costCenter, result.getObject().getCostCenter());
        verify(costCenterRepository).findById(1L);
    }

    @Test
    void testMapToModelWithCostCenterNotFound() {
        var assetDto = AssetDtoCreate.builder()
                .costCenterId(999L)
                .acquisitionDate(new Date())  // Corrected to Date
                .build();

        when(costCenterRepository.findById(999L)).thenReturn(null);

        Result<Asset> result = assetService.mapToModel(assetDto);

        assertEquals(false, result.isSuccess());
        assertEquals(String.format("Id: %d, Message: %s", 999L, StatusMessages.COST_CENTER_NOT_FOUND), result.getMessages().get(0));
        verify(costCenterRepository).findById(999L);
    }

    @Test
    void testMapToDtoWithSuccess() {
        var asset = new Asset();
        asset.setCode("A001");
        asset.setName("Asset 1");
        asset.setValue(1000.0);
        asset.setAcquisitionDate(new Date());  // Corrected to Date
        var costCenter = new CostCenter();
        costCenter.setId(1L);
        asset.setCostCenter(costCenter);

        AssetDtoCreate resultDto = assetService.mapToDto(asset);

        assertEquals(asset.getCode(), resultDto.getCode());
        assertEquals(asset.getName(), resultDto.getName());
        assertEquals(asset.getValue(), resultDto.getValue());
        assertEquals(asset.getAcquisitionDate(), resultDto.getAcquisitionDate());
        assertEquals(costCenter.getId(), resultDto.getCostCenterId());
    }

    @Test
    void testUpdateFromDtoWithSuccess() {
        var asset = new Asset();
        asset.setCode("A001");
        asset.setName("Asset 1");

        var assetDto = new AssetDtoUpdate();
        assetDto.setCode("A002");
        assetDto.setName("Updated Asset");
        assetDto.setCostCenterId(1L);
        assetDto.setAcquisitionDate(new Date());  // Corrected to Date

        var costCenter = new CostCenter();
        costCenter.setId(1L);
        when(costCenterRepository.findById(1L)).thenReturn(costCenter);

        Result<Asset> result = assetService.updateFromDto(asset, assetDto);

        assertEquals(true, result.isSuccess());
        assertEquals("A002", asset.getCode());
        assertEquals("Updated Asset", asset.getName());
        assertEquals(costCenter, asset.getCostCenter());
        assertEquals(assetDto.getAcquisitionDate(), asset.getAcquisitionDate());  // Corrected to Date
        verify(costCenterRepository).findById(1L);
    }

    @Test
    void testUpdateFromDtoWithCostCenterNotFound() {
        var asset = new Asset();
        asset.setCode("A001");
        asset.setName("Asset 1");

        var assetDto = new AssetDtoUpdate();
        assetDto.setCode("A002");
        assetDto.setName("Updated Asset");
        assetDto.setCostCenterId(999L);
        assetDto.setAcquisitionDate(new Date());  // Corrected to Date

        when(costCenterRepository.findById(999L)).thenReturn(null);

        Result<Asset> result = assetService.updateFromDto(asset, assetDto);

        assertEquals(false, result.isSuccess());
        assertEquals(String.format("Id: %d, Message: %s", 999L, StatusMessages.COST_CENTER_NOT_FOUND), result.getMessages().get(0));
    }

    @Test
    void testFindAllByCompanyId() {
        Long companyId = 1L;
        var assets = List.of(new Asset(), new Asset());
        when(assetRepository.findAllByCompanyIdAndIsDeletedFalse(companyId)).thenReturn(assets);

        List<Asset> result = assetService.findAllByCompanyId(companyId);

        assertEquals(assets.size(), result.size());
        verify(assetRepository).findAllByCompanyIdAndIsDeletedFalse(companyId);
    }

    @Test
    void testFindAllByCompanyIdWithNoAssets() {
        Long companyId = 1L;
        when(assetRepository.findAllByCompanyIdAndIsDeletedFalse(companyId)).thenReturn(List.of());

        List<Asset> result = assetService.findAllByCompanyId(companyId);

        assertEquals(0, result.size());
        verify(assetRepository).findAllByCompanyIdAndIsDeletedFalse(companyId);
    }
}
