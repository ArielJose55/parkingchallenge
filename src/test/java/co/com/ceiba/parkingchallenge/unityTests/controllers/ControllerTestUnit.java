package co.com.ceiba.parkingchallenge.unityTests.controllers;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.parkingchallenge.common.UtilUnit;
import co.com.ceiba.parkingchallenge.controllers.RegistrationController;
import co.com.ceiba.parkingchallenge.controllers.VehicleController;
import co.com.ceiba.parkingchallenge.exceptions.NotFountModelException;
import co.com.ceiba.parkingchallenge.exceptions.NotRegisterVehicleException;
import co.com.ceiba.parkingchallenge.exceptions.NotSaveModelException;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.Motorbike;
import co.com.ceiba.parkingchallenge.services.RegistrationService;
import co.com.ceiba.parkingchallenge.services.VehicleService;

@RunWith(SpringRunner.class)
public class ControllerTestUnit extends UtilUnit{

	@Rule
	public final JUnitSoftAssertions tryExceptionVehicle = new JUnitSoftAssertions();
	
	@Rule
	public final JUnitSoftAssertions tryExceptionRegistration = new JUnitSoftAssertions();
	
	@Mock
	private VehicleService vehicleService;
	
	@Mock
	private RegistrationService registerableService;
	
	@InjectMocks
	private VehicleController vehicleController;
	
	@InjectMocks
	private RegistrationController registerableController;
	
	@Test
	public void ThrowExceptionsVehicleControllerTest() {
		Car car = (Car) createVehicleCar("M1");
		Motorbike moto = (Motorbike) createVehicleMotorbike("M2");
		when( vehicleService.registerVehicle(car) ).thenReturn( Optional.empty() );
		when( vehicleService.registerVehicle(moto) ).thenReturn( Optional.empty() ); 
		when( vehicleService.save(car) ).thenReturn( Optional.empty() );
		when( vehicleService.save(moto) ).thenReturn( Optional.empty() ); 
		when( vehicleService.listAllVehicles() ).thenReturn( Optional.empty() );
		when( vehicleService.getVehicle(car.getPlate()) ).thenReturn( Optional.empty() );
		
		try {
			vehicleController.register(car);
		}catch (NotRegisterVehicleException e) {
			tryExceptionVehicle.assertThat(e)
				.hasMessage("Oops! No fue posible registrar el ingreso del vehiculo");
		}
		
		try {
			vehicleController.register(moto);
		}catch (NotRegisterVehicleException e) {
			tryExceptionVehicle.assertThat(e)
				.hasMessage("Oops! No fue posible registrar el ingreso del vehiculo");
		}
		
		try {
			vehicleController.save(car);
		}catch (NotSaveModelException e) {
			tryExceptionVehicle.assertThat(e)
				.hasMessage("Oops! No fue posible almacenar los datos del vehiculo");
		}
		
		try {
			vehicleController.save(moto);
		}catch (NotSaveModelException e) {
			tryExceptionVehicle.assertThat(e)
				.hasMessage("Oops! No fue posible almacenar los datos del vehiculo");
		}
		
		try {
			vehicleController.listAll();
		}catch (NotFountModelException e) {
			tryExceptionVehicle.assertThat(e)
				.hasMessage("No se encontraron vehiculos activos en el parqueadero");
		}
		
		try {
			vehicleController.findByPlate(car.getPlate());
		}catch (NotFountModelException e) {
			tryExceptionVehicle.assertThat(e)
				.hasMessage("No se encontro ningun vehiculo registrado con esta placa");
		}
	}
	
	
	@Test
	public void throwExceptionsRegistrationControllerTest() {
		Car car = (Car) createVehicleCar("M1");
		Motorbike moto = (Motorbike) createVehicleMotorbike("M2");
		when( registerableService.listAllRegistrations() ).thenReturn( Optional.empty() ); 
		when( registerableService.registerVehicularExit(car) ).thenReturn( Optional.empty() );
		when( registerableService.registerVehicularExit(moto) ).thenReturn( Optional.empty() );
		
		try {
			registerableController.listAllActives();
		}catch (Exception e) {
			tryExceptionRegistration.assertThat(e)
				.hasMessage("No hay vehiculos activos en el parqueadero");
		}
		
		try {
			registerableController.unRegister(car);
		}catch (Exception e) {
			tryExceptionRegistration.assertThat(e)
				.hasMessage("Ooups! Fue imposible registrar la salida del este vehiculo");
		}
		try {
			registerableController.unRegister(moto);
		}catch (Exception e) {
			tryExceptionRegistration.assertThat(e)
				.hasMessage("Ooups! Fue imposible registrar la salida del este vehiculo");
		}
	}
	
}
