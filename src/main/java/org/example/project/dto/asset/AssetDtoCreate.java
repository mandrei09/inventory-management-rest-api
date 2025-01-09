package org.example.project.dto.asset;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.project.dto.generic.IBaseDtoCreate;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AssetDtoCreate implements IBaseDtoCreate {

    @Schema(description = "The unique code of the asset.", example = "ASSET-001", required = true)
    @NotNull(message = "Code cannot be null.")
    private String code;

    @Schema(description = "The name of the asset.", example = "Laptop", required = true)
    @NotNull(message = "Name cannot be null.")
    private String name;

    @Schema(description = "The value of the asset.", example = "1000.0", required = true)
    @NotNull(message = "Value cannot be null.")
    @Min(value = 0, message = "The value should be greater than 0!")
    private Double value;

    @Schema(description = "The ID of the cost center associated with the asset.", example = "10", required = true)
    @NotNull(message = "CostCenterId cannot be null.")
    private Long costCenterId;

    @Schema(description = "The date when the asset was acquired.", example = "2022-05-10")
    private Date acquisitionDate;
}
