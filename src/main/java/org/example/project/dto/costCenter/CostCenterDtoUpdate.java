package org.example.project.dto.costCenter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.project.dto.generic.IBaseDtoUpdate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CostCenterDtoUpdate implements IBaseDtoUpdate {

    @Schema(description = "The unique code of the cost center.", example = "CC001")
    private String code;

    @Schema(description = "The name of the cost center.", example = "IT Department")
    private String name;

    @Schema(description = "The ID of the division to which the cost center belongs.", example = "10")
    private Long divisionId;
}
