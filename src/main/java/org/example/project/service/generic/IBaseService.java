package org.example.project.service.generic;

import org.example.project.dto.generic.IBaseDtoCreate;
import org.example.project.dto.generic.IBaseDtoUpdate;
import org.example.project.model.generic.BaseEntity;
import org.example.project.result.Result;
import org.example.project.utils.interfaces.IMapping;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IBaseService<Model extends BaseEntity, IDtoCreate extends IBaseDtoCreate, IDtoUpdate extends IBaseDtoUpdate>
    extends IMapping<Model, IDtoCreate, IDtoUpdate> {
    List<Model> findEntitiesByFilters(Map<String, String> filters);
    Model findById(Long id);
    Model create(IDtoCreate dto);
    Model create(Model model);
    Result<Model> update(Long id, IDtoUpdate dto);
    Result<Model> softDelete(Long id);
    Result<Model> delete(Long id);
    Result<Integer> cleanUp(Date dateAfter);
}
