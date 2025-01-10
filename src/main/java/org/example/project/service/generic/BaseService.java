package org.example.project.service.generic;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import org.example.project.dto.generic.IBaseDtoCreate;
import org.example.project.dto.generic.IBaseDtoUpdate;
import org.example.project.model.generic.BaseEntity;
import org.example.project.repository.generic.IBaseEntityRepository;
import org.example.project.result.Result;
import org.example.project.utils.StatusMessages;
import org.example.project.utils.GenericSpecification;
import org.example.project.utils.interfaces.IAppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class BaseService
        <Model extends BaseEntity, DtoCreate extends IBaseDtoCreate, DtoUpdate extends IBaseDtoUpdate>
        implements IBaseService<Model, DtoCreate, DtoUpdate>, IAppUtils {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);

    private final IBaseEntityRepository<Model> repository;

    public BaseService(IBaseEntityRepository<Model> baseEntityRepository) {
        this.repository = baseEntityRepository;
    }

    @Override
    @Operation(
            summary = "Find entities by filters",
            description = "Retrieves a list of entities based on the specified filters.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of entities found"),
                    @ApiResponse(responseCode = "400", description = "Invalid filter parameters")
            }
    )
    public Result<List<Model>> findEntitiesByFilters(@Parameter(description = "Map of filter parameters") Map<String, String> filters) {
        Result<List<Model>> result = new Result<>();

        if(filters != null && !filters.isEmpty()) {
            try {
                return result.entityFound(repository.findAll(Specification.where(GenericSpecification.getQueryableAnd(filters))));
            }
            catch (Exception ex) {
                return result.entityNotFound(ex.getMessage());
            }
        }

        return result.entityFound(repository.findAllByIsDeletedFalse());
    }

    @Operation(
            summary = "Find an entity by ID",
            description = "Finds and returns an entity by its unique identifier.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Entity found"),
                    @ApiResponse(responseCode = "404", description = "Entity not found")
            }
    )
    public Result<Model> findById(@Parameter(description = "ID of the entity") Long id) {
        Result<Model> result = new Result<>();

        Model entityFound = repository.findById(id);
        if(entityFound == null) {
            return result.entityNotFound(id, StatusMessages.ENTITY_NOT_FOUND);
        }
        return result.entityFound(entityFound);
    }

    @Override
    @Operation(
            summary = "Create a new entity",
            description = "Creates a new entity from the provided DTO and stores it in the database.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Entity created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    public Result<Model> create(DtoCreate dto) {
        Result<Model> result = mapToModel(dto);

        if(result.isSuccess()) {
            Model databaseEntity = result.getObject();
            databaseEntity.setCreatedAt(new Date());
            databaseEntity.setModifiedAt(new Date());
            databaseEntity.setDeleted(false);
            repository.save(databaseEntity);

            result.setObject(databaseEntity);
        }
        return result;
    }

    @Override
    @Transactional
    @Operation(
            summary = "Create new entities",
            description = "Creates new entities from the provided DTO list and stores those in the database.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Entities created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    public Result<List<Model>> createMany(List<DtoCreate> dtoList) {
        Result<List<Model>> listResult = new Result<>();
        Result<Model> result = null;

        List<Model> entities = new ArrayList<>();
        Model databaseEntity = null;

        listResult.setSuccess(true);

        for (DtoCreate dto : dtoList) {
            result = mapToModel(dto);
            if(result.isSuccess()) {
                databaseEntity = result.getObject();
                databaseEntity.setCreatedAt(new Date());
                databaseEntity.setModifiedAt(new Date());
                databaseEntity.setDeleted(false);

                entities.add(databaseEntity);
            }
            else {
                listResult.entityNotFound(result.getMessages());
            }
        }
        if(listResult.isSuccess()) {
            repository.saveAll(entities);
            listResult.entityFound(entities);
        }

        return listResult;
    }

    @Override
    @Operation(
            summary = "Create an entity from a model",
            description = "Creates a new entity from a given model and saves it to the database.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Entity created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    public Model create(Model model) {
        model.setCreatedAt(new Date());
        model.setModifiedAt(new Date());
        model.setDeleted(false);

        return repository.save(model);
    }

    @Override
    @Operation(
            summary = "Update an existing entity",
            description = "Updates an existing entity based on the provided DTO and ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Entity updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Entity not found")
            }
    )
    public Result<Model> update(Long id, DtoUpdate dto) {
        Result<Model> result = new Result<>();

        Model databaseEntity = repository.findById(id);
        if (databaseEntity == null) return result.entityNotFound(StatusMessages.entityNotFound(id));

        result = updateFromDto(databaseEntity, dto);

        if(result.isSuccess()) {
            databaseEntity.setModifiedAt(new Date());
            return result.entityFound(repository.save(databaseEntity));
        }
        else
            return result;
    }

    @Override
    @Operation(
            summary = "Soft delete an entity",
            description = "Marks an entity as deleted without removing it from the database.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Entity soft-deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Entity not found")
            }
    )
    public Result<Model> softDelete(Long id) {
        Result<Model> result = new Result<>();

        Model databaseEntity = repository.findById(id);
        if (databaseEntity == null) return result.entityNotFound(StatusMessages.entityNotFound(id));

        databaseEntity.setDeleted(true);
        databaseEntity.setModifiedAt(new Date());

        return result.entityFound(repository.save(databaseEntity));
    }

    @Override
    @Operation(
            summary = "Delete an entity",
            description = "Permanently deletes an entity from the database.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Entity deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Entity not found")
            }
    )
    public Result<Model> delete(Long id) {
        Result<Model> result = new Result<>();

        Model databaseEntity = repository.findById(id);
        if (databaseEntity == null) return result.entityNotFound(StatusMessages.entityNotFound(id));

        repository.delete(databaseEntity);
        return result.entityFound(databaseEntity);
    }

    @Override
    @Transactional
    @Operation(
            summary = "Clean up soft-deleted entities",
            description = "Deletes entities that are marked as deleted and were created before the specified date.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Entities cleaned up successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid date provided")
            }
    )
    public Result<Integer> cleanUp(@Parameter(description = "Date after which to clean up entities") Date dateBefore) {
        Result<Integer> result = new Result<>();

        if(dateBefore == null) dateBefore = new Date();

        Integer entitiesDeleted = repository.deleteAllByIsDeletedTrueAndCreatedAtBefore(dateBefore);
        if(entitiesDeleted != null) {
            result.entityFound(entitiesDeleted);
        }

        return result;
    }
}