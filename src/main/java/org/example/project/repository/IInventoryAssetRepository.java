package org.example.project.repository;

import org.example.project.model.InventoryAsset;
import org.example.project.repository.generic.IBaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IInventoryAssetRepository extends IBaseEntityRepository<InventoryAsset> {

    InventoryAsset findByInventoryIdAndAssetId(Long inventoryId, Long assetId);

    @Query("""
    SELECT ia FROM InventoryAsset ia\s
    WHERE ia.asset.costCenter.division.department.company.id = :companyId
        and ia.isDeleted = false and ia.inventory.id = :inventoryId
    """)
    List<InventoryAsset> findAllByInventoryIdAndCompanyId(Long inventoryId, Long companyId);

    @Query("select count(ia) from InventoryAsset ia " +
            "where ia.inventory.id = :inventoryId and ia.costCenterFinal is not null and ia.isDeleted = false and ia.asset.isDeleted = false")
    Integer countAllAssetsScannedByInventoryId(Long inventoryId);

    @Query(value = """
        SELECT COUNT(*)
        FROM inventory_asset ia
        INNER JOIN (
            SELECT ia.cost_center_final_id, ia.inventory_id, COUNT(*) AS ScannedAssets
            FROM inventory_asset ia
            INNER JOIN asset a ON a.is_deleted = 0 AND ia.is_deleted= 0
            WHERE ia.cost_center_final_id IS NOT NULL
            GROUP BY ia.cost_center_final_id, ia.inventory_id
        ) S ON ia.cost_center_final_id = S.cost_center_final_id AND S.inventory_id = :inventoryId
        INNER JOIN (
            SELECT ia.cost_center_final_id, ia.inventory_id, COUNT(*) AS AllAssets
            FROM inventory_asset ia
            INNER JOIN asset a ON a.is_deleted = 0 AND ia.is_deleted = 0
            GROUP BY ia.cost_center_final_id, ia.inventory_id
        ) A ON ia.cost_center_final_id = A.cost_center_final_id AND A.inventory_id = :inventoryId
        WHERE A.AllAssets = S.ScannedAssets;
    """, nativeQuery = true)
    Integer countFullScannedCostCenters(@Param("inventoryId") Long inventoryId);

    @Query(value = """
        SELECT COALESCE(SUM(ABS(COALESCE(ia.quantity_initial, 0) - COALESCE(ia.quantity_final, 0)) * a.Value), 0.0)
        FROM inventory_asset ia
        INNER JOIN asset a
        ON a.id = ia.asset_id AND a.is_deleted = 0 AND ia.is_deleted = 0
        WHERE ia.inventory_id = :inventoryId
        AND ia.cost_center_final_id IS NOT NULL
        AND ia.quantity_final < ia.quantity_initial
        """, nativeQuery = true)
    Double calculateTotalDifferenceValue(@Param("inventoryId") Long inventoryId);

    @Query(value = """
        SELECT ia.* 
        FROM inventory_asset ia
        INNER JOIN asset a ON a.id = ia.asset_id AND ia.is_deleted = 0 AND a.is_deleted = 0
        WHERE ia.inventory_id = :inventoryId and ia.cost_center_final_id is not null
          AND (
              (COALESCE(ia.quantity_final, 0.0) != COALESCE(ia.quantity_initial, 0.0)) 
              OR (ia.cost_center_initial_id != COALESCE(ia.cost_center_final_id, 0))
          )
        """, nativeQuery = true)
    List<InventoryAsset> findByInventoryIdWithDifferences(@Param("inventoryId") Long inventoryId);

    @Query("SELECT MAX(ia.modifiedAt) FROM InventoryAsset ia WHERE ia.inventory.id = :inventoryId")
    Date findMaxModifiedAtByInventoryId(Long inventoryId);

}
