package co.com.ceiba.parkingchallenge.integrationTests.controllers;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;


import co.com.ceiba.parkingchallenge.common.UtilIntegration;
import co.com.ceiba.parkingchallenge.controllers.VehicleController;
import co.com.ceiba.parkingchallenge.entities.CarEntity;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.repositories.CarRepository;


@RunWith(SpringRunner.class)
public class HandleExceptionControllerTest extends UtilIntegration{
	
	@Mock
	private CarRepository carRepository;
	
	@InjectMocks
	private VehicleController vehicleController;
	
	@Test
	public void hadndleExceptionNotFount() {
		
		Car car = (Car) createVehicleCar("MM3451");
		CarEntity carEntity = createCarEntity("MLÑFF1");
		
		when(carRepository.save(carEntity)).thenReturn( null );
		
		try {
			vehicleController.save(car);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
