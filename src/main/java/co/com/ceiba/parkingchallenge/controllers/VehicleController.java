package co.com.ceiba.parkingchallenge.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.Motorbike;
import co.com.ceiba.parkingchallenge.models.Registration;
import co.com.ceiba.parkingchallenge.models.Vehicle;
import co.com.ceiba.parkingchallenge.services.VehicleService;
import co.com.ceiba.parkingchallenge.services.exceptions.NotFountModelException;
import co.com.ceiba.parkingchallenge.services.exceptions.NotSaveModelException;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
	
	@Autowired
	private VehicleService vehicleService;
	
	@PostMapping("/car/register")
	public Registration register(@RequestBody Car vehicle) {
		return vehicleService.registerVehicle(vehicle)
				.orElseThrow(() -> new NotSaveModelException(Vehicle.class));
	}
	
	@PostMapping("/car/save")
	public Vehicle saveCar(@RequestBody Car car) {
		return vehicleService.save(car)
				.orElseThrow(() -> new NotSaveModelException(Car.class));
	}
	
	@PostMapping("/motorbike/save")
	public Vehicle saveMotorbike(@RequestBody Motorbike motorbike) {
		return vehicleService.save(motorbike)
				.orElseThrow(() -> new NotSaveModelException(Motorbike.class));
	}
	
	@GetMapping("/car")
	public List<Vehicle> listAllCars(){
		return vehicleService.listAllVehicles()
				.orElseThrow(() -> new RuntimeException("No hay vehiculos activos"));
	}
	
	@GetMapping("/car/{plate}")
	public Vehicle getCar(@PathVariable String plate) {
		return vehicleService.getVehiclee(plate, Car.class)
				.orElseThrow(() -> new NotFountModelException(Car.class));
	}
	
	@GetMapping("/motorbike/{plate}")
	public Vehicle getMotorbike(@PathVariable String plate) {
		return vehicleService.getVehiclee(plate, Motorbike.class)
				.orElseThrow(() -> new NotFountModelException(Motorbike.class));
	}
	
}
