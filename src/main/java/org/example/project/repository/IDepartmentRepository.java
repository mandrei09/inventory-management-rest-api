package org.example.project.repository;

import org.example.project.model.Department;
import org.example.project.repository.generic.IBaseEntityRepository;

import java.util.List;

public interface IDepartmentRepository extends IBaseEntityRepository<Department> {
    List<Department> findAllByCompanyIdAndIsDeletedFalse(int companyId);
}
