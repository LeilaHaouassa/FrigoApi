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

    public Storehouse getStorehouseByLocation(String location) {
        return getStorehouseByLocation(location, false);
    }

    // the ignoreNew parameter is to assure that when the storehouse is new this function always return
    // a null because there s no instance with similar location since the location is empty
    public Storehouse getStorehouseByLocation(String location, boolean ignoreNew) {
        location = location.toLowerCase();
        for (Storehouse storehouse : storehouseList) {
            if (!ignoreNew || !storehouse.isNew()) {
                String compText = storehouse.getLocation();
                compText = compText.toLowerCase();
                if (compText.equals(location)) {
                    return storehouse;
                }
            }
        }
        return null;
    }
}
