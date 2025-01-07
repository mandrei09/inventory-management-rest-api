package org.example.project.controller.generic;

import jakarta.validation.Valid;
import org.example.project.dto.generic.BaseDto;
import org.example.project.model.generic.BaseEntity;
import org.example.project.service.generic.BaseEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;
import java.util.Map;

public abstract class BaseEntityController<Model extends BaseEntity, Dto extends BaseDto> {
    private final BaseEntityService<Model, Dto> service;

    public BaseEntityController(BaseEntityService<Model, Dto> service) {
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
    public ResponseEntity<Model> create(@RequestBody @Valid Dto dto) {
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
    public ResponseEntity<Model> update(@PathVariable Long entityId, @RequestBody @Valid Dto dto) {
        Model updatedEntity = service.update(entityId, dto);

        if (updatedEntity != null) {
            return ResponseEntity.ok().body(updatedEntity);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/softDelete/{entityId}")
    public ResponseEntity<Model> softDelete(@PathVariable Long entityId) {
        Model updatedEntity = service.softDelete(entityId);

        if (updatedEntity != null) {
            return ResponseEntity.ok().body(updatedEntity);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{entityId}")
    public ResponseEntity<Model> delete(@PathVariable Long entityId) {
        Model updatedEntity = service.delete(entityId);

        if (updatedEntity != null) {
            return ResponseEntity.ok().body(updatedEntity);
        }
        return ResponseEntity.notFound().build();
    }
}