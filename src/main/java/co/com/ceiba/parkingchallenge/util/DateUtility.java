package co.com.ceiba.parkingchallenge.util;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;

import co.com.ceiba.parkingchallenge.exceptions.ViolatedConstraintException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DateUtility {

	private DateUtility() {}
	
	public static long calcularHoursInParking(LocalDateTime registrationDate, LocalDateTime departureDate) {
		if (departureDate.isBefore(registrationDate)) {
			log.error(MessageFormat.format("La fecha de salida {0} tiene que ser porterior a la fecha de {1}" , departureDate, registrationDate));
			throw new ViolatedConstraintException("Fecha de salida incorrecta. Esta antes de la fecha de registro");
		}

		return Duration.between(registrationDate, departureDate).toHours();
	}

}
