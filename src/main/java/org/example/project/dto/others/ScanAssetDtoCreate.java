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
    private Long assetId;

    private Long costCenterId;

    @Min(value = 0, message = "Quantity cannot be negative.")
    private Double quantity;
}
