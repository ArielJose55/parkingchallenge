package co.com.ceiba.parkingchallenge.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public Optional<Registration> registerVehicle(Vehicle vehicle) {
		return Optional.of(RegistrationMapper
				.mapperToModel(registrationRepository
						.save(controlVehicle
								.validateRegister(vehicle, constraintRepository, registrationRepository))));
	}
	
	public Optional<List<Vehicle>> listAllVehicles(){
		List<Vehicle> listActiveVehicle = carRepository.findAllActiveVehicles()
				  .stream()
				  .map(VehicleMapper::mapperOfEntity)
				  .collect(Collectors.toList());
		listActiveVehicle.addAll(motorbikeRepository.findAllActiveVehicles()
				.stream()
				.map(VehicleMapper::mapperOfEntity)
				.collect(Collectors.toList()));
		
		return Optional.of(listActiveVehicle);
	}
	
	public Optional<Vehicle> save(Vehicle vehicle){
		if(vehicle instanceof Car) {
			return VehicleMapper.mapperToModel(carRepository.save(VehicleMapper.mapperToEntity((Car) vehicle)));
		}else {
			return VehicleMapper.mapperToModel(motorbikeRepository.save(VehicleMapper.mapperToEntity((Motorbike) vehicle)));
		}
	}
	
	public Optional<Vehicle> getVehiclee(String plate, Class <? extends Vehicle> vehicle){
		if(vehicle.isAssignableFrom(Car.class)) {
			return VehicleMapper.mapperToModel(carRepository.findByPlate(plate));
		}else {
			return VehicleMapper.mapperToModel(motorbikeRepository.findByPlate(plate));
		}
	}
}
