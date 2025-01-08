package org.example.project.dto.costCenter;

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
public class CostCenterDtoUpdate implements IBaseDtoUpdate {
    private String code;

    private String name;

    private Integer divisionId;
}
