package co.com.ceiba.parkingchallenge.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.parkingchallenge.models.Registration;
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
@Service
public class VehicleService {
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private MotorbikeRepository motorbikeRepository;
	
	@Autowired
	private ConstraintRepository constraintRepository;
	
	@Autowired
	private RegistrationRepository registrationRepository;
	
	@Autowired
	private ControlVehicle controlVehicle;
	
	/**
	 * 
	 * 
	 * @param vehicle
	 * @return
	 */
	public Optional<Registration> registerVehicle(Vehicle vehicle) {
		return Optional.ofNullable(controlVehicle
				.registerVehicle(vehicle, constraintRepository, registrationRepository));
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public Optional<List<Vehicle>> listAllVehicles(){	
		return Optional.ofNullable(controlVehicle
				.listAllVehicles(carRepository, motorbikeRepository));
	}
	
	/**
	 * 
	 * 
	 * @param vehicle
	 * @return
	 */
	public Optional<Vehicle> save(Vehicle vehicle) {
		return Optional.ofNullable(controlVehicle
				.saveVehicle(vehicle, carRepository, motorbikeRepository));
	}
	
	/**
	 * 
	 * 
	 * @param plate
	 * @return
	 */
	public Optional<Vehicle> getVehicle(String plate){
		return Optional.ofNullable(controlVehicle
				.findVehicleByPlate(plate, carRepository, motorbikeRepository));
	}
}
