package service;

import com.afry.domain.TollFeeMaster;
import com.afry.domain.TollFeeRegister;
import com.afry.domain.Vehicle;
import com.afry.service.TollFeeCalculatorService;
import com.afry.service.impl.TollFeeCalculatorServiceImpl;
import com.afry.util.DateUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.afry.repository.TollFeeMasterRepository;
import com.afry.repository.TollFreeDatesRepository;

import java.text.ParseException;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TollFeeCalculatorServiceImplTest {

    private TollFeeMasterRepository tollFeeMasterRepository;

    private TollFreeDatesRepository tollFreeDatesRepository;

    private TollFeeCalculatorServiceImpl tollFeeCalculatorService;

    @BeforeEach
    void setUp() throws ParseException {
        tollFeeMasterRepository = mock(TollFeeMasterRepository.class);
        tollFreeDatesRepository = mock(TollFreeDatesRepository.class);
        tollFeeCalculatorService = mock(TollFeeCalculatorServiceImpl.class);

        TollFeeMaster tollFeeMaster = new TollFeeMaster(6,0, 29, 8);
        TollFeeMaster tollFeeMaster1 = new TollFeeMaster(6,30, 59, 13);
        TollFeeMaster tollFeeMaster2 = new TollFeeMaster(8,0, 59, 13);
        TollFeeMaster tollFeeMaster3 = new TollFeeMaster(9,0, 59, 18);
        when(tollFeeMasterRepository.save(tollFeeMaster)).thenReturn(tollFeeMaster);
        when(tollFeeMasterRepository.save(tollFeeMaster)).thenReturn(tollFeeMaster1);
        when(tollFeeMasterRepository.save(tollFeeMaster)).thenReturn(tollFeeMaster2);
        when(tollFeeMasterRepository.save(tollFeeMaster)).thenReturn(tollFeeMaster3);

        Vehicle vehicle1 = new Vehicle("TN0222", "Ford","Car","2020-01-03 06:25:15");
        Vehicle vehicle2 = new Vehicle("TN0222", "Ford","Car","2020-01-03 06:45:15");
        Vehicle vehicle3 = new Vehicle("TN0222", "Ford","Car","2020-01-03 08:25:15");
        Vehicle vehicle4 = new Vehicle("TN0222", "Ford","Car","2020-01-03 09:25:15");

        TollFeeRegister tollFeeRegister1 = new TollFeeRegister("1","TN0222", "Car", "2020-01-03", "06:25:15", 8);
        TollFeeRegister tollFeeRegister2 = new TollFeeRegister("1","TN0222", "Car", "2020-01-03", "06:45:15", 13);
        TollFeeRegister tollFeeRegister3 = new TollFeeRegister("1","TN0222", "Car", "2020-01-03", "08:25:15", 13);
        TollFeeRegister tollFeeRegister4 = new TollFeeRegister("1","TN0222", "Car", "2020-01-03", "09:25:15", 18);

        when(tollFeeCalculatorService.getTollFee(vehicle1)).thenReturn(tollFeeRegister1);
        when(tollFeeCalculatorService.getTollFee(vehicle2)).thenReturn(tollFeeRegister2);
        when(tollFeeCalculatorService.getTollFee(vehicle3)).thenReturn(tollFeeRegister3);
        when(tollFeeCalculatorService.getTollFee(vehicle4)).thenReturn(tollFeeRegister4);

        when(tollFeeCalculatorService.getTotalFee("TN0222","2020-01-03")).thenReturn(34);

    }

    @Test
    public void testGetTollFee() throws ParseException {
        Vehicle vehicle = new Vehicle("TN0222", "Ford","Car","2020-01-03 09:25:15");
        TollFeeRegister tollFeeRegister1 = tollFeeCalculatorService.getTollFee(vehicle);
        assertNotNull(tollFeeRegister1);
        assertThat(tollFeeRegister1.getTollFee(), is(18));
    }

    @Test
    public void testSaveTollFee() throws ParseException {
        TollFeeMaster tollFeeMaster = new TollFeeMaster(9,0, 29, 13);
        tollFeeMasterRepository.save(tollFeeMaster);
        assertThat(tollFeeMaster.getTollFee(), is(13));
    }

    @Test
    public void testGetTotalFee() throws ParseException {
       int totalFee =  tollFeeCalculatorService.getTotalFee("TN0222","2020-01-03");
        assertThat(totalFee, is(34));
    }


}
