package org.example.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.project.model.generic.CodeNameEntity;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Asset extends CodeNameEntity {

    @Schema(description = "The value of the asset.", example = "1000.00")
    private Double value;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cost_center_id")
    @Schema(description = "The cost center associated with the asset.")
    private CostCenter costCenter;

    @Schema(description = "The acquisition date of the asset.", example = "2025-01-01")
    private Date acquisitionDate;
}
