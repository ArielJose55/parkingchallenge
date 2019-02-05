package co.com.ceiba.parkingchallenge.services.calculation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.entities.TariffEntity;
import co.com.ceiba.parkingchallenge.exceptions.ViolatedConstraintException;
import co.com.ceiba.parkingchallenge.util.DateUtility;

/**
 * 
 * @author ariel.arnedo
 *
 */
public interface ICalculation {

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

		if ( hours <= TariffApply.HOUR_LIMETE_START_DAY.getVelue() ) {// cobrar por hora

			Optional<TariffEntity> tariff = Lists.newArrayList(tariffs)
					.stream() 
					.filter(t -> t.getNumberHours() == TariffApply.BY_HOUR.getVelue() )
					.findFirst(); // encuentra una tarrifa aplicable para una hora

			if (!tariff.isPresent())
				throw new ViolatedConstraintException(
						"No existe tarrifa aplicable para este vehiculo para " + hours + " horas u horas");

			return (double) hours * tariff.get().getValue(); // retorna la cantidas de horas en parqueo multiplicada por la tarifa de una hora

		} else if ( hours <= TariffApply.BY_DAY.getVelue() ) {// cobrar por un dia

			Optional<TariffEntity> tariff = Lists.newArrayList(tariffs)
					.stream() 
					.filter(t -> t.getNumberHours() == TariffApply.BY_DAY.getVelue() )
					.findFirst(); // encuentra la tarrifa aplicable para una dia

			if (!tariff.isPresent())
				throw new ViolatedConstraintException(
						"No existe tarrifa aplicable para este vehiculo para " + hours + " horas");

			return tariff.get().getValue().doubleValue(); // retorna el valor de un dia de parqueo porque se demoro mas del 9 h y menos de 24 h

		} else {// cobrar por dias y horas 

			TariffEntity tariffHour = Lists.newArrayList(tariffs) // encuentra la tarrifa aplicable  para una hora
					.stream() 
					.filter(t -> t.getNumberHours() == TariffApply.BY_HOUR.getVelue() )
					.findFirst()
					.orElse( null );

			TariffEntity tariffDay = Lists.newArrayList(tariffs) // encuentra la tarrifa aplicable para una dia
					.stream() 
					.filter(t -> t.getNumberHours() == TariffApply.BY_DAY.getVelue() )
					.findFirst()
					.orElse( null );

			if (tariffHour == null || tariffDay == null)
				throw new ViolatedConstraintException("No existen tarrifas aplicables para este vehiculo");

			long numberDaysInParking = hours / TariffApply.BY_DAY.getVelue(); // dias es parqueo

			long numberHoursInParking = hours - (TariffApply.BY_DAY.getVelue() * numberDaysInParking); // horas restantes de parqueo

			return ((double) numberDaysInParking * tariffDay.getValue()) 
					+ ((double) numberHoursInParking * tariffHour.getValue());
		}
	}
}
