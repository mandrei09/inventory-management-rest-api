package org.example.project.dto.department;

import jakarta.validation.constraints.NotNull;
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
    private String code;

    private String name;

    private Long companyId;
}
