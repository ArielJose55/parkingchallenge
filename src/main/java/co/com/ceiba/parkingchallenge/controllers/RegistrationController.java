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
@RequestMapping
public class RegistrationController {

	private static final String NO_HAY_VEHICULOS_ACTIVOS_EN_EL_PARQUEADERO = "No hay vehiculos activos en el parqueadero";
	private static final String IMPOSIBLE_REGISTRAR_LA_SALIDA = "Ooups! Fue imposible registrar la salida del este vehiculo";
	
	@Autowired
	private RegistrationService registerableService;
	
	@GetMapping("/active") 																//---->	OK	
	public List<Registration> listAllActives() {
		return registerableService.listAllRegistrations() 
				.orElseThrow(
						() -> new NotFountModelException(NO_HAY_VEHICULOS_ACTIVOS_EN_EL_PARQUEADERO));
	}
	
	@PostMapping("/car/check-out") 															//---->	OK
	public Invoice unRegister(@RequestBody Car car) {
		return registerableService.registerVehicularExit(car)
				.orElseThrow(
						() -> new NotRegisterVehicleException(IMPOSIBLE_REGISTRAR_LA_SALIDA));
	}
	
	@PostMapping("/motorbike/check-out") 														//---->	OK
	public Invoice unRegister(@RequestBody Motorbike motorbike) {
		return registerableService.registerVehicularExit(motorbike)
				.orElseThrow(
						() -> new NotRegisterVehicleException(IMPOSIBLE_REGISTRAR_LA_SALIDA));
	}
}
