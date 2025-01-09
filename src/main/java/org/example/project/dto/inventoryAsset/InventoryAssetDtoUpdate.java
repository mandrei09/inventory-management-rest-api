package org.example.project.dto.inventoryAsset;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.project.dto.generic.IBaseDtoUpdate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InventoryAssetDtoUpdate implements IBaseDtoUpdate {

    @Schema(description = "The unique ID of the asset associated with the inventory.", example = "1")
    private Long assetId;

    @Schema(description = "The unique ID of the inventory where the asset is stored.", example = "101")
    private Long inventoryId;

    @Schema(description = "The ID of the initial cost center associated with the asset.", example = "10")
    private Long costCenterInitialId;

    @Schema(description = "The ID of the final cost center associated with the asset, if applicable.", example = "12")
    private Long costCenterFinalId;

    @Schema(description = "The initial quantity of the asset in the inventory.", example = "50")
    @Min(value = -1, message = "The initial quantity should be at least 0!")
    private Double quantityInitial;

    @Schema(description = "The final quantity of the asset in the inventory, if applicable.", example = "45")
    @Min(value = -1, message = "The initial quantity should be at least 0!")
    private Double quantityFinal;
}
