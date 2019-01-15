package co.com.ceiba.parkingchallenge.mappers;

import java.util.List;
import java.util.Optional;

import co.com.ceiba.parkingchallenge.entities.CarEntity;
import co.com.ceiba.parkingchallenge.entities.MotorbikeEntity;
import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.models.Registration;

public class RegistrationMapper {
	
	private RegistrationMapper() {}

	public static Registration mapperToModel(RegistrationEntity entity) {
		Registration model = new Registration();
		model.setId(entity.getId());
		model.setRegistrationDate(entity.getRegistrationDate());
		model.setState(entity.getState());
		return model;
	}
	
	public static Registration mapperToModel(RegistrationEntity entity, List<CarEntity> carEntities, List<MotorbikeEntity> motorbikeEntities) {
		Registration model = new Registration();
		model.setId(entity.getId());
		model.setRegistrationDate(entity.getRegistrationDate());
		model.setState(entity.getState());
		
		long countCar = carEntities.stream().filter(c -> c.getVehicleEntity().getPlate().equals(entity.getVehicleEntity().getPlate())).count();
		long countBike = motorbikeEntities.stream().filter(c -> c.getVehicleEntity().getPlate().equals(entity.getVehicleEntity().getPlate())).count();
		
		if(countCar != 0) { //Es un carro
			
			// Si existe mas de una coincidencia entonces hay incosistencia de datos, no puede haber mas de un carro con la misma placa activo
			if(countCar > 1) throw new RuntimeException("Hay registrados mas de un carro con esta placa: " + entity.getVehicleEntity().getPlate()); 
			
			Optional<CarEntity> car = carEntities
					.stream()
					.filter(c -> c.getVehicleEntity().getPlate().equals(entity.getVehicleEntity().getPlate()))
					.findFirst();
			
			model.setVehicle( car.isPresent() ? VehicleMapper.mapperOfEntity(car.get()) : null );
			
		}
			
		if (countBike != 0) {
			// Si existe mas de una coincidencia entonces hay incosistencia de datos, no
			// puede haber mas de un carro con la misma placa activo
			if (countBike > 1)
				throw new RuntimeException("Hay registrados mas de una motocicleta con esta placa: "
						+ entity.getVehicleEntity().getPlate());

			Optional<MotorbikeEntity> bike = motorbikeEntities.stream()
					.filter(c -> c.getVehicleEntity().getPlate().equals(entity.getVehicleEntity().getPlate()))
					.findFirst();

			model.setVehicle(bike.isPresent() ? VehicleMapper.mapperOfEntity(bike.get()) : null);
		}
		
		return model;
	}
}
