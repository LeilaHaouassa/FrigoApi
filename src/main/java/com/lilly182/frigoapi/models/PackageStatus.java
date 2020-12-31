package com.lilly182.frigoapi.models;

public enum PackageStatus {
        IN, OUT, EXPIRED
}
// IN is the default status for a package which means that a package is IN the storehouse
// OUT  means that a package in OUT of the storehouse
// IN  means that a package's   expiration date has passed while the exit date is still blank