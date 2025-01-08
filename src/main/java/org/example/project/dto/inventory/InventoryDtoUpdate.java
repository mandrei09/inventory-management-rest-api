package org.example.project.dto.inventory;

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
public class InventoryDtoUpdate implements IBaseDtoUpdate {
    private String code;

    private String name;

    private String info;

    private Integer companyId;

    private Date startDate;

    private Date endDate;
}
