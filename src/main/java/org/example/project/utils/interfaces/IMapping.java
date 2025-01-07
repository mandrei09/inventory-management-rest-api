package org.example.project.utils.interfaces;

import org.example.project.dto.generic.BaseDto;
import org.example.project.model.generic.BaseEntity;

public interface IMapping<Model extends BaseEntity, Dto extends BaseDto> {
    Model mapToModel(Dto dto);
    Dto mapToDto(Model model);
    void updateFromDto(Model model, Dto dto);
}
