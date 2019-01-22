package co.com.ceiba.parkingchallenge.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.parkingchallenge.entities.CarEntity;
import co.com.ceiba.parkingchallenge.entities.MotorbikeEntity;
import co.com.ceiba.parkingchallenge.mappers.RegistrationMapper;
import co.com.ceiba.parkingchallenge.mappers.VehicleMapper;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.Motorbike;
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
		return Optional.ofNullable(RegistrationMapper
				.mapperToModel(registrationRepository
						.save(controlVehicle
								.validateRegister(vehicle, constraintRepository, registrationRepository))));
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public Optional<List<Vehicle>> listAllVehicles(){
		List<Vehicle> listActiveVehicle = carRepository.findAllActiveVehicles()
				  .stream()
				  .map(VehicleMapper::mapperOfEntity)
				  .collect(Collectors.toList());
		listActiveVehicle.addAll(motorbikeRepository.findAllActiveVehicles()
				.stream()
				.map(VehicleMapper::mapperOfEntity)
				.collect(Collectors.toList()));
		
		return Optional.ofNullable(listActiveVehicle);
	}
	
	/**
	 * 
	 * 
	 * @param vehicle
	 * @return
	 */
	public Optional<Vehicle> save(Vehicle vehicle){
		if(vehicle instanceof Car) {
			return Optional.ofNullable(VehicleMapper.mapperToModel(carRepository.save(VehicleMapper.mapperToEntity((Car) vehicle))));
		}else {
			return Optional.ofNullable(VehicleMapper.mapperToModel(motorbikeRepository.save(VehicleMapper.mapperToEntity((Motorbike) vehicle))));
		}
	}
	
	/**
	 * 
	 * 
	 * @param plate
	 * @param vehicle
	 * @return
	 */
	public Optional<Vehicle> getVehicle(String plate, Class <? extends Vehicle> vehicle) {
		if(vehicle.isAssignableFrom(Car.class)) {
			return Optional.ofNullable(VehicleMapper.mapperToModel(carRepository.findByPlate(plate)));
		}else {
			return Optional.ofNullable(VehicleMapper.mapperToModel(motorbikeRepository.findByPlate(plate)));
		}
	}
	
	/**
	 * 
	 * 
	 * @param plate
	 * @return
	 */
	public Optional<Vehicle> getVehicle(String plate){
		CarEntity car = carRepository.findByPlate(plate);
		if(car != null) {
			return Optional.of(VehicleMapper.mapperToModel(car));
		}
		
		MotorbikeEntity motorbike = motorbikeRepository.findByPlate(plate);
		if(motorbike != null) {
			return Optional.of(VehicleMapper.mapperToModel(motorbike));
		}
		return Optional.empty();
	}
}
