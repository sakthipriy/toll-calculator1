package com.afry.repository;

import com.afry.domain.TollFreeDates;
import com.afry.domain.TollFreeVehicles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TollFreeVehiclesRepository  extends CrudRepository<TollFreeVehicles, String> {

    TollFreeVehicles findByVehicleType(String vehicleType);
}
