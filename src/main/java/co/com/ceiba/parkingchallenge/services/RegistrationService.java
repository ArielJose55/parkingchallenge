package co.com.ceiba.parkingchallenge.services;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.parkingchallenge.models.Invoice;

import co.com.ceiba.parkingchallenge.models.Registration;
import co.com.ceiba.parkingchallenge.models.Vehicle;
import co.com.ceiba.parkingchallenge.repositories.CarRepository;
import co.com.ceiba.parkingchallenge.repositories.InvoiceRepository;
import co.com.ceiba.parkingchallenge.repositories.MotorbikeRepository;
import co.com.ceiba.parkingchallenge.repositories.RegistrationRepository;
import co.com.ceiba.parkingchallenge.repositories.TariffRepository;



@Service
public class RegistrationService {

	@Autowired
	private RegistrationRepository registrationRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private MotorbikeRepository motorbikeRepository;
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private TariffRepository tariffRepository;
	
	@Autowired
	private ControlRegistration controlRegistration;
	
	/**
	 * 
	 * @return
	 */
	public Optional<List<Registration>> listAllRegistrations(){
		return Optional.ofNullable(controlRegistration
				.listAllRegistrations(registrationRepository, carRepository, motorbikeRepository));
	}
	
	public Optional<Invoice> registerVehicularExit(Vehicle vehicle){
		return Optional.ofNullable(controlRegistration
				.registerCheckOutVehicular(vehicle, registrationRepository, carRepository,
						motorbikeRepository, invoiceRepository, tariffRepository));
	}
}
