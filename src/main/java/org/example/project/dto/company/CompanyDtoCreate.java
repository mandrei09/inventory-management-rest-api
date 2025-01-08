package org.example.project.dto.company;

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
    @NotNull(message = "Code cannot be null.")
    private String code;

    @NotNull(message = "Name cannot be null.")
    private String name;

    @NotNull(message = "ManagerId cannot be null.")
    private Integer managerId;
}
