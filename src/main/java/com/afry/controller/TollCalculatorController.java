package com.afry.controller;

import com.afry.domain.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.afry.service.TollFeeCalculatorService;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/toll")
public class TollCalculatorController {

    @Autowired
    private TollFeeCalculatorService tollFeeCalculatorService;

    @RequestMapping(value = "/getTollFee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTollFee(@RequestBody Vehicle vehicle) throws ParseException {
        TollFeeRegister tollFee = tollFeeCalculatorService.getTollFee(vehicle);
        return new ResponseEntity<>(tollFee, HttpStatus.OK);
    }

    @RequestMapping(value = "/getTotalFee", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTotalFee(@RequestParam String vehicle, @RequestParam String date) throws ParseException {
        Integer totalFee = tollFeeCalculatorService.getTotalFee(vehicle, date);
        return  new ResponseEntity<>(totalFee, HttpStatus.OK);
    }

    @RequestMapping(value = "/saveTollFee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveTollFee(@RequestBody TollFeeMaster tollFeeMaster){
        return new ResponseEntity<>(tollFeeCalculatorService.saveTollFee(tollFeeMaster), HttpStatus.OK);
    }

    @RequestMapping(value = "/saveTollFreeDates", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveTollFreeDates(@RequestBody TollFreeDates tollFreeDates){
        return new ResponseEntity<>(tollFeeCalculatorService.saveTollFreeDates(tollFreeDates), HttpStatus.OK);
    }

    @RequestMapping(value = "/updateTollFreeVehicles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTollFreeVehicles(@RequestBody TollFreeVehicles tollFreeVehicles){
        return new ResponseEntity<>(tollFeeCalculatorService.updateTollFreeVehicles(tollFreeVehicles), HttpStatus.OK);
    }
}
