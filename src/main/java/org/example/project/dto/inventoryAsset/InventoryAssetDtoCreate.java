package org.example.project.dto.inventoryAsset;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.project.dto.asset.AssetDtoCreate;
import org.example.project.dto.costCenter.CostCenterDtoCreate;
import org.example.project.dto.generic.IBaseDtoCreate;
import org.example.project.dto.inventory.InventoryDtoCreate;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InventoryAssetDtoCreate implements IBaseDtoCreate {
    @NotNull(message = "AssetId cannot be null.")
    private Long assetId;

    @NotNull(message = "InventoryId cannot be null.")
    private Long inventoryId;

    @NotNull(message = "CostCenterInitial cannot be null.")
    private Long costCenterInitialId;

    private Long costCenterFinalId;

    @NotNull(message = "The initial quantity cannot be null.")
    @Min(value = -1, message = "The initial quantity should be al least 0!")
    private Double quantityInitial;

    @Min(value = -1, message = "The initial quantity should be al least 0!")
    private Double quantityFinal;
}
