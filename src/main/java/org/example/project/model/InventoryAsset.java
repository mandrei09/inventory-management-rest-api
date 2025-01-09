package org.example.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.project.model.embedable.InventoryAssetId;
import org.example.project.model.generic.BaseEntity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class InventoryAsset extends BaseEntity {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "inventoryId", column = @Column(name = "inventory_id", nullable = false)),
            @AttributeOverride(name = "assetId", column = @Column(name = "asset_id", nullable = false))
    })
    private InventoryAssetId inventoryAssetId;

    @ManyToOne
    @MapsId("inventoryId")
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    @Schema(description = "The inventory this asset is associated with.", example = "Inventory for Q1 2025")
    private Inventory inventory;

    @ManyToOne
    @MapsId("assetId")
    @JoinColumn(name = "asset_id", referencedColumnName = "id")
    @Schema(description = "The asset that is part of the inventory.", example = "Laptop XYZ")
    private Asset asset;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cost_center_initial_id")
    @Schema(description = "The initial cost center associated with the asset.", example = "Engineering")
    private CostCenter costCenterInitial;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cost_center_final_id")
    @Schema(description = "The final cost center associated with the asset.", example = "Finance")
    private CostCenter costCenterFinal;

    @Schema(description = "The initial quantity of the asset in the inventory.", example = "50.0")
    private Double quantityInitial;

    @Schema(description = "The final quantity of the asset in the inventory.", example = "45.0")
    private Double quantityFinal;
}
