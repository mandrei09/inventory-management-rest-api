package org.example.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.project.dto.generic.BaseDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompanyDto extends BaseDto {
    @NotNull(message = "Code cannot be null.")
    private String code;

    @NotNull(message = "Name cannot be null.")
    private String name;

    @NotNull(message = "ManagerId cannot be null.")
    private int managerId;
}
