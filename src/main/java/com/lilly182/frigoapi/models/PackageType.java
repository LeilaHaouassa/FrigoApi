package com.lilly182.frigoapi.models;

import javax.persistence.Entity;

@Entity

public class PackageType extends BaseEntity{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}



// the type was chosen to be an entity instead of an Enum because the admin can add or delete
//types and as far as i know deleting enum without accessing the source code is not possible.