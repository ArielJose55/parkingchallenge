package co.com.ceiba.parkingchallenge.services;

import org.springframework.stereotype.Component;


import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.repositories.RegistrationRepository;


@Component
public class ControlVehicularExit implements IVehicularExit{
	
	@Override
	public RegistrationEntity verifyActiveRegistration(String plate, RegistrationRepository registrationRepository) {
		return registrationRepository.findRegistrationActive(plate);
	}
}
