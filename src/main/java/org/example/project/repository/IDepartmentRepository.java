package org.example.project.repository;

import org.example.project.model.Department;
import org.example.project.repository.generic.BaseEntityRepository;

import java.util.List;

public interface IDepartmentRepository extends BaseEntityRepository<Department> {
    List<Department> findAllByCompanyIdAndIsDeletedFalse(int companyId);
}
