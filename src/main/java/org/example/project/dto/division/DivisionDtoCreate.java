package org.example.project.dto.division;

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
public class DivisionDtoCreate implements IBaseDtoCreate {

    @Schema(description = "The unique code of the division.", example = "D001", required = true)
    @NotNull(message = "Code cannot be null.")
    private String code;

    @Schema(description = "The name of the division.", example = "Human Resources", required = true)
    @NotNull(message = "Name cannot be null.")
    private String name;

    @Schema(description = "The ID of the department to which the division belongs.", example = "1001", required = true)
    @NotNull(message = "DepartmentId cannot be null.")
    private Long departmentId;
}
