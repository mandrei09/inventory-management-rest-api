package org.example.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.project.model.generic.CodeNameEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CostCenter extends CodeNameEntity {

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "division_id")
    @Schema(description = "The division to which this cost center belongs.", example = "Finance Division")
    private Division division;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "employee_cost_center",
            joinColumns = @JoinColumn(name = "cost_center_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    @Schema(description = "The employees assigned to this cost center.")
    private Set<Employee> employees = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "costCenter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asset> assets;

}
