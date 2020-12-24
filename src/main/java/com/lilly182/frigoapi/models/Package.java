package com.lilly182.frigoapi.models;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
public class Package extends BaseEntity {
    @OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PackageType packageType;
    @ManyToOne
    @JoinColumn(name = "storehouse_id", nullable = false)
    private Storehouse storehouse;
    @CreationTimestamp
    private Date entryDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date exitDate;
    @Temporal(TemporalType.DATE)
    private Date expirationDate;
    private Double quantity;
    @Enumerated(value = EnumType.STRING)
    private PackageStatus packageStatus = PackageStatus.IN;
}
