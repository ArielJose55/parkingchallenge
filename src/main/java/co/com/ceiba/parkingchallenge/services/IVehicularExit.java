package co.com.ceiba.parkingchallenge.services;

import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.repositories.RegistrationRepository;

/**
 * 
 * @author ariel.arnedo
 *
 */
public interface IVehicularExit {

	/**
	 * 
	 * @param plate
	 * @param registrationRepository
	 * @return
	 */
	public RegistrationEntity verifyActiveRegistration(String plate, RegistrationRepository registrationRepository);
}
