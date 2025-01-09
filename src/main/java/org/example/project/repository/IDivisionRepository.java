package org.example.project.repository;

import org.example.project.model.Division;
import org.example.project.repository.generic.IBaseEntityRepository;

import java.util.List;

public interface IDivisionRepository extends IBaseEntityRepository<Division> {
    List<Division> findAllByDepartmentIdAndIsDeletedFalse(int departmentId);
}
