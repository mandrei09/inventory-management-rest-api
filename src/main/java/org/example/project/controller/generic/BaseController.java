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
import org.example.project.utils.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<Model>> findByFilters(
            @RequestParam(required = false)
            @Parameter(description = "Filters to apply to the query") Map<String, String> filters) {
        return ResponseEntity.ok().body(service.findEntitiesByFilters(filters));
    }

    @Operation(summary = "Get entity by ID", description = "Retrieve a specific entity by its ID.")
    @GetMapping("/{entityId}")
    public ResponseEntity<Model> findById(
            @PathVariable @Parameter(description = "The ID of the entity to retrieve") Long entityId) {
        return ResponseEntity.ok().body(service.findById(entityId));
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
                .body(result != null ? result.getMessage() : ErrorCodes.UNKNOWN_ERROR);
    }

    @Operation(summary = "Update an existing entity", description = "Update an existing entity with the provided DTO by entity ID.")
    @PutMapping("/update/{entityId}")
    public ResponseEntity<?> update(
            @PathVariable @Parameter(description = "The ID of the entity to update") Long entityId,
            @RequestBody @Valid @Parameter(description = "DTO containing the updated data of the entity") DtoUpdate dto) {
        Result<Model> result = service.update(entityId, dto);

        if (result != null && result.isSuccess()) {
            return ResponseEntity.ok().body(result.getObject());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result != null ? result.getMessage() : ErrorCodes.UNKNOWN_ERROR);
    }

    @Operation(summary = "Soft delete an entity", description = "Soft delete an entity by ID, marking it as deleted without actually removing it from the database.")
    @PutMapping("/softDelete/{entityId}")
    public ResponseEntity<?> softDelete(@PathVariable @Parameter(description = "The ID of the entity to soft delete") Long entityId) {
        Result<Model> result = service.softDelete(entityId);

        if (result != null && result.isSuccess()) {
            return ResponseEntity.ok().body(result.getObject());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result != null ? result.getMessage() : ErrorCodes.UNKNOWN_ERROR);
    }

    @Operation(summary = "Delete an entity", description = "Delete an entity permanently by its ID.")
    @DeleteMapping("/delete/{entityId}")
    public ResponseEntity<?> delete(@PathVariable @Parameter(description = "The ID of the entity to delete") Long entityId) {
        Result<Model> result = service.delete(entityId);

        if (result != null && result.isSuccess()) {
            return ResponseEntity.ok().body(result.getObject());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result != null ? result.getMessage() : ErrorCodes.UNKNOWN_ERROR);
    }

    @Operation(summary = "Clean up entities", description = "Hard delete entities based on a date filter.")
    @DeleteMapping("/cleanup")
    public ResponseEntity<?> cleanUp(@RequestParam(required = false) @Parameter(description = "Delete entities created after this date") Date dateAfter) {
        Result<Integer> result = service.cleanUp(dateAfter);

        if (result != null && result.isSuccess()) {
            return ResponseEntity.ok().body(result.getObject() + " entities deleted!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result != null ? result.getMessage() : ErrorCodes.UNKNOWN_ERROR);
    }
}
