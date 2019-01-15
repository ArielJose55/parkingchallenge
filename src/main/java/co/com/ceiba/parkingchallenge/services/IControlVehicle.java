package co.com.ceiba.parkingchallenge.services;

import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.models.Vehicle;
import co.com.ceiba.parkingchallenge.repositories.ConstraintRepository;
import co.com.ceiba.parkingchallenge.repositories.RegistrationRepository;

/**
 * 
 * @author ariel.arnedo
 *
 */
public interface IControlVehicle {	
	public RegistrationEntity validateRegister(Vehicle vehicle, ConstraintRepository constraintRepository, RegistrationRepository registrationRepository);
}
