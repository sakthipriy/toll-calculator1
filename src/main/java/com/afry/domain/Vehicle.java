package com.afry.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vehicle {

    private String vehicleNumber;
    private String vehicleName;
    private String vehicleType;
    private String date;

}
