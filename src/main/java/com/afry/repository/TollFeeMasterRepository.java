package com.afry.repository;

import com.afry.domain.TollFeeMaster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TollFeeMasterRepository extends CrudRepository<TollFeeMaster, String> {

    TollFeeMaster findByHourAndMinLessThanEqualAndMaxGreaterThanEqual(int hour, int min, int max);
}
