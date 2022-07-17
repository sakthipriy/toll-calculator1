package com.afry.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Map;

@Slf4j
@Entity
@Table(name = "toll_free_dates")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TollFreeDates {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "toll_free_dates_id", updatable = false, nullable = false)
    private String tollFreeDatesId;

    @Column(name = "year")
    private int year;

    @Column(name = "month")
    private String month;

    @Column(name = "holidays")
    private String holidays;
}
