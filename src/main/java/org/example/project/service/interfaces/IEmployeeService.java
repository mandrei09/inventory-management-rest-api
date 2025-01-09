package org.example.project.service.interfaces;

import org.example.project.dto.employee.EmployeeDtoCreate;
import org.example.project.dto.employee.EmployeeDtoUpdate;
import org.example.project.model.Employee;
import org.example.project.service.generic.IBaseService;

public interface IEmployeeService extends IBaseService<Employee, EmployeeDtoCreate, EmployeeDtoUpdate> {

}
