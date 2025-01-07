package org.example.project.model.generic;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class CodeNameEntity extends BaseEntity {
    private String code;
    private String name;
}
