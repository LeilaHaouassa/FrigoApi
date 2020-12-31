package com.lilly182.frigoapi.services;


import com.lilly182.frigoapi.exceptions.ResourceNotFoundException;
import com.lilly182.frigoapi.models.Package;
import com.lilly182.frigoapi.models.PackageStatus;
import com.lilly182.frigoapi.models.Storehouse;
import com.lilly182.frigoapi.repositories.PackageRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Service
public class PackageService implements CrudService<Package,Long> {

    private final PackageRepository packageRepository;

    public PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    @Override
    public Set<Package> findAll() {
        Set<Package> packages = new HashSet<>();
        packageRepository.findAll().forEach(packages::add);

        for (Package aPackage: packages) {
            changePackageStatus(aPackage);
            packageRepository.save(aPackage);
        }
        return packages;
    }

    public void changePackageStatus(Package aPackage) {
        if(aPackage.getExitDate() != null){
            aPackage.setPackageStatus(PackageStatus.OUT);
        }else if(aPackage.getExpirationDate().compareTo(new Date()) <= 0) {
            aPackage.setPackageStatus(PackageStatus.EXPIRED);
        }
    }

    @Override
    public Package findById(Long id) {
        return packageRepository.findById(id).orElse(null);
    }

    @Override
    public Package update(Long packageId,Package packageDetails) {
        Package updatedPackage = findById(packageId);
        if(updatedPackage != null){
            updatedPackage.setEntryDate(packageDetails.getEntryDate());
            updatedPackage.setExitDate(packageDetails.getExitDate());
            updatedPackage.setExpirationDate(packageDetails.getExpirationDate());
            updatedPackage.setPackageType(packageDetails.getPackageType());
            updatedPackage.setQuantity(packageDetails.getQuantity());

            updatedPackage = packageRepository.save(updatedPackage);
        }
        return updatedPackage;
    }

    @Override
    public Package save(Package aPackage) {
        return packageRepository.save(aPackage);
    }

    @Override
    public void delete(Package aPackage) {
        packageRepository.delete(aPackage);
    }

    @Override
    public void deleteById(Long packageId) {
        Package toDeletePackage = findById(packageId);
        if(toDeletePackage == null) throw  new ResourceNotFoundException("No Package was found with such id "+ packageId);
        packageRepository.deleteById(packageId);
    }

    public Package addPackageToStorehouse(Storehouse storehouse, Package aPackage ){
        aPackage.setStorehouse(storehouse);
        storehouse.getPackages().add(aPackage);
        return aPackage;
    }
}
