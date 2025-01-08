package org.example.project.dto.company;

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
    private String code;

    private String name;

    private Integer managerId;
}
