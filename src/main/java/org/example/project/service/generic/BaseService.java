package org.example.project.service.generic;

import jakarta.transaction.Transactional;
import org.example.project.dto.generic.IBaseDtoCreate;
import org.example.project.dto.generic.IBaseDtoUpdate;
import org.example.project.model.generic.BaseEntity;
import org.example.project.repository.generic.BaseEntityRepository;
import org.example.project.result.Result;
import org.example.project.utils.GenericSpecification;
import org.example.project.utils.interfaces.IAppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class BaseService
        <Model extends BaseEntity, DtoCreate extends IBaseDtoCreate, DtoUpdate extends IBaseDtoUpdate>
        implements IBaseService<Model, DtoCreate, DtoUpdate>, IAppUtils{

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);

    @Autowired
    private final BaseEntityRepository<Model> repository;

    public BaseService(BaseEntityRepository<Model> baseEntityRepository) {
        this.repository = baseEntityRepository;
    }

    @Override
    @Transactional()
    public List<Model> findEntitiesByFilters(Map<String, String> filters) {
        if(filters != null && !filters.isEmpty()) {
            return repository.findAll(
                Specification.where(GenericSpecification.getQueryableAnd(filters)));
        }
        return repository.findAllByIsDeletedFalse().toList();
    }

    public Model findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Model create(DtoCreate dto) {
        Model databaseEntity = mapToModel(dto);

        databaseEntity.setCreatedAt(new Date());
        databaseEntity.setModifiedAt(new Date());
        databaseEntity.setDeleted(false);

        return repository.save(databaseEntity);
    }

    @Override
    public Model create(Model model) {
        model.setCreatedAt(new Date());
        model.setModifiedAt(new Date());
        model.setDeleted(false);

        return repository.save(model);
    }

    @Override
    public Result<Model> update(Long id, DtoUpdate dto) {
        Result<Model> result = new Result<>();

        Model databaseEntity = repository.findById(id);
        if (databaseEntity == null) return result.entityNotFound(id + " entity not found!");

        result = updateFromDto(databaseEntity, dto);

        if(result.isSuccess()) {
            databaseEntity.setModifiedAt(new Date());
            return result.entityFound(repository.save(databaseEntity));
        }
        else
            return result;
    }

    @Override
    public Result<Model> softDelete(Long id) {
        Result<Model> result = new Result<>();

        Model databaseEntity = repository.findById(id);
        if (databaseEntity == null) return result.entityNotFound(id + " entity not found!");

        databaseEntity.setDeleted(true);
        databaseEntity.setModifiedAt(new Date());

        return result.entityFound(repository.save(databaseEntity));
    }

    @Override
    public Result<Model> delete(Long id) {
        Result<Model> result = new Result<>();

        Model databaseEntity = repository.findById(id);
        if (databaseEntity == null) return result.entityNotFound(id + " entity not found!");

        repository.delete(databaseEntity);
        return result.entityFound(databaseEntity);
    }

    @Override
    public Result<Integer> cleanUp(Date dateAfter) {
        Result<Integer> result = new Result<>();

        if(dateAfter == null) dateAfter = new Date();

        Integer entitiesDeleted = repository.deleteAllByIsDeletedTrueAndCreatedAtBefore(dateAfter);
        if(entitiesDeleted != null) {
            result.entityFound(entitiesDeleted);
        }

        return result;
    }
}
