package org.example.project.service.interfaces;

import org.example.project.dto.department.DepartmentDtoCreate;
import org.example.project.dto.department.DepartmentDtoUpdate;
import org.example.project.model.Department;
import org.example.project.service.generic.IBaseService;

public interface IDepartmentService extends IBaseService<Department, DepartmentDtoCreate, DepartmentDtoUpdate> {

}
