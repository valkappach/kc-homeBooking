package com.kcbooking.kchomebooking.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Embeddable
public class Address {

    private String streetLine1;
    private String streetLine2;
    private String city;
    private int postCode;
    private String country;


}
