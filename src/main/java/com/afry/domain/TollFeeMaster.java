package com.afry.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Slf4j
@Entity
@Table(name = "toll_fee_master")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TollFeeMaster {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "toll_fee_master_id", updatable = false, nullable = false)
    private String tollFeeMasterId;

    @Column(name = "hour")
    private int hour;
    @Column(name = "min")
    private int min;
    @Column(name = "max")
    private int max;
    @Column(name = "tollFee")
    private int tollFee;

    public TollFeeMaster(int hour, int min, int max, int tollFee){
        this.hour = hour;
        this.min = min;
        this.max = max;
        this.tollFee = tollFee;
    }

}
