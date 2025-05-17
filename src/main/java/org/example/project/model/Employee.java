package org.example.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.project.model.generic.BaseEntity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Employee extends BaseEntity {

    @Schema(description = "Internal code of the employee.", example = "EMP123")
    private String internalCode;

    @Schema(description = "Name of the employee.", example = "John")
    private String name;

    @Schema(description = "Last name of the employee.", example = "Doe")
    private String lastName;

    @Schema(description = "Email address of the employee.", example = "john.doe@example.com")
    private String email;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    @Schema(description = "Manager of the employee.", example = "EMP456")
    private Employee manager;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @Schema(description = "The company where the employee works.", example = "TechCorp")
    private Company company;

    @Schema(description = "Birth date of the employee.", example = "1985-08-15")
    private Date birthDate;

    @JsonBackReference
    @ManyToMany(mappedBy = "employees")
    @Schema(description = "Cost centers associated with the employee.")
    private Set<CostCenter> costCenters = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "manager")
    private List<Employee> subordinates;
}
