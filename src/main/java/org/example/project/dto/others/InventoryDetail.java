package org.example.project.dto.others;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.project.model.Asset;
import org.example.project.model.InventoryAsset;
import org.example.project.utils.InventoryStatus;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InventoryDetail {
    private InventoryStatus status;
    private Integer assetsScanned = null;
    private Integer finishedCostCenters = null;
    private Double valueLost = null;
    List<InventoryAsset> differences = null;
}
