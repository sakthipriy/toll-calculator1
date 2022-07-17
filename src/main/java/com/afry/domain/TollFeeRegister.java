package com.afry.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Slf4j
@Entity
@Table(name = "toll_fee_register")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TollFeeRegister {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "toll_fee_register_id", updatable = false, nullable = false)
    private String tollFeeRegisterId;

    @Column(name = "vehicleNumber")
    private String vehicleNumber;
    @Column(name = "vehicleType")
    private String vehicleType;
    @Column(name = "date")
    private String date;

    @Column(name = "time")
    private String time;
    @Column(name = "tollFee")
    private int tollFee;

    public TollFeeRegister(String vehicleNumber, String vehicleType, String date, String time, int tollFee) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.date = date;
        this.time = time;
        this.tollFee = tollFee;
    }

}
