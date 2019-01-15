package co.com.ceiba.parkingchallenge.services.calculation;

import java.util.List;

import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.entities.TariffEntity;

public class CalculationPaymentCar implements ICalculation {

	@Override
	public double calculateAmountToPay(RegistrationEntity reEntity, Object entity, List<TariffEntity> tariffs) {
		return calculate(reEntity, entity, tariffs);
	}
}
