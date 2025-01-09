package org.example.project.service.interfaces;

import org.example.project.dto.division.DivisionDtoCreate;
import org.example.project.dto.division.DivisionDtoUpdate;
import org.example.project.model.Division;
import org.example.project.service.generic.IBaseService;

public interface IDivisionService extends IBaseService<Division, DivisionDtoCreate, DivisionDtoUpdate> {

}
