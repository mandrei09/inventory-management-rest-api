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
public class Inventory extends CodeNameEntity {

    @Schema(description = "Additional information related to the inventory.", example = "Inventory for Q1 2025")
    private String info;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "company_id")
    @Schema(description = "The company associated with the inventory.", example = "TechCorp")
    private Company company;

    @Schema(description = "The start date of the inventory period.", example = "2025-01-01")
    private Date startDate;

    @Schema(description = "The end date of the inventory period.", example = "2025-03-31")
    private Date endDate;
}
