package org.example.project.dto.inventory;

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
public class InventoryDtoCreate implements IBaseDtoCreate {
    @NotNull(message = "Code cannot be null.")
    private String code;

    @NotNull(message = "Name cannot be null.")
    private String name;

    private String info;

    @NotNull(message = "CompanyId cannot be null.")
    private Long companyId;

    @NotNull(message = "StartDate cannot be null.")
    private Date startDate;

    @NotNull(message = "EndDate cannot be null.")
    private Date endDate;
}
