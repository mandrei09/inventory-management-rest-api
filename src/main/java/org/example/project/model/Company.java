package org.example.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.project.model.generic.CodeNameEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Company extends CodeNameEntity {

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "manager_id")
    @Schema(description = "The manager of the company.", example = "John Doe")
    private Employee manager;
}
