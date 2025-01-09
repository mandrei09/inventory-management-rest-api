package org.example.project.dto.others;

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
public class ScanAssetDtoCreate implements IBaseDtoCreate {

    @NotNull(message = "AssetId cannot be null.")
    @Schema(description = "The unique identifier of the asset being scanned.", example = "101")
    private Long assetId;

    @Schema(description = "The unique identifier of the cost center associated with the scanned asset.", example = "5")
    private Long costCenterId;

    @Min(value = 0, message = "Quantity cannot be negative.")
    @Schema(description = "The quantity of the asset being scanned.", example = "50.0")
    private Double quantity;
}
