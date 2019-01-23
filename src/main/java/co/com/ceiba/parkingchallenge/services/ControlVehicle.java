package co.com.ceiba.parkingchallenge.services;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import co.com.ceiba.parkingchallenge.entities.CarEntity;
import co.com.ceiba.parkingchallenge.entities.MotorbikeEntity;
import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.entities.StateType;
import co.com.ceiba.parkingchallenge.exceptions.ViolatedConstraintException;
import co.com.ceiba.parkingchallenge.mappers.RegistrationMapper;
import co.com.ceiba.parkingchallenge.mappers.VehicleMapper;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.FactoryRegistratration;
import co.com.ceiba.parkingchallenge.models.Motorbike;
import co.com.ceiba.parkingchallenge.models.Registration;
import co.com.ceiba.parkingchallenge.models.Rule;
import co.com.ceiba.parkingchallenge.models.Vehicle;
import co.com.ceiba.parkingchallenge.repositories.CarRepository;
import co.com.ceiba.parkingchallenge.repositories.ConstraintRepository;
import co.com.ceiba.parkingchallenge.repositories.MotorbikeRepository;
import co.com.ceiba.parkingchallenge.repositories.RegistrationRepository;
import co.com.ceiba.parkingchallenge.util.ReaderContraintXml;

@Component
public class ControlVehicle implements IControlVehicle{
	
	@Override
	public Registration registerVehicle(Vehicle vehicle, ConstraintRepository constraintRepository,
			RegistrationRepository registrationRepository) {
		return RegistrationMapper
				.mapperToModel(registrationRepository
						.save(validateRegister(vehicle, constraintRepository, registrationRepository)));
	}

	@Override
	public RegistrationEntity validateRegister(Vehicle vehicle, ConstraintRepository constraintRepository, RegistrationRepository registrationRepository) {
		applyRuleVehicleActive(vehicle, registrationRepository); // Verifica que el vehiculo no se encuentre activo en el parqueadero
		
		Integer countMax = constraintRepository.numberMaxNumberVehicle(vehicle.getClass().getSimpleName()); 
		
		if (vehicle instanceof Car) {
			
			Integer carsInParking = registrationRepository.countActiveCar(); //Obtiene la cantidad de carros activos en el parqueadero
			
			if ((countMax == null || carsInParking == null) || countMax > carsInParking) {// Verifica que no se haya alcanzado en numero maximo de carros
				ReaderContraintXml reader = new ReaderContraintXml();
				applyRulesOfParking(vehicle, reader.readerRules(new File("rules.xml") ,Rule.Type.PLATE)); //
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
	
	@Override
	public List<Vehicle> listAllVehicles(CarRepository carRepository, MotorbikeRepository motorbikeRepository) {
		List<Vehicle> listActiveVehicle = carRepository.findAllActiveVehicles()
				  .stream()
				  .map(VehicleMapper::mapperOfEntity)
				  .collect(Collectors.toList());
		listActiveVehicle.addAll(motorbikeRepository.findAllActiveVehicles()
				.stream()
				.map(VehicleMapper::mapperOfEntity)
				.collect(Collectors.toList()));
		
		return listActiveVehicle;
	}
	
	@Override
	public Vehicle saveVehicle(Vehicle vehicle, CarRepository carRepository, MotorbikeRepository motorbikeRepository) {
		if(vehicle instanceof Car) {
			return VehicleMapper.mapperToModel(carRepository.save(VehicleMapper.mapperToEntity((Car) vehicle)));
		}else {
			return VehicleMapper.mapperToModel(motorbikeRepository.save(VehicleMapper.mapperToEntity((Motorbike) vehicle)));
		}
	}


	@Override
	public Vehicle findVehicleByPlate(String plate, CarRepository carRepository, MotorbikeRepository motorbikeRepository) {
		CarEntity car = carRepository.findByPlate(plate);
		if(car != null) {
			return VehicleMapper.mapperToModel(car);
		}
		
		MotorbikeEntity motorbike = motorbikeRepository.findByPlate(plate);
		if(motorbike != null) {
			return VehicleMapper.mapperToModel(motorbike);
		}
		return null;
	}
	
	@Override
	public RegistrationEntity verifyActiveRegistration(String plate, RegistrationRepository registrationRepository) {
		return registrationRepository.findRegistrationActive(plate);
	}
	
	
}
