package org.example.project.dto.costCenter;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class CostCenterDtoCreate implements IBaseDtoCreate {

    @Schema(description = "The unique code of the cost center.", example = "CC001", required = true)
    @NotNull(message = "Code cannot be null.")
    private String code;

    @Schema(description = "The name of the cost center.", example = "IT Department", required = true)
    @NotNull(message = "Name cannot be null.")
    private String name;

    @Schema(description = "The ID of the division to which the cost center belongs.", example = "10", required = true)
    @NotNull(message = "DivisionId cannot be null.")
    private Long divisionId;
}
