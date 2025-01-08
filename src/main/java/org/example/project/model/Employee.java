package org.example.project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.project.model.generic.BaseEntity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Employee extends BaseEntity {
    private String internalCode;
    private String name;
    private String lastName;
    private String email;

    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Employee manager;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    private Date birthDate;

    @ManyToMany(mappedBy = "employees")
    private Set<CostCenter> costCenters = new HashSet<>();

}
