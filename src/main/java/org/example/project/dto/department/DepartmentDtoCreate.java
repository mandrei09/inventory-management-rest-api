package org.example.project.dto.department;

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
public class DepartmentDtoCreate implements IBaseDtoCreate {

    @Schema(description = "The unique code of the department.", example = "D001", required = true)
    @NotNull(message = "Code cannot be null.")
    private String code;

    @Schema(description = "The name of the department.", example = "HR Department", required = true)
    @NotNull(message = "Name cannot be null.")
    private String name;

    @Schema(description = "The ID of the company to which the department belongs.", example = "1001", required = true)
    @NotNull(message = "CompanyId cannot be null.")
    private Long companyId;
}
