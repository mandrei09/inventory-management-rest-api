package org.example.project.dto.company;

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
public class CompanyDtoCreate implements IBaseDtoCreate {

    @Schema(description = "The unique code of the company.", example = "CMP001", required = true)
    @NotNull(message = "Code cannot be null.")
    private String code;

    @Schema(description = "The name of the company.", example = "Optima Group", required = true)
    @NotNull(message = "Name cannot be null.")
    private String name;

    @Schema(description = "The ID of the manager responsible for the company.", example = "123", required = true)
    @NotNull(message = "ManagerId cannot be null.")
    private Long managerId;
}
