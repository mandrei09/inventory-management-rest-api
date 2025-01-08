package org.example.project.repository;

import org.example.project.model.CostCenter;
import org.example.project.model.Department;
import org.example.project.repository.generic.BaseEntityRepository;

import java.util.List;

public interface ICostCenterRepository extends BaseEntityRepository<CostCenter> {
    List<CostCenter> findAllByDivisionIdAndIsDeletedFalse(int divisionId);
}
