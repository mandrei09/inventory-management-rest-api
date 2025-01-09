package org.example.project.dto.employee;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,4}$", message = "Invalid email format.")
    @NotNull(message = "Email type cannot be null.")
    private String email;

    private Long managerId;

    @NotNull(message = "CompanyId cannot be null.")
    private Long companyId;

    private Date birthDate;
}
