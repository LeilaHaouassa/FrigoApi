package com.lilly182.frigoapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "storehouses")
public class Storehouse extends BaseEntity {
    private String location;
    @NotBlank
    private String name;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "storehouse")
    private Set<Package> packages = new HashSet<>();

}
