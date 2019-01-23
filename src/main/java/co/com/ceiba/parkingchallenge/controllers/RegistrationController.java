package co.com.ceiba.parkingchallenge.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.parkingchallenge.exceptions.NotFountModelException;
import co.com.ceiba.parkingchallenge.exceptions.NotRegisterVehicleException;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.Invoice;
import co.com.ceiba.parkingchallenge.models.Motorbike;
import co.com.ceiba.parkingchallenge.models.Registration;
import co.com.ceiba.parkingchallenge.services.RegistrationService;

@CrossOrigin(origins = "http://localhost:4200")	
@RestController
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	private RegistrationService registerableService;
	
	@GetMapping("/actives") 																//---->	OK	
	public List<Registration> listAllReservationsActives() {
		return registerableService.listAllRegistrations()
				.orElseThrow(
						() -> new NotFountModelException("No hay vehiculos activos en el parqueadero"));
	}
	
	@PostMapping("/car/check-out") 															//---->	OK
	public Invoice registerVehicularExit(@RequestBody Car car) {
		return registerableService.registerVehicularExit(car)
				.orElseThrow(
						() -> new NotRegisterVehicleException("Ooups! Fue imposible registrar la salida del este carro"));
	}
	
	@PostMapping("/bike/check-out") 														//---->	OK
	public Invoice registerVehicularExit(@RequestBody Motorbike motorbike) {
		return registerableService.registerVehicularExit(motorbike)
				.orElseThrow(
						() -> new NotRegisterVehicleException("Ooups! Fue imposible registrar la salida del esta motocicleta"));
	}
}
