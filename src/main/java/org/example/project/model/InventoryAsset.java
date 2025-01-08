package org.example.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.project.model.embedable.InventoryAssetId;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class InventoryAsset {
    @EmbeddedId
    private InventoryAssetId id = new InventoryAssetId();

    private Double value;

    @ManyToOne
    @MapsId("inventoryId")
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @ManyToOne
    @MapsId("assetId")
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cost_center_initial_id")
    private CostCenter costCenterInitial;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cost_center_final_id")
    private CostCenter costCenterFinal;

    private Double quantityInitial;
    private Double quantityFinal;
}
