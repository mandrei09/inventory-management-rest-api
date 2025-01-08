package org.example.project.service.generic;

import jakarta.transaction.Transactional;
import org.example.project.dto.generic.IBaseDtoCreate;
import org.example.project.dto.generic.IBaseDtoUpdate;
import org.example.project.model.generic.BaseEntity;
import org.example.project.repository.generic.BaseEntityRepository;
import org.example.project.result.Result;
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

public abstract class BaseEntityService
        <Model extends BaseEntity, DtoCreate extends IBaseDtoCreate, DtoUpdate extends IBaseDtoUpdate>
        implements IMapping<Model, DtoCreate, DtoUpdate>, IAppUtils {
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

    public Model create(DtoCreate dto) {
        Model databaseEntity = mapToModel(dto);

        databaseEntity.setCreatedAt(new Date());
        databaseEntity.setModifiedAt(new Date());
        databaseEntity.setDeleted(false);

        return repository.save(databaseEntity);
    }

    public Result<Model> update(Long id, DtoUpdate dto) {
        Result<Model> result = new Result<>();

        Model databaseEntity = repository.findById(id);
        if (databaseEntity == null) return result.entityNotFound("Entity");

        result = updateFromDto(databaseEntity, dto);

        if(result.isSuccess()) {
            databaseEntity.setModifiedAt(new Date());
            return result.entityFound(repository.save(databaseEntity));
        }
        else
            return result;
    }

    public Result<Model>  softDelete(Long id) {
        Result<Model> result = new Result<>();

        Model databaseEntity = repository.findById(id);
        if (databaseEntity == null) return result.entityNotFound("Entity");

        databaseEntity.setDeleted(true);
        databaseEntity.setModifiedAt(new Date());

        return result.entityFound(repository.save(databaseEntity));
    }

    public Result<Model>  delete(Long id) {
        Result<Model> result = new Result<>();

        Model databaseEntity = repository.findById(id);
        if (databaseEntity == null) return result.entityNotFound("Entity");

        repository.delete(databaseEntity);
        return result.entityFound(databaseEntity);
    }
}
