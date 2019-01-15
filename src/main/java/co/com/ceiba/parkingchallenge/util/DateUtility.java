package co.com.ceiba.parkingchallenge.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class DateUtility {
	
	private DateUtility() {}

	public static long calcularHoursInParking(LocalDateTime registrationDate, LocalDateTime departureDate) {
		if(departureDate.isBefore(registrationDate)) throw new RuntimeException("Fecha de salida incorrecta. Esta antes de la fecha de registro");
		
		return Duration.between(registrationDate, departureDate).toHours(); 
	}
	
}
