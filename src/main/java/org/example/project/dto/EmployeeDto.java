package org.example.project.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.project.dto.generic.BaseDto;
import org.example.project.model.Employee;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeDto extends BaseDto {
    @NotNull(message = "Internal code cannot be null.")
    private String internalCode;

    @NotNull(message = "Name cannot be null.")
    private String name;

    @NotNull(message = "Lastname type cannot be null.")
    private String lastName;

    @NotNull(message = "Email type cannot be null.")
    private String email;

    private int managerId;

    private Date birthDate;
}
