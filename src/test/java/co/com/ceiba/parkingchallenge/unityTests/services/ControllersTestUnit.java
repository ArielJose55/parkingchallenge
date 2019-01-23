package co.com.ceiba.parkingchallenge.unityTests.services;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
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
public class ControllersTestUnit extends UtilUnit{

	@Mock
	private VehicleService vehicleService;
	
	@Mock
	private RegistrationService registerableService;

	private Car car;
	
	private Motorbike moto;
	
	private String plate;
	
	@InjectMocks
	private VehicleController vehicleController;
	
	@InjectMocks
	private RegistrationController registrationController;
	
	@Before
	public void setUp() {
		this.car = (Car) createVehicleCar("M1");
		this.moto = (Motorbike) createVehicleMotorbike("M2");
		this.plate = "M3";
	}
	
	@Test
	public void findVehicleByPlateResponseOfServiceIsNull() {
		
		when(vehicleService.getVehicle(plate)).thenReturn(Optional.empty());
		
		try {
			vehicleController.findVehicleByPlate(plate);
		}catch (NotFountModelException e) {
			assertThat(e)
				.hasMessage("No se encontro ningun vehiculo registrado con esta placa: " + plate);
		}
	}
	
	@Test
	public void listAllVehiclesResponseOfServiceIsNull() {
		
		when(vehicleService.listAllVehicles()).thenReturn(Optional.empty());
		
		try {
			vehicleController.listAllVehicles();
		}catch (NotFountModelException e) {
			assertThat(e)
				.hasMessage("No se encontraron vehiculos activos en el parqueadero");
		}
	}
	
	@Test
	public void saveMotorbikeResponseOfServiceIsNull() {
		
		when(vehicleService.save(moto)).thenReturn(Optional.empty());
		
		try {
			vehicleController.saveMotorbike(moto);
		}catch (NotSaveModelException e) {
			assertThat(e)
				.hasMessage("Oops! No fue posible almacenar los datos de la motocicleta");
		}
	}
	
	@Test
	public void saveCarResponseOfServiceIsNull() {
		
		when(vehicleService.save(car)).thenReturn(Optional.empty());
		
		try {
			vehicleController.saveCar(car);
		}catch (NotSaveModelException e) {
			assertThat(e)
				.hasMessage("Oops! No fue posible almacenar los datos del carro");
		}
	}
	
	@Test
	public void registerCarResponseOfServiceIsNull() {
		
		when(vehicleService.registerVehicle(car)).thenReturn(Optional.empty());
		
		try {
			vehicleController.register(car);
		}catch (NotRegisterVehicleException e) {
			assertThat(e)
				.hasMessage("Oops! No fue posible registrar el ingreso del carro");
		}
	}
	
	@Test
	public void registerMotorbikeResponseOfServiceIsNull() {
		
		when(vehicleService.registerVehicle(moto)).thenReturn(Optional.empty());
		
		try {
			vehicleController.register(moto);
		}catch (NotRegisterVehicleException e) {
			assertThat(e)
				.hasMessage("Oops! No fue posible registrar el ingreso de la motocicleta");
		}
	}
	
	@Test
	public void  listAllReservationsActivesResponseOfServiceIsNull() {
		
		when(registerableService.listAllRegistrations()).thenReturn(Optional.empty());
		
		try {
			registrationController.listAllReservationsActives();
		}catch (NotFountModelException e) {
			assertThat(e)
				.hasMessage("No hay vehiculos activos en el parqueadero");
		}
	}
	
	@Test
	public void  registerVehicularExitCarResponseOfServiceIsNull() {
		
		when(registerableService.registerVehicularExit(car)).thenReturn(Optional.empty());
		
		try {
			registrationController.registerVehicularExit(car);
		}catch (NotRegisterVehicleException e) {
			assertThat(e)
				.hasMessage("Ooups! Fue imposible registrar la salida del este carro");
		}
	}
	
	@Test
	public void  registerVehicularExitMotorbikeResponseOfServiceIsNull() {
		
		when(registerableService.registerVehicularExit(moto)).thenReturn(Optional.empty());
		
		try {
			registrationController.registerVehicularExit(moto);
		}catch (NotRegisterVehicleException e) {
			assertThat(e)
				.hasMessage("Ooups! Fue imposible registrar la salida del esta motocicleta");
		}
	}
}
	
	
	
