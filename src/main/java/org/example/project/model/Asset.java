package org.example.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.project.model.generic.CodeNameEntity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Asset extends CodeNameEntity {
    private Double value;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cost_center_id")
    private CostCenter costCenter;

    private Date acquisitionDate;
}
