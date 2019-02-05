package co.com.ceiba.parkingchallenge.services;

import java.util.List;

import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.exceptions.ViolatedConstraintException;
import co.com.ceiba.parkingchallenge.models.Registration;
import co.com.ceiba.parkingchallenge.models.Rule;
import co.com.ceiba.parkingchallenge.models.RuleDay;
import co.com.ceiba.parkingchallenge.models.Vehicle;
import co.com.ceiba.parkingchallenge.repositories.CarRepository;
import co.com.ceiba.parkingchallenge.repositories.ConstraintRepository;
import co.com.ceiba.parkingchallenge.repositories.MotorbikeRepository;
import co.com.ceiba.parkingchallenge.repositories.RegistrationRepository;

/**
 * 
 * @author ariel.arnedo
 *
 */
interface IControlVehicle {
	
	/**
	 * 
	 * @param vehicle
	 * @param constraintRepository
	 * @param registrationRepository
	 * @return
	 */
	Registration registerVehicle(Vehicle vehicle, ConstraintRepository constraintRepository, RegistrationRepository registrationRepository);
	
	/**
	 * 
	 * @param vehicle
	 * @param constraintRepository
	 * @param registrationRepository
	 * @return
	 */
	RegistrationEntity validateRegister(Vehicle vehicle, ConstraintRepository constraintRepository, RegistrationRepository registrationRepository);
	
	/**
	 * 
	 * @param carRepository
	 * @param motorbikeRepository
	 * @return
	 */
	List<Vehicle> listAllVehicles(CarRepository carRepository, MotorbikeRepository motorbikeRepository);
	
	/**
	 * 
	 * @param vehicle
	 * @param carRepository
	 * @param motorbikeRepository
	 * @return
	 */
	Vehicle saveVehicle(Vehicle vehicle, CarRepository carRepository, MotorbikeRepository motorbikeRepository);
	
	/**
	 * 
	 * @param plate
	 * @param carRepository
	 * @param motorbikeRepository
	 * @return
	 */
	Vehicle findVehicleByPlate(String plate, CarRepository carRepository, MotorbikeRepository motorbikeRepository);
	
	/**
	 * 
	 * @param plate
	 * @param registrationRepository
	 * @return
	 */
	RegistrationEntity verifyActiveRegistration(String plate, RegistrationRepository registrationRepository);
	
	/**
	 * 
	 * @param vehicle
	 * @param registrationRepository
	 */
	default void applyRuleVehicleActive(Vehicle vehicle, RegistrationRepository registrationRepository) {
		if(registrationRepository.findRegistrationActive(vehicle.getPlate()) != null)
			throw new ViolatedConstraintException("Este Vehiculo ya se encuentra en el parqueadero");
	}
	
	/**
	 * Aplica las reglas relacionadas con los dias habilidados de parkeo para vehiculos y las restricciones por cilindraje para motos
	 * 
	 * @param vehicle
	 * @param rules
	 */
	default void applyRulesOfParking(Vehicle vehicle, List<Rule> rules) {
		for (Rule rule : rules) {
			if (rule.getType().equals(Rule.Type.PLATE)) {
				RuleDay ruleDay = (RuleDay) rule;
				if (ruleDay.getPlace().verifyIfApplicable( vehicle.getPlate(), rule.getKey(), ruleDay.getDays())) {
					throw new ViolatedConstraintException("Hoy, el vehiculo con esta: "+ vehicle.getPlate() +" NO esta autorizado para ingresar");
				}
				return;
			}
		}
	}
}
