package org.example.project.repository;

import org.example.project.model.Asset;
import org.example.project.model.Inventory;
import org.example.project.repository.generic.BaseEntityRepository;

import java.util.List;

public interface IAssetRepository extends BaseEntityRepository<Asset> {
    List<Asset> findAllByCostCenterIdAndIsDeletedFalse(int costCenterId);
}
