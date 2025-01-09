package org.example.project.dto.inventory;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "The unique code of the inventory.", example = "INV123")
    @NotNull(message = "Code cannot be null.")
    private String code;

    @Schema(description = "The name of the inventory.", example = "Main Warehouse Inventory")
    @NotNull(message = "Name cannot be null.")
    private String name;

    @Schema(description = "Additional information about the inventory.", example = "Inventory for storing office equipment.")
    private String info;

    @Schema(description = "The ID of the company that owns the inventory.", example = "1")
    @NotNull(message = "CompanyId cannot be null.")
    private Long companyId;

    @Schema(description = "The start date of the inventory period.", example = "2025-01-01")
    @NotNull(message = "StartDate cannot be null.")
    private Date startDate;

    @Schema(description = "The end date of the inventory period.", example = "2025-12-31")
    @NotNull(message = "EndDate cannot be null.")
    private Date endDate;
}
