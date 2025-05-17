package org.example.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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

    @JsonBackReference
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Department> departments;

    @JsonBackReference
    @OneToMany(mappedBy = "company")
    private List<Employee> employees;
}
