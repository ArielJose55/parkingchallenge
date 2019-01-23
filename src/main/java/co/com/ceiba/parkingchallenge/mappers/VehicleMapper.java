package co.com.ceiba.parkingchallenge.mappers;

import co.com.ceiba.parkingchallenge.entities.CarEntity;
import co.com.ceiba.parkingchallenge.entities.MotorbikeEntity;
import co.com.ceiba.parkingchallenge.entities.VehicleEntity;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.Motorbike;
import co.com.ceiba.parkingchallenge.models.Vehicle;

public final class VehicleMapper {
	
	private VehicleMapper() {}
	
	public static Vehicle mapperToModel(CarEntity entity) {
		return entity != null? 
				new Car(entity.getVehicleEntity().getPlate(), entity.getVehicleEntity().getModel(), entity.getVehicleEntity().getBrand(), entity.getMotorType()) 
				: null;
	}
	
	public static Vehicle mapperToModel(MotorbikeEntity entity) {
		return entity != null?
				new Motorbike(entity.getVehicleEntity().getPlate(), entity.getVehicleEntity().getModel(), entity.getVehicleEntity().getBrand(), entity.getDisplacement())
				: null;
	}
	
	public static CarEntity mapperToEntity(Car model) {
		return model != null? 
				new CarEntity(null ,model.getTypeMotor(), new VehicleEntity(model.getPlate(), model.getModel(),model.getBrand()))
				: null;
	}
	
	public static MotorbikeEntity mapperToEntity(Motorbike model) {
		return model != null?
			new MotorbikeEntity(null, model.getDisplacement(), new VehicleEntity(model.getPlate(), model.getModel(),model.getBrand()))
			: null;
	}
	
	public static VehicleEntity mapperToEntity(Vehicle model) {
		return model != null?
				new VehicleEntity(model.getPlate(), model.getModel(), model.getBrand())
				: null;
	}
	
	public static Vehicle mapperOfEntity(CarEntity entity) {
		return entity != null? 
			new Car(entity.getVehicleEntity().getPlate(), entity.getVehicleEntity().getModel(), entity.getVehicleEntity().getBrand(),
				entity.getMotorType())
			: null;
	}
	
	public static Vehicle mapperOfEntity(MotorbikeEntity entity) {
		return entity != null? 
			new Motorbike(entity.getVehicleEntity().getPlate(), entity.getVehicleEntity().getModel(), entity.getVehicleEntity().getBrand(),
				entity.getDisplacement())
			: null;
	}
}
