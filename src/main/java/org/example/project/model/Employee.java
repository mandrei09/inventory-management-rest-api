package org.example.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.example.project.model.generic.BaseEntity;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee extends BaseEntity {
    private String internalCode;
    private String name;
    private String lastName;
    private String email;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private Date birthDate;

}
