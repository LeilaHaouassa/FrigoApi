package com.lilly182.frigoapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
public class PackageType extends BaseEntity{
    private String name;
}

// the type was chosen to be an entity instead of an Enum because the admin can add or delete
//types and as far as i know deleting enum without accessing the source code is not possible.