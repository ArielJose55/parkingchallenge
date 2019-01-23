package co.com.ceiba.parkingchallenge.integrationTests.controllers;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import co.com.ceiba.parkingchallenge.ParkingchallengeApplication;
import co.com.ceiba.parkingchallenge.common.UtilIntegration;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.Motorbike;
import co.com.ceiba.parkingchallenge.repositories.TariffRepository;
import co.com.ceiba.parkingchallenge.services.RegistrationService;
import co.com.ceiba.parkingchallenge.services.VehicleService;
import config.H2TestProfileJPAConfig;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = { ParkingchallengeApplication.class,
		H2TestProfileJPAConfig.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RegistrationControllerTest extends UtilIntegration {

	@LocalServerPort
	private Integer port;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private RegistrationService registrationServices;

	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private TariffRepository tariffRepository;

	@Test
	public void listAllReservationsActivesTest() throws Exception {

		mvc.perform(get(createUrl(port, "/registration/actives")).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void registerVehicularCarTest() throws Exception {

		Car car = createVehicle("M41-46", "Combustion");

		assertThat(tariffRepository.saveAll(Lists.newArrayList(createTariff(1, 1000.0, "Car"), createTariff(24, 8000.0, "Car"))))
			.isNotNull();
		
		assertThat(vehicleService.save(car).isPresent()).isEqualTo(true);

		assertThat(vehicleService.registerVehicle(car).isPresent()).isEqualTo(true);

		mvc.perform(post(createUrl(port, "/registration/car/check-out")).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(car))).andExpect(status().isOk());
	}

	@Test
	public void registerVehicularExitMotorbikeTestFail() throws Exception {
		
		Motorbike motorbike = createVehicle("M41-47", 1000);

		assertThat(vehicleService.save(motorbike).isPresent()).isEqualTo(true);

		assertThat(vehicleService.registerVehicle(motorbike).isPresent()).isEqualTo(true);
		
		mvc.perform(post(createUrl(port, "/registration/bike/check-out")).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(motorbike))).andExpect(status().is(400))
				.andExpect(jsonPath("message", is("No existe tarrifa aplicable para este vehiculo para 0 horas u horas")));
	}
	
	@Test
	public void registerVehicularExitMotorbikeTest() throws Exception {
		
		Motorbike motorbike = createVehicle("M41-48", 1000);

		assertThat(tariffRepository.saveAll(Lists.newArrayList(createTariff(1, 500.0, "Motorbike"), createTariff(24, 4000.0, "Motorbike"))))
			.isNotNull();
		
		assertThat(vehicleService.save(motorbike).isPresent()).isEqualTo(true);

		assertThat(vehicleService.registerVehicle(motorbike).isPresent()).isEqualTo(true);
		
		mvc.perform(post(createUrl(port, "/registration/bike/check-out")).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(motorbike))).andExpect(status().isOk());
				
	}
	
}
