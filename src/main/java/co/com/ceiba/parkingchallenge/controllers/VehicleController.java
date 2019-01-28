package co.com.ceiba.parkingchallenge.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.parkingchallenge.exceptions.NotFountModelException;
import co.com.ceiba.parkingchallenge.exceptions.NotRegisterVehicleException;
import co.com.ceiba.parkingchallenge.exceptions.NotSaveModelException;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.Motorbike;
import co.com.ceiba.parkingchallenge.models.Registration;
import co.com.ceiba.parkingchallenge.models.Vehicle;
import co.com.ceiba.parkingchallenge.services.VehicleService;

@CrossOrigin(origins = "http://localhost:4200")	
@RestController
@RequestMapping
public class VehicleController {
	
	
	private static final String NO_SE_ENCONTRO_VEHICULO_REGISTRADO = "No se encontro ningun vehiculo registrado con esta placa";
	private static final String NO_SE_ENCONTRARON_VEHICULOS_ACTIVOS_EN_EL_PARQUEADERO = "No se encontraron vehiculos activos en el parqueadero";
	private static final String NO_FUE_POSIBLE_ALMACENAR_EL_VEHICULO = "Oops! No fue posible almacenar los datos del vehiculo";
	private static final String NO_FUE_POSIBLE_EL_INGRESO_DEL_VEHICULO = "Oops! No fue posible registrar el ingreso del vehiculo";
	
	
	@Autowired
	private VehicleService vehicleService;
	
	@PostMapping("/car/register") 												
	public Registration register(@RequestBody Car vehicle) {
		return vehicleService.registerVehicle(vehicle)
				.orElseThrow(
						() -> new NotRegisterVehicleException(NO_FUE_POSIBLE_EL_INGRESO_DEL_VEHICULO));
	}
	
	@PostMapping("/motorbike/register") 										
	public Registration register(@RequestBody Motorbike vehicle) {
		return vehicleService.registerVehicle(vehicle)
				.orElseThrow(
						() -> new NotRegisterVehicleException(NO_FUE_POSIBLE_EL_INGRESO_DEL_VEHICULO));
	}
	
	@PostMapping("/car")							
	public Vehicle save(@RequestBody Car car) {
		return vehicleService.save(car)
				.orElseThrow(
						() -> new NotSaveModelException(NO_FUE_POSIBLE_ALMACENAR_EL_VEHICULO));
	}
	
	@PostMapping("/motorbike") 											
	public Vehicle save(@RequestBody Motorbike motorbike) {
		return vehicleService.save(motorbike)
				.orElseThrow(
						() -> new NotSaveModelException(NO_FUE_POSIBLE_ALMACENAR_EL_VEHICULO));
	}
	
	@GetMapping() 														
	public List<Vehicle> listAll(){
		return vehicleService.listAllVehicles()
				.orElseThrow(
						() -> new NotFountModelException(NO_SE_ENCONTRARON_VEHICULOS_ACTIVOS_EN_EL_PARQUEADERO));
	}
	
	@GetMapping("/{plate}")	
	public Vehicle findByPlate(@PathVariable String plate) {
		return vehicleService.getVehicle(plate)
				.orElseThrow(
						() -> new NotFountModelException( NO_SE_ENCONTRO_VEHICULO_REGISTRADO ));
	}
}
