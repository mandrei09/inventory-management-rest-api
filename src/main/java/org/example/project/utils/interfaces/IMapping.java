package org.example.project.utils.interfaces;

import org.example.project.dto.generic.IBaseDtoCreate;
import org.example.project.dto.generic.IBaseDtoUpdate;
import org.example.project.model.generic.BaseEntity;
import org.example.project.result.Result;

public interface IMapping<Model extends BaseEntity, IDtoCreate extends IBaseDtoCreate, IDtoUpdate extends IBaseDtoUpdate> {
    Model mapToModel(IDtoCreate dtoCreate);
    IDtoCreate mapToDto(Model model);
    Result<Model> updateFromDto(Model model, IDtoUpdate dtoUpdate);
}
