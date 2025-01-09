package org.example.project.dto.division;

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
public class DivisionDtoUpdate implements IBaseDtoUpdate {

    @Schema(description = "The unique code of the division.", example = "D001")
    private String code;

    @Schema(description = "The name of the division.", example = "Human Resources")
    private String name;

    @Schema(description = "The ID of the department to which the division belongs.", example = "1001")
    private Long departmentId;
}
