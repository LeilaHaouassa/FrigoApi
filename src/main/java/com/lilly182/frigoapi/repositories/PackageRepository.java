package com.lilly182.frigoapi.repositories;

import com.lilly182.frigoapi.models.Package;
import com.lilly182.frigoapi.models.PackageStatus;
import com.lilly182.frigoapi.models.Storehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PackageRepository extends JpaRepository<Package,Long> {

    public Set<Package> findAllByPackageStatus(PackageStatus status);
    public Set<Package> findAllByStorehouse(Storehouse storehouse);
}
