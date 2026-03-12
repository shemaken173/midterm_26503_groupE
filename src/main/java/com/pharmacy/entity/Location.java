package com.pharmacy.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@Entity
@Table(name = "locations")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Human-readable name/code for this administrative unit
    private String name;
    private String code;

    // Type of the unit: PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE
    @Enumerated(EnumType.STRING)
    private LocationType type;

    // Self-referencing parent relationship (null for provinces)
    // Cascade ALL so that when we persist a village, the entire chain is saved
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    @JsonIgnoreProperties({"children", "user"})
    private Location parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Location> children;

    // When a user is attached, it should always be a VILLAGE
    @OneToOne(mappedBy = "location")
    @JsonIgnore
    private User user;

    // Helper method to get the full address path
    @JsonIgnore
    public String getFullPath() {
        if (parent == null) {
            return name;
        }
        return parent.getFullPath() + " > " + name;
    }

    // Helper method to get province (traverse up to root)
    @JsonIgnore
    public Location getProvince() {
        Location current = this;
        while (current.parent != null) {
            current = current.parent;
        }
        return current;
    }
}
