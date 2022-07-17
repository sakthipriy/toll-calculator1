package com.afry.repository;

import com.afry.domain.TollFreeDates;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TollFreeDatesRepository extends CrudRepository<TollFreeDates, String> {

    TollFreeDates findByYearAndMonth(int year, String month);
}
