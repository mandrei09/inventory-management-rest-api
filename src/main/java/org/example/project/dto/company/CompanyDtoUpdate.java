package org.example.project.dto.company;

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
public class CompanyDtoUpdate implements IBaseDtoUpdate {

    @Schema(description = "The unique code of the company.", example = "CMP001")
    private String code;

    @Schema(description = "The name of the company.", example = "Optima Group")
    private String name;

    @Schema(description = "The ID of the manager responsible for the company.", example = "123")
    private Long managerId;
}
