package org.example.project.repository;

import org.example.project.model.CostCenter;
import org.example.project.model.Inventory;
import org.example.project.repository.generic.BaseEntityRepository;

import java.util.List;

public interface IInventoryRepository extends BaseEntityRepository<Inventory> {
    List<Inventory> findAllByCompanyIdAndIsDeletedFalse(int companyId);
}
