package com.lilly182.frigoapi.bootstrap;

import com.lilly182.frigoapi.models.Owner;
import com.lilly182.frigoapi.services.OwnerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner{

    private final OwnerService ownerService;

    public DataLoader(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public void run(String... args) throws Exception {

        Owner owner1 = new Owner();
        owner1.setPhoneNumber("20357951");
        owner1.setFirstName("Foulan");
        owner1.setLastName("Foulani");
        owner1.setEmail("Foulan@gmail.com");
        owner1.setAge(23);
        owner1.setAddress("Mouzanbi9");

        ownerService.save(owner1);


        Owner owner2 = new Owner();
        owner2.setPhoneNumber("20357952");
        owner2.setFirstName("Foulana");
        owner2.setLastName("Foulaniya");
        owner2.setEmail("Foulana@gmail.com");
        owner2.setAge(22);
        owner2.setAddress("Mouritania");

        ownerService.save(owner2);
    }
}
