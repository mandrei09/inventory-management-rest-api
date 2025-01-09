package org.example.project.dto.inventoryAsset;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.project.dto.generic.IBaseDtoCreate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InventoryAssetDtoCreate implements IBaseDtoCreate {

    @Schema(description = "The unique ID of the asset associated with the inventory.", example = "1")
    @NotNull(message = "AssetId cannot be null.")
    private Long assetId;

    @Schema(description = "The unique ID of the inventory where the asset is stored.", example = "101")
    @NotNull(message = "InventoryId cannot be null.")
    private Long inventoryId;

    @Schema(description = "The ID of the initial cost center associated with the asset.", example = "10")
    @NotNull(message = "CostCenterInitial cannot be null.")
    private Long costCenterInitialId;

    @Schema(description = "The ID of the final cost center associated with the asset, if applicable.", example = "12")
    private Long costCenterFinalId;

    @Schema(description = "The initial quantity of the asset in the inventory.", example = "50")
    @NotNull(message = "The initial quantity cannot be null.")
    @Min(value = -1, message = "The initial quantity should be at least 0!")
    private Double quantityInitial;

    @Schema(description = "The final quantity of the asset in the inventory, if applicable.", example = "45")
    @Min(value = -1, message = "The initial quantity should be at least 0!")
    private Double quantityFinal;
}
