package org.example.project.dto.asset;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.project.dto.generic.IBaseDtoUpdate;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AssetDtoUpdate implements IBaseDtoUpdate {
    private String code;

    private String name;

    @Min(value = 0, message = "The value should be greater than 0!")
    private Double value;

    private Integer costCenterId;

    private Date acquisitionDate;
}
