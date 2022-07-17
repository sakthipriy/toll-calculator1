package com.afry.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Slf4j
@Entity
@Table(name = "toll_free_vehicles")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TollFreeVehicles {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "toll_free_vehicle_id", updatable = false, nullable = false)
    private String tollFreeVehicleId;

    @Column(name = "vehicleType")
    private String vehicleType;

    @Column(name = "isTollFree")
    private Boolean isTollFree;
}
