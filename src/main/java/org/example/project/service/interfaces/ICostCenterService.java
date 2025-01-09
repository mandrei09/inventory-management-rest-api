package org.example.project.service.interfaces;

import org.example.project.dto.costCenter.CostCenterDtoCreate;
import org.example.project.dto.costCenter.CostCenterDtoUpdate;
import org.example.project.model.CostCenter;
import org.example.project.service.generic.IBaseService;

public interface ICostCenterService extends IBaseService<CostCenter, CostCenterDtoCreate, CostCenterDtoUpdate> {

}
