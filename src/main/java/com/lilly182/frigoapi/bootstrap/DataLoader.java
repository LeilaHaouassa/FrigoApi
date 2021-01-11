package com.lilly182.frigoapi.bootstrap;

import com.lilly182.frigoapi.models.Owner;
import com.lilly182.frigoapi.models.Package;
import com.lilly182.frigoapi.models.PackageType;
import com.lilly182.frigoapi.models.Storehouse;
import com.lilly182.frigoapi.services.OwnerService;
import com.lilly182.frigoapi.services.PackageService;
import com.lilly182.frigoapi.services.PackageTypeService;
import com.lilly182.frigoapi.services.StorehouseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

// this class allows me to create some dummy data.
@Component
public class DataLoader implements CommandLineRunner{

    private final OwnerService ownerService;
    private final StorehouseService storehouseService;
    private final PackageService packageService;
    private final PackageTypeService packageTypeService;

    public DataLoader(OwnerService ownerService, StorehouseService storehouseService, PackageService packageService, PackageTypeService packageTypeService) {
        this.ownerService = ownerService;
        this.storehouseService = storehouseService;
        this.packageService = packageService;
        this.packageTypeService = packageTypeService;
    }

    public static Date provideDate(Calendar cal, int year, int day , int month){
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    @Override
    public void run(String... args) throws Exception {

        Calendar cal = Calendar.getInstance();

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

        Storehouse storehouse = new Storehouse();
        storehouse.setName("Frigo of Foulan");
        storehouse.setLocation("Hay Riadh 5");
        storehouseService.addStorehouseToOwner(owner1,storehouse);

        PackageType type1 = new PackageType();
        type1.setName("Fish");

        PackageType type2 = new PackageType();
        type2.setName("Fruit");

        packageTypeService.save(type1);
        packageTypeService.save(type2);

        Package aPackage1 = new Package();
        aPackage1.setQuantity(20.0);
        aPackage1.setPackageType(type1);
        Date dateOfExpiration = provideDate(cal,2021,2,2);
        aPackage1.setExpirationDate(dateOfExpiration);
        packageService.addPackageToStorehouse(storehouse,aPackage1);


        Package aPackage2 = new Package();
        aPackage2.setQuantity(20.0);
        aPackage2.setPackageType(type1);
        dateOfExpiration = provideDate(cal,2021,10,1);
        aPackage2.setExpirationDate(dateOfExpiration);
        Date dateOfExit =provideDate(cal,2021,7,1);
        aPackage2.setExitDate(dateOfExit);
        packageService.addPackageToStorehouse(storehouse,aPackage2);

        storehouseService.save(storehouse);

        packageService.save(aPackage1);
        packageService.save(aPackage2);




    }
}
