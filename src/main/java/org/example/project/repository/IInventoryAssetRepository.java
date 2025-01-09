package org.example.project.repository;

import org.example.project.model.InventoryAsset;
import org.example.project.repository.generic.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IInventoryAssetRepository extends BaseEntityRepository<InventoryAsset> {

    InventoryAsset findByInventoryIdAnAssetId(Long inventoryId, Long assetId);

    @Query("""
    SELECT ia FROM InventoryAsset ia\s
    WHERE ia.asset.costCenter.division.department.company.id = :companyId
        and ia.isDeleted = false and ia.inventory.id = :inventoryId
    """)
    List<InventoryAsset> findAllByInventoryIdAndCompanyId(Long inventoryId, Long companyId);

    @Query("select count(ia) from InventoryAsset ia " +
            "where ia.inventory.id = :inventory and ia.costCenterFinal is not null and ia.isDeleted = false and ia.asset.isDeleted = false")
    Integer countAllAssetsScannedByInventoryId(Long inventoryId);

    @Query(value = """
    SELECT COUNT(*)
    FROM inventoryAsset ia
    FULL JOIN (
        SELECT ia.CostCenterIdFinal, ia.InventoryId, COUNT(*) AS ScannedAssets
        FROM inventoryAsset ia
        INNER JOIN Asset a ON a.IsDeleted = 0 AND ia.IsDeleted = 0
        WHERE ia.CostCenterIdFinal IS NOT NULL
        GROUP BY ia.CostCenterIdFinal, ia.InventoryId
    ) S ON ia.CostCenterIdFinal = S.CostCenterIdFinal and S.InventoryId = :inventoryId
    FULL JOIN (
        SELECT ia.CostCenterIdFinal, ia.InventoryId, COUNT(*) AS AllAssets
        FROM inventoryAsset ia
        INNER JOIN Asset a ON a.IsDeleted = 0 AND ia.IsDeleted = 0
        GROUP BY ia.CostCenterIdFinal
    ) A ON ia.CostCenterIdFinal = A.CostCenterIdFinal and A.InventoryId = :inventoryId
    WHERE A.AllAssets = S.ScannedAssets
    """, nativeQuery = true)
    Integer countFullScannedCostCenters(@Param("inventoryId") Long inventoryId);

    @Query(value = """
        SELECT SUM(ABS(ISNULL(ia.QuantityInitial, 0) - ISNULL(ia.QuantityFinal, 0)) * a.Value)
        FROM inventoryAsset ia
        INNER JOIN asset a 
        ON a.assetid = ia.assetid AND a.isDeleted = 0 AND ia.isDeleted = 0
        WHERE ia.inventoryId = :inventoryId 
          AND ia.CostCenterIdFinal IS NOT NULL 
          AND ia.QuantityFinal < ia.QuantityInitial
        """, nativeQuery = true)
    Double calculateTotalDifferenceValue(@Param("inventoryId") Long inventoryId);

    @Query(value = """
        SELECT * 
        FROM inventoryAsset ia
        INNER JOIN asset a 
        ON a.assetid = ia.assetid AND ia.isDeleted = 0 AND a.isDeleted = 0
        WHERE ia.InventoryId = :inventoryId 
          AND (
              (ISNULL(ia.QuantityFinal, 0.0) != ISNULL(ia.QuantityInitial, 0.0)) 
              OR (ia.CostCenterIdInitial != ISNULL(ia.CostCenterIdFinal, 0))
          )
        """, nativeQuery = true)
    List<InventoryAsset> findByInventoryIdWithDifferences(@Param("inventoryId") Long inventoryId);

    @Query("SELECT MAX(ia.modifiedAt) FROM InventoryAsset ia WHERE ia.inventory.id = :inventory")
    Date findMaxModifiedAtByInventoryId(Long inventoryId);

}
