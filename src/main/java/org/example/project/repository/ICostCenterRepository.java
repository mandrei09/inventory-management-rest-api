package org.example.project.repository;

import org.example.project.model.CostCenter;
import org.example.project.repository.generic.IBaseEntityRepository;

import java.util.List;

public interface ICostCenterRepository extends IBaseEntityRepository<CostCenter> {
    List<CostCenter> findAllByDivisionIdAndIsDeletedFalse(int divisionId);
}
