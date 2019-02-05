package co.com.ceiba.parkingchallenge.services;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.parkingchallenge.models.Invoice;

import co.com.ceiba.parkingchallenge.models.Registration;
import co.com.ceiba.parkingchallenge.models.Vehicle;


/**
 * 
 * 
 * @author ariel.arnedo
 *
 */
@Service
public class RegistrationService {

	@Autowired
	private ControlRegistration controlRegistration;
	
	/**
	 * 
	 * 
	 * @return
	 */
	public Optional<List<Registration>> listAllRegistrations(){
		return Optional.ofNullable(controlRegistration
				.listAllRegistrations());
	}
	
	/**
	 * 
	 * 
	 * @param vehicle
	 * @return
	 */
	public Optional<Invoice> registerVehicularExit(Vehicle vehicle){
		return Optional.ofNullable( controlRegistration
				.registerCheckOutVehicular ( vehicle ));
	}
}
