package org.example.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.example.project.model.generic.CodeNameEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company extends CodeNameEntity {
    @OneToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @OneToMany
    private List<Employee> employees;
}
