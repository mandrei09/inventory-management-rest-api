package org.example.project.repository;

import org.example.project.model.CostCenter;
import org.example.project.model.Inventory;
import org.example.project.repository.generic.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IInventoryRepository extends BaseEntityRepository<Inventory> {
    List<Inventory> findAllByCompanyIdAndIsDeletedFalse(int companyId);

    @Query("SELECT MAX(i.id) FROM Inventory i WHERE i.company.id = :companyId")
    Long findByCompanyId(Long companyId);
}
