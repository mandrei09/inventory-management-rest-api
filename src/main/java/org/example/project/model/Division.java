package org.example.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.project.model.generic.CodeNameEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Division extends CodeNameEntity {

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "department_id")
    @Schema(description = "The department to which this division belongs.", example = "Engineering")
    private Department department;
}
