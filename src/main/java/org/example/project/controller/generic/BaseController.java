package org.example.project.controller.generic;

import jakarta.validation.Valid;
import org.example.project.dto.generic.IBaseDtoCreate;
import org.example.project.dto.generic.IBaseDtoUpdate;
import org.example.project.model.generic.BaseEntity;
import org.example.project.result.Result;
import org.example.project.service.generic.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/api")
public abstract class BaseController
        <Model extends BaseEntity, DtoCreate extends IBaseDtoCreate, DtoUpdate extends IBaseDtoUpdate> {

    private final BaseService<Model, DtoCreate, DtoUpdate> service;

    public BaseController(BaseService<Model, DtoCreate, DtoUpdate> service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Model>> findByFilters(@RequestParam(required = false) Map<String, String> filters) {
        return ResponseEntity.ok().body(service.findEntitiesByFilters(filters));
    }

    @GetMapping("/{entityId}")
    public ResponseEntity<Model> findById(@PathVariable Long entityId) {
        return ResponseEntity.ok().body(service.findById(entityId));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid DtoCreate dto) {
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
        Result<Model> result = service.delete(entityId);

        if (result != null && result.isSuccess()) {
            return ResponseEntity.ok().body(result.getObject());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(result != null ? result.getMessage() : "Unknown error!");
    }

    @DeleteMapping("/cleanup")
    public ResponseEntity<?> cleanUp(@RequestParam(required = false) Date dateAfter) {
        Result<Integer> result = service.cleanUp(dateAfter);

        if (result != null && result.isSuccess()) {
            return ResponseEntity.ok().body(result.getObject() + " entities deleted!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result != null ? result.getMessage() : "Unknown error!");
    }
}