package co.com.ceiba.parkingchallenge.common;

import co.com.ceiba.parkingchallenge.entities.MotorbikeEntity;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.Motorbike;

public class UtilIntegration extends UtilUnit {
	
	protected Motorbike createVehicle(String plate, int displacement) {
		Motorbike motorbike = new Motorbike(plate);
		MotorbikeEntity entity = createMotorbikeEntity(plate);
		motorbike.setModel(entity.getVehicleEntity().getModel());
		motorbike.setBrand(entity.getVehicleEntity().getBrand());
		motorbike.setDisplacement(displacement);
		return motorbike;
	}
	
	protected Car createVehicle(String plate, String typeMotor) {
		Car car = new Car(plate);
		MotorbikeEntity entity = createMotorbikeEntity(plate);
		car.setModel(entity.getVehicleEntity().getModel());
		car.setBrand(entity.getVehicleEntity().getBrand());
		car.setTypeMotor(typeMotor);
		return car;
	}
	
	
	protected String createUrl(int port, String path) {
		return "http://localhost:" + port + path;
	}

}
