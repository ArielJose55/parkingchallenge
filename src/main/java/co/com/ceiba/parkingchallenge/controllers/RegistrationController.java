package co.com.ceiba.parkingchallenge.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.Invoice;
import co.com.ceiba.parkingchallenge.models.Motorbike;
import co.com.ceiba.parkingchallenge.models.Registration;
import co.com.ceiba.parkingchallenge.services.RegistrationService;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	private RegistrationService registerableService;
	
	@GetMapping("/actives") 																//---->	OK	
	public List<Registration> listAllReservationsActives() {
		return registerableService.listAllRegistrations()
				.orElseThrow(() -> new RuntimeException("No hay vehiculos activos"));
	}
	
	@PostMapping("/car/check-out") 															//---->	OK
	public Invoice registerVehicularExit(@RequestBody Car car) {
		return registerableService.registerVehicularExit(car)
				.orElseThrow(() -> new RuntimeException("No se ha podigo guardar los "));
	}
	
	@PostMapping("/bike/check-out") 														//---->	OK
	public Invoice registerVehicularExit(@RequestBody Motorbike motorbike) {
		return registerableService.registerVehicularExit(motorbike)
				.orElseThrow(() -> new RuntimeException("No se ha podigo guardar los "));
	}
}
