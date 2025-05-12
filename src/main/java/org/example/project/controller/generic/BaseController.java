package org.example.project.controller.generic;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.project.dto.generic.IBaseDtoCreate;
import org.example.project.dto.generic.IBaseDtoUpdate;
import org.example.project.model.generic.BaseEntity;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseService;
import org.example.project.utils.StatusMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@Tag(name = "Base Management", description = "API for managing generic entities")
@RequestMapping("/api")
public abstract class BaseController
        <Model extends BaseEntity, DtoCreate extends IBaseDtoCreate, DtoUpdate extends IBaseDtoUpdate> {

    private final BaseService<Model, DtoCreate, DtoUpdate> service;

    public BaseController(BaseService<Model, DtoCreate, DtoUpdate> service) {
        this.service = service;
    }

    @Operation(summary = "Get all entities by filters", description = "Retrieve a list of entities filtered by the specified parameters.")
    @GetMapping("/all")
    public ResponseEntity<?> findByFiltersAndPagination(@RequestParam Map<String, String> allParams) {
        Integer page = allParams.containsKey("page") ? Integer.parseInt(allParams.get("page")) : null;
        Integer perPage = allParams.containsKey("perPage") ? Integer.parseInt(allParams.get("perPage")) : null;

        Map<String, String> filters = new HashMap<>(allParams);
        filters.remove("page");
        filters.remove("perPage");

        Result<?> result = service.findEntitiesByFilters(filters, page, perPage);

        if (result != null && result.isSuccess()) {
            return ResponseEntity.ok(result.getObject());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result != null ? result : StatusMessages.UNKNOWN_ERROR);
    }

    @Operation(summary = "Get entity by ID", description = "Retrieve a specific entity by its ID.")
    @GetMapping("/{entityId}")
    public ResponseEntity<?> findById(
            @PathVariable @Parameter(description = "The ID of the entity to retrieve") Long entityId) {
        Result<Model> result = service.findById(entityId);

        if(result != null && result.isSuccess()) {
            return ResponseEntity.ok()
                    .body(result.getObject());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result != null ? result : StatusMessages.UNKNOWN_ERROR);
    }

    @Operation(summary = "Create a new entity", description = "Create a new entity based on the provided DTO.")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid @Parameter(description = "DTO containing the data to create the new entity") DtoCreate dto) {
        Result<Model> result = service.create(dto);

        if (result != null && result.isSuccess()) {
            var uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(result.getObject().getId())
                    .toUri();

            return ResponseEntity.created(uri).body(result.getObject());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result != null ? result : StatusMessages.UNKNOWN_ERROR);
    }

    @Operation(summary = "Create new entities.", description = "Create new entities based on the provided list of DTOs.")
    @PostMapping("/create-many")
    public ResponseEntity<?> create(@RequestBody @Valid @Parameter(description = "DTO list containing the data to create the new entities") List<DtoCreate> dtoList) {
        Result<List<Model>> result = service.createMany(dtoList);

        if (result != null && result.isSuccess()) {
            var uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(result.getObject().get(0).getId())
                    .toUri();

            return ResponseEntity.created(uri).body(result.getObject());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result != null ? result : StatusMessages.UNKNOWN_ERROR);
    }

    @Operation(summary = "Update an existing entity", description = "Update an existing entity with the provided DTO by entity ID.")
    @PutMapping("/update/{entityId}")
    public ResponseEntity<?> update(
            @PathVariable @Parameter(description = "The ID of the entity to update") Long entityId,
            @RequestBody @Valid @Parameter(description = "DTO containing the updated data of the entity") DtoUpdate dto) {
        Result<Model> result = service.update(entityId, dto);

        if (result != null && result.isSuccess()) {
            return ResponseEntity.ok()
                    .body(result.getObject());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result != null ? result : StatusMessages.UNKNOWN_ERROR);
    }

    @Operation(summary = "Soft delete an entity", description = "Soft delete an entity by ID, marking it as deleted without actually removing it from the database.")
    @PutMapping("/softDelete/{entityId}")
    public ResponseEntity<?> softDelete(@PathVariable @Parameter(description = "The ID of the entity to soft delete") Long entityId) {
        Result<Model> result = service.softDelete(entityId);

        if (result != null && result.isSuccess()) {
            return ResponseEntity.ok()
                    .body(result.getObject());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result != null ? result : StatusMessages.UNKNOWN_ERROR);
    }

    @Operation(summary = "Delete an entity", description = "Delete an entity permanently by its ID.")
    @DeleteMapping("/delete/{entityId}")
    public ResponseEntity<?> delete(@PathVariable @Parameter(description = "The ID of the entity to delete") Long entityId) {
        Result<Model> result = service.delete(entityId);

        if (result != null && result.isSuccess()) {
            return ResponseEntity.ok()
                    .body(StatusMessages.entityDeleted(result.getObject().getClass().getSimpleName()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result != null ? result : StatusMessages.UNKNOWN_ERROR);
    }

    @Operation(summary = "Clean up entities", description = "Hard delete entities based on a date filter.")
    @DeleteMapping("/cleanup")
    public ResponseEntity<?> cleanUp(@RequestParam(required = false) @Parameter(description = "Delete entities created after this date") Date dateBefore) {
        Result<Integer> result = service.cleanUp(dateBefore);

        if (result != null && result.isSuccess()) {
            return ResponseEntity.ok()
                    .body(StatusMessages.entitiesDeleted(result.getObject(), "entities"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result != null ? result : StatusMessages.UNKNOWN_ERROR);
    }
}