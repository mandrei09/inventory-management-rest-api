package org.example.project.dto.asset;

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
    @NotNull(message = "Code cannot be null.")
    private String code;

    @NotNull(message = "Name cannot be null.")
    private String name;

    @NotNull(message = "Value cannot be null.")
    @Min(value = 0, message = "The value should be greater than 0!")
    private Double value;

    @NotNull(message = "CostCenterId cannot be null.")
    private Integer costCenterId;

    private Date acquisitionDate;
}
