package org.example.project.dto.department;

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
public class DepartmentDtoUpdate implements IBaseDtoUpdate {

    @Schema(description = "The unique code of the department.", example = "D001")
    private String code;

    @Schema(description = "The name of the department.", example = "HR Department")
    private String name;

    @Schema(description = "The ID of the company to which the department belongs.", example = "1001")
    private Long companyId;
}
