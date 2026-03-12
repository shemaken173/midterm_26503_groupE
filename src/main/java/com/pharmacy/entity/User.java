package com.pharmacy.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // User is linked to a VILLAGE (the most specific location)
    // The village has parent references up to province
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    // Helper method to get province from user's village
    @JsonIgnore
    public String getProvinceName() {
        return location != null ? location.getProvince().getName() : null;
    }

    // Helper method to get province code
    @JsonIgnore
    public String getProvinceCode() {
        return location != null ? location.getProvince().getCode() : null;
    }

    // Helper method to get full address
    @JsonIgnore
    public String getFullAddress() {
        return location != null ? location.getFullPath() : null;
    }
}

