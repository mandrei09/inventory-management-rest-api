package org.example.project.repository;

import org.example.project.model.Asset;
import org.example.project.repository.generic.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAssetRepository extends BaseEntityRepository<Asset> {
    List<Asset> findAllByCostCenterIdAndIsDeletedFalse(int costCenterId);

    @Query("SELECT a FROM Asset a WHERE a.costCenter.division.department.company.id = :companyId and a.isDeleted = false")
    List<Asset> findAllByCompanyIdAndIsDeletedFalse(Long companyId);
}
