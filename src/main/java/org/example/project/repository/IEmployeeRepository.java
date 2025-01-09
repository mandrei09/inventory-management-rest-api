package org.example.project.repository;

import org.example.project.model.Employee;
import org.example.project.repository.generic.IBaseEntityRepository;

import java.util.List;

public interface IEmployeeRepository extends IBaseEntityRepository<Employee> {
    List<Employee> findAllByCompanyIdAndIsDeletedFalse(int companyId);
}
