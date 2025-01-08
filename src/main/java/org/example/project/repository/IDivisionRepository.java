package org.example.project.repository;

import org.example.project.model.Department;
import org.example.project.model.Division;
import org.example.project.repository.generic.BaseEntityRepository;

import java.util.List;

public interface IDivisionRepository extends BaseEntityRepository<Division> {
    List<Division> findAllByDepartmentIdAndIsDeletedFalse(int departmentId);
}
