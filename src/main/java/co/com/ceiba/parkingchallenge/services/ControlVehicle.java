package co.com.ceiba.parkingchallenge.services;

import java.io.File;
import java.util.List;

import org.mockito.Mock;
import org.springframework.stereotype.Component;

import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.entities.StateType;
import co.com.ceiba.parkingchallenge.exceptions.ViolatedConstraintException;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.FactoryRegistratration;
import co.com.ceiba.parkingchallenge.models.Rule;
import co.com.ceiba.parkingchallenge.models.RuleDay;
import co.com.ceiba.parkingchallenge.models.Vehicle;
import co.com.ceiba.parkingchallenge.repositories.ConstraintRepository;
import co.com.ceiba.parkingchallenge.repositories.RegistrationRepository;
import co.com.ceiba.parkingchallenge.util.ReaderContraintXml;

@Component
public class ControlVehicle implements IControlVehicle{
	
	
	
	@Override
	public RegistrationEntity validateRegister(Vehicle vehicle, ConstraintRepository constraintRepository, RegistrationRepository registrationRepository) {
		applyRuleVehicleActive(vehicle, registrationRepository); // Verifica que el vehiculo no se encuentre activo en el parqueadero
		
		Integer countMax = constraintRepository.numberMaxNumberVehicle(vehicle.getClass().getSimpleName()); 
		
		if (vehicle instanceof Car) {
			
			Integer carsInParking = registrationRepository.countActiveCar(); //Obtiene la cantidad de carros activos en el parqueadero
			
			if ((countMax == null || carsInParking == null) || countMax > carsInParking) {// Verifica que no se haya alcanzado en numero maximo de carros
				applyRulesOfParking(vehicle, new ReaderContraintXml().readerRules(new File("rules.xml") ,Rule.Type.PLATE)); //
				return FactoryRegistratration.create(StateType.ACTIVE, vehicle); // Crea una reservacion
			}
			
			throw new ViolatedConstraintException("Cantidad maxima de Carros alcanzados");
		} else {
			
			Integer bikesInParking = registrationRepository.countActiveMotorbike(); //Obtiene la cantidad de Motos activos en el parqueadero
			
			if ((countMax == null || bikesInParking == null) || countMax > bikesInParking) {
				return FactoryRegistratration.create(StateType.ACTIVE, vehicle); // Crea una reservacion
			} else
				throw new ViolatedConstraintException("Cantidad maxima de Motos alcanzados");
		}
	}
	
	/**
	 * 
	 * @param vehicle
	 * @param registrationRepository
	 */
	private void applyRuleVehicleActive(Vehicle vehicle, RegistrationRepository registrationRepository) {
		if(registrationRepository.findRegistrationActive(vehicle.getPlate()) != null)
			throw new ViolatedConstraintException("Este Vehiculo ya se encuentra en el parqueadero");
	}
	
	
	/**
	 * Aplica las reglas relacionadas con los dias habilidados de parkeo para vehiculos y las restricciones por cilindraje para motos
	 * 
	 * @param vehicle
	 * @param rules
	 */
	public void applyRulesOfParking(Vehicle vehicle, List<Rule> rules) {
		for (Rule rule : rules) {
			if (rule.getType().equals(Rule.Type.PLATE)) {
				RuleDay ruleDay = (RuleDay) rule;
				if (ruleDay.compare(ruleDay.getPlace(), vehicle.getPlate(), rule.getKey(), ruleDay.getDays()))
					throw new ViolatedConstraintException("Hoy, el vehiculo con esta: "+ vehicle.getPlate() +" NO esta autorizado para ingresar");
				return;
			}
		}
	}
}
