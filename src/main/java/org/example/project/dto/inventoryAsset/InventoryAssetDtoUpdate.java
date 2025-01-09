package org.example.project.dto.inventoryAsset;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.project.dto.asset.AssetDtoCreate;
import org.example.project.dto.asset.AssetDtoUpdate;
import org.example.project.dto.costCenter.CostCenterDtoCreate;
import org.example.project.dto.costCenter.CostCenterDtoUpdate;
import org.example.project.dto.generic.IBaseDtoUpdate;
import org.example.project.dto.inventory.InventoryDtoCreate;
import org.example.project.dto.inventory.InventoryDtoUpdate;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InventoryAssetDtoUpdate implements IBaseDtoUpdate {
    private Long assetId;

    private Long inventoryId;

    private Long costCenterInitialId;

    private Long costCenterFinalId;

    @Min(value = -1, message = "The initial quantity should be al least 0!")
    private Double quantityInitial;

    @Min(value = -1, message = "The initial quantity should be al least 0!")
    private Double quantityFinal;
}
