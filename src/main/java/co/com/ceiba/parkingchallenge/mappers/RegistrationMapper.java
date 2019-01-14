package co.com.ceiba.parkingchallenge.mappers;

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
}
