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
@RequestMapping("/vehicle")
public class VehicleController {
	
	@Autowired
	private VehicleService vehicleService;
	
	@PostMapping("/car/register") 												//---->	OK
	public Registration register(@RequestBody Car vehicle) {
		return vehicleService.registerVehicle(vehicle)
				.orElseThrow(
						() -> new NotRegisterVehicleException("Oops! No fue posible registrar el ingreso del carro"));
	}
	
	@PostMapping("/motorbike/register") 										//---->	//OK
	public Registration register(@RequestBody Motorbike vehicle) {
		return vehicleService.registerVehicle(vehicle)
				.orElseThrow(
						() -> new NotRegisterVehicleException("Oops! No fue posible registrar el ingreso de la motocicleta"));
	}
	
	@PostMapping("/car/save")							//---->	//OK
	public Vehicle saveCar(@RequestBody Car car) {
		return vehicleService.save(car)
				.orElseThrow(
						() -> new NotSaveModelException("Oops! No fue posible almacenar los datos del carro"));
	}
	
	@PostMapping("/motorbike/save") 											//---->	//OK
	public Vehicle saveMotorbike(@RequestBody Motorbike motorbike) {
		return vehicleService.save(motorbike)
				.orElseThrow(
						() -> new NotSaveModelException("Oops! No fue posible almacenar los datos de la motocicleta"));
	}
	
	@GetMapping("/all") 														//---->	//OK
	public List<Vehicle> listAllVehicles(){
		return vehicleService.listAllVehicles()
				.orElseThrow(
						() -> new NotFountModelException("No se encontraron vehiculos activos en el parqueadero"));
	}
	
	@GetMapping("/car/{plate}") 												
	public Vehicle getCar(@PathVariable String plate) {
		return vehicleService.getVehicle(plate, Car.class)
				.orElseThrow(
						() -> new NotFountModelException("No se encontro ningun carro registrado con esta placa: " + plate));
	}
	
	@GetMapping("/motorbike/{plate}")	 										
	public Vehicle getMotorbike(@PathVariable String plate) {
		return vehicleService.getVehicle(plate, Motorbike.class)
				.orElseThrow(
						() -> new NotFountModelException("No se encontro ningun motocicleta registrado con esta placa: " + plate));
	}
	
	@GetMapping("/{plate}")	
	public Vehicle findVehicleByPlate(@PathVariable String plate) {
		return vehicleService.getVehicle(plate)
				.orElseThrow(
						() -> new NotFountModelException("No se encontro ningun vehiculo registrado con esta placa: " + plate));
	}
}
