package co.com.ceiba.parkingchallenge.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.entities.StateType;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.FactoryRegistratration;
import co.com.ceiba.parkingchallenge.models.Rule;
import co.com.ceiba.parkingchallenge.models.RuleDay;
import co.com.ceiba.parkingchallenge.models.Vehicle;
import co.com.ceiba.parkingchallenge.repositories.ConstraintRepository;
import co.com.ceiba.parkingchallenge.repositories.RegistrationRepository;
import co.com.ceiba.parkingchallenge.util.ReaderContraint;

@Component
public class ControlVehicle implements IControlVehicle{
	
	@Autowired
	private ReaderContraint readerConstraint;
	
	@Override
	public RegistrationEntity validateRegister(Vehicle vehicle, ConstraintRepository constraintRepository, RegistrationRepository registrationRepository) {
		applyRuleVehicleActive(vehicle, registrationRepository); // Verifica que el vehiculo no se encuentre activo en el parqueadero
		Integer countMax = constraintRepository.numberMaxNumberVehicle(vehicle.getClass().getSimpleName()); 
		if (vehicle instanceof Car) {
			Integer countInParking = registrationRepository.countActiveCar(); //Obtiene la cantidad de carros activos en el parqueadero
			if ((countMax == null || countInParking == null) || countMax >= countInParking) {// Verifica que no se haya alcanzado en numero maximo de carros
				applyRulesOfParking(vehicle, readerConstraint.readerRules(Rule.Type.PLATE)); //
				return FactoryRegistratration.create(StateType.ACTIVE, vehicle); // Crea una reservacion
			}
			throw new RuntimeException("Cantidad maxima de Carros alcanzados");
		} else {
			if (countMax < registrationRepository.countActiveMotorbike()) {// Verifica que no se haya alcanzado en
																			// numero maximo de motos
				return FactoryRegistratration.create(StateType.ACTIVE, vehicle); // Crea una reservacion
			} else
				throw new RuntimeException("Cantidad maxima de Motos alcanzados");
		}
	}
	
	private void applyRuleVehicleActive(Vehicle vehicle, RegistrationRepository registrationRepository) {
		if(registrationRepository.findRegistrationActive(vehicle.getPlate()) != null)
			throw new RuntimeException("Este Vehiculo ya se encuentra en el parqueadero");
	}
	
	/**
	 * Aplica las reglas relacionadas con los dias habilidados de parkeo para vehiculos y las restricciones por cilindraje para motos
	 * 
	 * @param vehicle
	 * @param rules
	 */
	private void applyRulesOfParking(Vehicle vehicle, List<Rule> rules) {
		for (Rule rule : rules) {
			if (rule.getType().equals(Rule.Type.PLATE)) {
				RuleDay ruleDay = (RuleDay) rule;
				if (ruleDay.compare(ruleDay.getPlace(), vehicle.getPlate(), rule.getKey(), ruleDay.getDays()))
					throw new RuntimeException("Hoy, esta autorizado a ingresar");
				return;
			} else {
				throw new UnsupportedOperationException("Metodo no implmentado");
			}
		}
	}
}
