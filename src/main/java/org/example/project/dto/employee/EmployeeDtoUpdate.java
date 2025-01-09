package org.example.project.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.project.dto.generic.IBaseDtoUpdate;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeDtoUpdate implements IBaseDtoUpdate {
    private String internalCode;

    private String name;

    private String lastName;

    private String email;

    private Long managerId;

    private Long companyId;

    private Date birthDate;
}
