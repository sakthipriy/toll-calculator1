package com.afry.repository;

import com.afry.domain.TollFeeMaster;
import com.afry.domain.TollFeeRegister;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TollFeeRegisterRepository  extends CrudRepository<TollFeeRegister, String> {

    List<TollFeeRegister> findByVehicleNumber(String vehicleNumber);
    List<TollFeeRegister> findByVehicleNumberAndDateOrderByTime(String vehicleNumber, String date);
}
