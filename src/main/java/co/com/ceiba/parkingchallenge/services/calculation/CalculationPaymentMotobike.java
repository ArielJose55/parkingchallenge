package co.com.ceiba.parkingchallenge.services.calculation;

import java.io.File;
import java.util.List;

import co.com.ceiba.parkingchallenge.entities.MotorbikeEntity;
import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.entities.TariffEntity;
import co.com.ceiba.parkingchallenge.models.Rule;
import co.com.ceiba.parkingchallenge.models.RuleDisplacement;
import co.com.ceiba.parkingchallenge.util.ReaderContraintXml;

/**
 * 
 * @author ariel.arnedo
 *
 */
public class CalculationPaymentMotobike implements ICalculation{

	@Override
	public double calculateAmountToPay(RegistrationEntity reEntity, Object entity, List<TariffEntity> tariffs) {
		File fileRules = new File("rules.xml");
		ReaderContraintXml reader = new ReaderContraintXml();
		MotorbikeEntity motor = (MotorbikeEntity) entity;
		List<Rule> rules = reader.readerRules(fileRules ,Rule.Type.DISPLACEMENT);
		
		double valueAdded = rules.stream()
			.map(r -> (RuleDisplacement)r)
			.filter(r->r.isApplyRule(motor.getDisplacement()))
			.mapToDouble(RuleDisplacement::getValueAdded)
			.sum();
		return valueAdded + calculate(reEntity, entity, tariffs);
	}
}
