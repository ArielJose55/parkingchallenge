package co.com.ceiba.parkingchallenge.services.calculation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.entities.TariffEntity;
import co.com.ceiba.parkingchallenge.util.DateUtility;

/**
 * 
 * @author ariel.arnedo
 *
 */
public interface ICalculation {
	
	public static final int HOURS_DAY = 24;

	public static final int TARIFF_APPLY_HOUR = 1;

	public static final int TARIFF_APPLY_DAY = 24;

	public static final int HOUR_LIMETE_START_DAY = 9; // hora de comienzo de dia de parqueo

	/**
	 * 
	 * @param reEntity
	 * @param tarrif
	 * @return
	 */
	public double calculateAmountToPay(RegistrationEntity reEntity, Object entity,  List<TariffEntity> tariffs); 
	
	/**
	 * 
	 * @param reEntity
	 * @param tariffs
	 * @return
	 */
	public default double calculate(RegistrationEntity reEntity, Object entity, List<TariffEntity> tariffs) {
		
		long hours = DateUtility.calcularHoursInParking(reEntity.getRegistrationDate(), LocalDateTime.now()); 

		if (hours <= HOUR_LIMETE_START_DAY) {// cobrar por hora

			Optional<TariffEntity> tariff = Lists.newArrayList(tariffs).stream() // encuentra la tarrifa aplicable para
																					// una hora
					.filter(t -> t.getNumberHours() == TARIFF_APPLY_HOUR).findFirst();

			if (!tariff.isPresent())
				throw new RuntimeException(
						"No existe tarrifa aplicable para este vehiculo para " + TARIFF_APPLY_HOUR + " horas u horas");

			return (double) hours * tariff.get().getValue();

		} else if (hours <= HOURS_DAY) {// cobrar por un dia

			Optional<TariffEntity> tariff = Lists.newArrayList(tariffs).stream() // encuentra la tarrifa aplicable para
																					// una dia
					.filter(t -> t.getNumberHours() == TARIFF_APPLY_DAY).findFirst();

			if (!tariff.isPresent())
				throw new RuntimeException(
						"No existe tarrifa aplicable para este vehiculo para " + TARIFF_APPLY_DAY + " horas u horas");

			return tariff.get().getValue().doubleValue();

		} else {// cobrar por dias y horas

			Optional<TariffEntity> tariffHour = Lists.newArrayList(tariffs).stream() // encuentra la tarrifa aplicable  para una hora
					.filter(t -> t.getNumberHours() == TARIFF_APPLY_HOUR).findFirst();

			Optional<TariffEntity> tariffDay = Lists.newArrayList(tariffs).stream() // encuentra la tarrifa aplicable para una dia
					.filter(t -> t.getNumberHours() == TARIFF_APPLY_DAY).findFirst();

			if (!tariffHour.isPresent() || !tariffDay.isPresent())
				throw new RuntimeException("No existen tarrifas aplicables para este vehiculo");

			long numberDaysInParking = hours / HOURS_DAY;

			long numberHoursInParking = hours - (HOURS_DAY * numberDaysInParking);

			return ((double) numberDaysInParking * tariffDay.get().getValue())
					+ ((double) numberHoursInParking * tariffHour.get().getValue());
		}
	}
}
