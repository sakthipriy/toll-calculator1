package com.afry.service.impl;

import com.afry.domain.*;
import com.afry.repository.TollFeeMasterRepository;
import com.afry.repository.TollFeeRegisterRepository;
import com.afry.repository.TollFreeDatesRepository;
import com.afry.repository.TollFreeVehiclesRepository;
import com.afry.service.TollFeeCalculatorService;
import com.afry.util.DateUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Month;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TollFeeCalculatorServiceImpl implements TollFeeCalculatorService {

    private TollFeeMasterRepository tollFeeMasterRepository;

    private TollFreeDatesRepository tollFreeDatesRepository;

    private TollFreeVehiclesRepository tollFreeVehiclesRepository;

    private TollFeeRegisterRepository tollFeeRegisterRepository;

    @Autowired
    public TollFeeCalculatorServiceImpl(TollFeeMasterRepository tollFeeMasterRepository,
                                        TollFreeDatesRepository tollFreeDatesRepository,
                                        TollFreeVehiclesRepository tollFreeVehiclesRepository,
                                        TollFeeRegisterRepository tollFeeRegisterRepository) {
        this.tollFeeMasterRepository = tollFeeMasterRepository;
        this.tollFreeDatesRepository = tollFreeDatesRepository;
        this.tollFreeVehiclesRepository = tollFreeVehiclesRepository;
        this.tollFeeRegisterRepository = tollFeeRegisterRepository;
    }

   /* public int getTotalFee(String vehicle, Date... dates) {
        Date intervalStart = dates[0];
        int totalFee = 0;
        for (Date date : dates) {
            TollFeeMaster nextFeeObj = getTollFee(vehicle, date);
            TollFeeMaster tempFeeObj = getTollFee(vehicle, intervalStart);
            int nextFee = nextFeeObj.getTollFee();
            int tempFee = tempFeeObj.getTollFee();
            TimeUnit timeUnit = TimeUnit.MINUTES;
            long diffInMillies = date.getTime() - intervalStart.getTime();
            long minutes = timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);

            if (minutes <= 60) {
                if (totalFee > 0) totalFee -= tempFee;
                if (nextFee >= tempFee) tempFee = nextFee;
                totalFee += tempFee;
            } else {
                totalFee += nextFee;
            }
        }
        if (totalFee > 60) totalFee = 60;
        return totalFee;
    }*/

    @Override
    public TollFeeMaster saveTollFee(TollFeeMaster tollFeeMaster) {
        return tollFeeMasterRepository.save(tollFeeMaster);
    }

    @Override
    public TollFreeDates saveTollFreeDates(TollFreeDates tollFreeDates) {
        return tollFreeDatesRepository.save(tollFreeDates);
    }

    @Override
    public TollFreeVehicles updateTollFreeVehicles(TollFreeVehicles tollFreeVehicles) {
        TollFreeVehicles tollFreeVehicle = getTollFreeVehicles(tollFreeVehicles.getVehicleType());
        if(tollFreeVehicle != null){
             tollFreeVehiclesRepository.delete(tollFreeVehicle);
        }
        return tollFreeVehiclesRepository.save(tollFreeVehicles);
    }


    @Override
    public TollFeeRegister getTollFee(Vehicle vehicle) throws ParseException {
        TollFeeMaster tollFee=getTollFee(vehicle.getVehicleType(),  DateUtil.convertStringToDate(vehicle.getDate()));
        TollFeeRegister tollFeeRegister = saveVehicleDetails(vehicle, tollFee !=null?tollFee.getTollFee():0);
        return tollFeeRegister;
    }

    public TollFeeMaster getTollFee(String vehicle, final Date date) {

        if(isTollFreeDate(date) || isTollFreeVehicle(vehicle)) return null;
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TollFeeMaster tollFee = tollFeeMasterRepository.findByHourAndMinLessThanEqualAndMaxGreaterThanEqual(hour, minute, minute);
        return tollFee;
    }


    @Override
    public int getTotalFee(String vehicle, String dateInString) throws ParseException {
        List<TollFeeRegister> tollFeeRegisterList = tollFeeRegisterRepository.findByVehicleNumberAndDateOrderByTime(vehicle, dateInString );
        final int[] totalFee = {0};
        if(!tollFeeRegisterList.isEmpty()) {
            Date intervalStart = DateUtil.convertStringToTime(tollFeeRegisterList.get(0).getTime());
            Date tempDate = new Date();
            tempDate.setTime(intervalStart.getTime() + 60 * 60000);
            while(tollFeeRegisterList.size() > 1 ) {
                TollFeeRegister tr = tollFeeRegisterList.stream().filter(t -> {
                    try {
                        return DateUtil.convertStringToTime(t.getTime()).getTime() <= tempDate.getTime();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList()).stream().max(Comparator.comparing(TollFeeRegister::getTollFee)).get();
                totalFee[0] = totalFee[0] + tr.getTollFee();
                tollFeeRegisterList = tollFeeRegisterList.stream().filter(t -> {
                    try {
                        return DateUtil.convertStringToTime(t.getTime()).getTime() > tempDate.getTime();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
                tempDate.setTime(tempDate.getTime() + 60 * 60000);
                if(tollFeeRegisterList.size() == 1){
                    totalFee[0] = totalFee[0] + tollFeeRegisterList.get(0).getTollFee();
                }
            }
        }
        if (totalFee[0] > 60) totalFee[0] = 60;
        return totalFee[0];
    }


    private TollFeeRegister saveVehicleDetails(Vehicle vehicle, int tollFee) throws ParseException {
        return tollFeeRegisterRepository.save(new TollFeeRegister(vehicle.getVehicleNumber(), vehicle.getVehicleType(),
                DateUtil.convertStrToDate(vehicle.getDate()), DateUtil.convertStrToTime(vehicle.getDate()), tollFee));
    }

    private boolean isTollFreeVehicle(String vehicleType) {
        //TollFreeVehicles tollFreeVehicles = getTollFreeVehicles(vehicleType);
        return (getTollFreeVehicles(vehicleType) != null && getTollFreeVehicles(vehicleType).getIsTollFree().booleanValue()) ? true : false;
    }

    private TollFreeVehicles getTollFreeVehicles(String vehicleType) {
        return tollFreeVehiclesRepository.findByVehicleType(vehicleType);
    }

    private Boolean isTollFreeDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) return true;

       // String monthValue = Month.of(month).toString();
        TollFreeDates dates = tollFreeDatesRepository.findByYearAndMonth(year, month.toUpperCase());
        if(dates != null){
            String holidays = dates.getHolidays();
            String[] days = holidays.split(",");
            List<String> daysList = Arrays.asList(days);
            boolean isMatch = daysList.stream().anyMatch(d -> Integer.parseInt(d) == day);
            if (isMatch)
                return true;
            return false;
        }
        return false;
    }

}
