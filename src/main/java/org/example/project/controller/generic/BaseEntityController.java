package org.example.project.controller.generic;

import jakarta.validation.Valid;
import org.example.project.dto.generic.IBaseDtoCreate;
import org.example.project.dto.generic.IBaseDtoUpdate;
import org.example.project.model.generic.BaseEntity;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;
import java.util.Map;

public abstract class BaseEntityController
        <Model extends BaseEntity, DtoCreate extends IBaseDtoCreate, DtoUpdate extends IBaseDtoUpdate> {

    private final BaseEntityService<Model, DtoCreate, DtoUpdate> service;

    public BaseEntityController(BaseEntityService<Model, DtoCreate, DtoUpdate> service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Model>> findByFilters(@RequestParam(required = false) Map<String, String> filters) {
        return ResponseEntity.ok().body(service.getEntitiesByFilters(filters));
    }

    @GetMapping("/{entityId}")
    public ResponseEntity<Model> findById(@PathVariable Long entityId) {
        return ResponseEntity.ok().body(service.getById(entityId));
    }

    @PostMapping("/create")
    public ResponseEntity<Model> create(@RequestBody @Valid DtoCreate dto) {
        Model createdEntity = service.create(dto);

        if (createdEntity != null) {
            var uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdEntity.getId())
                    .toUri();

            return ResponseEntity.created(uri).body(createdEntity);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/update/{entityId}")
    public ResponseEntity<?> update(@PathVariable Long entityId, @RequestBody @Valid DtoUpdate dto) {
        Result<Model> result = service.update(entityId, dto);

        if (result != null && result.isSuccess()) {
            return ResponseEntity.ok().body(result.getObject());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result != null ? result.getMessage() : "Unknown error!");
    }

    @PutMapping("/softDelete/{entityId}")
    public ResponseEntity<?> softDelete(@PathVariable Long entityId) {
        Result<Model> result = service.softDelete(entityId);

        if (result != null && result.isSuccess()) {
            return ResponseEntity.ok().body(result.getObject());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result != null ? result.getMessage() : "Unknown error!");
    }

    @DeleteMapping("/delete/{entityId}")
    public ResponseEntity<?> delete(@PathVariable Long entityId) {
        Result<Model> result = service.softDelete(entityId);

        if (result != null && result.isSuccess()) {
            return ResponseEntity.ok().body(result.getObject());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(result != null ? result.getMessage() : "Unknown error!");
    }
}