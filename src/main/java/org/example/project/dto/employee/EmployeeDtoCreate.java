package org.example.project.dto.employee;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.project.dto.generic.IBaseDtoCreate;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeDtoCreate implements IBaseDtoCreate {
    @NotNull(message = "Internal code cannot be null.")
    private String internalCode;

    @NotNull(message = "Name cannot be null.")
    private String name;

    @NotNull(message = "Lastname type cannot be null.")
    private String lastName;

    @NotNull(message = "Email type cannot be null.")
    private String email;

    private Integer managerId;

    @NotNull(message = "CompanyId cannot be null.")
    private Integer companyId;

    private Date birthDate;
}
