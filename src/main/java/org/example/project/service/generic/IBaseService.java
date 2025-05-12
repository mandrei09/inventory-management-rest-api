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
    Result<?> findEntitiesByFilters(Map<String, String> filters, Integer page, Integer perPage);
    Result<Model> create(IDtoCreate dto);
    Result<List<Model>> createMany(List<IDtoCreate> dtoList);
    Model create(Model model);
    Result<Model> update(Long id, IDtoUpdate dto);
    Result<Model> softDelete(Long id);
    Result<Model> delete(Long id);
    Result<Integer> cleanUp(Date dateAfter);
}
