package co.com.ceiba.parkingchallenge.mappers;

import java.util.Optional;

import co.com.ceiba.parkingchallenge.entities.CarEntity;
import co.com.ceiba.parkingchallenge.entities.MotorbikeEntity;
import co.com.ceiba.parkingchallenge.entities.VehicleEntity;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.Motorbike;
import co.com.ceiba.parkingchallenge.models.Vehicle;

public class VehicleMapper {
	
	private VehicleMapper() {}
	
	public static Optional<Vehicle> mapperToModel(CarEntity entity) {
		return Optional.of(new Car(entity.getVehicleEntity().getPlate(), entity.getVehicleEntity().getModel(), entity.getVehicleEntity().getBrand(),
				entity.getMotorType()));
	}
	
	public static Optional<Vehicle> mapperToModel(MotorbikeEntity entity) {
		return Optional.of(new Motorbike(entity.getVehicleEntity().getPlate(), entity.getVehicleEntity().getModel(), entity.getVehicleEntity().getBrand(),
				entity.getDisplacement()));
	}
	
	public static CarEntity mapperToEntity(Car model) {
		CarEntity entity = new CarEntity();
		entity.setMotorType(model.getTypeMotor());
		entity.setVehicleEntity(new VehicleEntity(model.getPlate(), model.getModel(),model.getBrand()));
		return entity;
	}
	
	public static MotorbikeEntity mapperToEntity(Motorbike model) {
		MotorbikeEntity entity = new MotorbikeEntity();
		entity.setDisplacement(model.getDisplacement());
		entity.setVehicleEntity(new VehicleEntity(model.getPlate(), model.getModel(),model.getBrand()));
		return entity;
	}
	
	public static VehicleEntity mapperToEntity(Vehicle model) {
		VehicleEntity entity = new VehicleEntity();
		entity.setPlate(model.getPlate());
		entity.setModel(model.getModel());
		entity.setBrand(model.getBrand());
		return entity;
	}
	
	public static Vehicle mapperOfEntity(CarEntity entity) {
		return new Car(entity.getVehicleEntity().getPlate(), entity.getVehicleEntity().getModel(), entity.getVehicleEntity().getBrand(),
				entity.getMotorType());
	}
	
	public static Vehicle mapperOfEntity(MotorbikeEntity entity) {
		return new Motorbike(entity.getVehicleEntity().getPlate(), entity.getVehicleEntity().getModel(), entity.getVehicleEntity().getBrand(),
				entity.getDisplacement());
	}
}
