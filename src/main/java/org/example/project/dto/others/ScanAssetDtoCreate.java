package org.example.project.dto.others;

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

    private Double quantity;
}
