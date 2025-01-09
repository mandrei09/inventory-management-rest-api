package org.example.project.dto.others;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "The current status of the inventory.", example = "FINISHED")
    private InventoryStatus status;

    @Schema(description = "The number of assets scanned in the inventory.", example = "200")
    private Integer assetsScanned = null;

    @Schema(description = "The number of cost centers completed in the inventory process.", example = "3")
    private Integer finishedCostCenters = null;

    @Schema(description = "The total value of the assets lost during the inventory process.", example = "500.00")
    private Double valueLost = null;

    @Schema(description = "A list of inventory asset differences, showing discrepancies between the initial and final quantities.",
            implementation = InventoryAsset.class)
    private List<InventoryAsset> differences = null;
}
