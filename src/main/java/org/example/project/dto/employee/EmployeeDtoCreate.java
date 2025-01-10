package org.example.project.dto.employee;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "The internal code of the employee.", example = "EMP123")
    @NotNull(message = "Internal code cannot be null.")
    private String internalCode;

    @Schema(description = "The name of the employee.", example = "John")
    @NotNull(message = "Name cannot be null.")
    private String name;

    @Schema(description = "The last name of the employee.", example = "Doe")
    @NotNull(message = "Lastname type cannot be null.")
    private String lastName;

    @Schema(description = "The email address of the employee.", example = "john.doe@example.com")
    @Pattern(regexp = "^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,4}$", message = "Invalid email format.")
    @NotNull(message = "Email type cannot be null.")
    private String email;

    @Schema(description = "The ID of the manager who supervises the employee.", example = "101")
    private Long managerId;

    @Schema(description = "The ID of the company to which the employee belongs.", example = "1")
    private Long companyId;

    @Schema(description = "The birth date of the employee.", example = "1980-12-15")
    private Date birthDate;
}
