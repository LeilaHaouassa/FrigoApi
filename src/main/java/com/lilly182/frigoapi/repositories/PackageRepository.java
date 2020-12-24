package com.lilly182.frigoapi.repositories;

import com.lilly182.frigoapi.models.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package,Long> {
}
