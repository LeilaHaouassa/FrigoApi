package com.lilly182.frigoapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
public class PackageType extends BaseEntity{
    private String name;
//    //This attribute designs the maximum healthy duration for a package to be stored
//    private int limitDuration;
}
