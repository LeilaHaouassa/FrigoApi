package com.lilly182.frigoapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "storehouses")
public class Storehouse extends BaseEntity {
    private String location;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "storehouse")
    private List<Package> packages;
}
