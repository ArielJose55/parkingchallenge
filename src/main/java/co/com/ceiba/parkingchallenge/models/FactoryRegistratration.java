package co.com.ceiba.parkingchallenge.models;

import java.time.LocalDateTime;

import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.entities.StateType;
import co.com.ceiba.parkingchallenge.mappers.VehicleMapper;

public class FactoryRegistratration {
	
	private FactoryRegistratration() {}

	public static RegistrationEntity create(StateType state, Vehicle vehicle) {
		RegistrationEntity registration = new RegistrationEntity();
		registration.setRegistrationDate(LocalDateTime.now());
		registration.setState(state);
		registration.setVehicleEntity(VehicleMapper.mapperToEntity(vehicle));
		return registration;
	}
}
