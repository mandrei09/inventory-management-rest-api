package org.example.project.service.generic;

import jakarta.transaction.Transactional;
import org.example.project.dto.generic.BaseDto;
import org.example.project.model.generic.BaseEntity;
import org.example.project.repository.generic.BaseEntityRepository;
import org.example.project.utils.GenericSpecification;
import org.example.project.utils.interfaces.IAppUtils;
import org.example.project.utils.interfaces.IMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class BaseEntityService<Model extends BaseEntity, Dto extends BaseDto>
        implements IMapping<Model, Dto>, IAppUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEntityService.class);

    @Autowired
    private final BaseEntityRepository<Model> repository;

    public BaseEntityService(BaseEntityRepository<Model> baseEntityRepository) {
        this.repository = baseEntityRepository;
    }

    @Transactional()
    public List<Model> getEntitiesByFilters(Map<String, String> filters) {
        if(filters != null && !filters.isEmpty()) {
            return repository.findAll(
                Specification.where(GenericSpecification.getQueryableAnd(filters)));
        }
        return repository.findAllByIsDeletedFalse().toList();
    }

    public Model getById(Long id) {
        return repository.findById(id);
    }

    public Model create(Dto dto) {
        Model databaseEntity = mapToModel(dto);
        return repository.save(databaseEntity);
    }

    public Model update(Long id, Dto dto) {
        Model databaseEntity = repository.findById(id);
        if (databaseEntity == null) return null;

        updateFromDto(databaseEntity, dto);

        databaseEntity.setModifiedAt(new Date());

        return repository.save(databaseEntity);
    }

    public Model softDelete(Long id) {
        Model databaseEntity = repository.findById(id);
        if (databaseEntity == null) return null;

        databaseEntity.setDeleted(true);
        databaseEntity.setModifiedAt(new Date());

        return repository.save(databaseEntity);
    }

    public Model delete(Long id) {
        Model databaseEntity = repository.findById(id);
        if (databaseEntity == null) return null;

        repository.delete(databaseEntity);
        return databaseEntity;
    }
}
