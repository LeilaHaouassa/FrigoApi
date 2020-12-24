package com.lilly182.frigoapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "owners")
public class Owner extends BaseEntity{
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    private String email;
    private String phoneNumber;
    @Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 100, message = "Age should not be greater than 100")
    private int age;
    @NotBlank
    private String address;
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "owner")
    private List<Storehouse> storehouseList;


    public String getFullName(){
        return firstName+" "+lastName;
    }


}
