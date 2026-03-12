package com.pharmacy.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "drugs")
@Data
public class Drug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
        name = "supplier_drug",
        joinColumns = @JoinColumn(name = "drug_id"),
        inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    @JsonIgnore
    private List<Supplier> suppliers;
}
