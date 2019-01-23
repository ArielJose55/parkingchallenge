package co.com.ceiba.parkingchallenge.unityTests.util;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.parkingchallenge.exceptions.ViolatedConstraintException;
import co.com.ceiba.parkingchallenge.util.DateUtility;

@RunWith(SpringRunner.class)
public class DateUtilityTest {
	

	@Test
	public void calcularHoursInParking() {
		
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime yesterday = today.minusDays(1);
		
		assertThat(DateUtility.calcularHoursInParking(yesterday, today))
			.isEqualTo(24);
	}
	
	@Test
	public void calcularHoursInParkingThrowException() {
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime yesterday = today.minusDays(1);
		
		try {
			DateUtility.calcularHoursInParking(today, yesterday);
			
			fail("ViolatedConstraintExcepcion experada porque la fecha de registro tiene que ser anterior a la fecha salida");
		}catch (ViolatedConstraintException e) {
			assertThat(e)
				.hasMessage("Fecha de salida incorrecta. Esta antes de la fecha de registro");
		}
	}
}
