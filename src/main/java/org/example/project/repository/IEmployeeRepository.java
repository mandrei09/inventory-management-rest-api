package org.example.project.repository;

import org.example.project.model.Employee;
import org.example.project.repository.generic.BaseEntityRepository;

import java.util.List;

public interface IEmployeeRepository extends BaseEntityRepository<Employee> {
    List<Employee> findAllByCompanyIdAndIsDeletedFalse(int companyId);
}
