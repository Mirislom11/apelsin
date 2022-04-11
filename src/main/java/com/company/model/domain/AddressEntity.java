package com.company.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "address")
public class AddressEntity extends BaseEntity{
    private String country;
    private String city;
    private String area;
}
