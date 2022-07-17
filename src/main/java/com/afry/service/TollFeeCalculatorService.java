package com.afry.service;

import com.afry.domain.*;

import java.text.ParseException;
import java.util.Date;


public interface TollFeeCalculatorService {

    TollFeeMaster saveTollFee(TollFeeMaster tollFeeMaster);

    TollFreeDates saveTollFreeDates(TollFreeDates tollFreeDates);

    TollFreeVehicles updateTollFreeVehicles(TollFreeVehicles tollFreeVehicles);

    TollFeeRegister getTollFee(Vehicle vehicle) throws ParseException;

    int getTotalFee(String vehicle, String date) throws ParseException;

}
