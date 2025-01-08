package org.example.project.dto.division;

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
    @NotNull(message = "Code cannot be null.")
    private String code;

    @NotNull(message = "Name cannot be null.")
    private String name;

    @NotNull(message = "DepartmentId cannot be null.")
    private Integer departmentId;
}
