package co.com.ceiba.parkingchallenge.integrationTests.controllers;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import co.com.ceiba.parkingchallenge.ParkingchallengeApplication;
import co.com.ceiba.parkingchallenge.common.UtilIntegration;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.Motorbike;
import co.com.ceiba.parkingchallenge.services.VehicleService;
import config.H2TestProfileJPAConfig;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {
		  	ParkingchallengeApplication.class, 
		  	H2TestProfileJPAConfig.class},
			webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class VehicleControllerTest extends UtilIntegration {
	
	@LocalServerPort
	private Integer port;
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private VehicleService vehicleService;
		
	@Test
	public void saveMotorbikeTest() throws Exception {
		Motorbike motorbike = createVehicle("M41-41", 1000);
		
		mvc.perform(post(createUrl(port, "/motorbike"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(motorbike)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("plate", is("M41-41")));
		
		mvc.perform(post(createUrl(port, "/car"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(motorbike)))
				.andExpect(status().is(500))
				.andExpect(jsonPath("message", is(containsString("could not execute statement;"))));		
	}
	
	@Test
	public void saveCarTest() throws Exception {
		Car car = createVehicle("M41-42", "Combustion");
		
		mvc.perform(post(createUrl(port, "/car"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(car)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("plate", is("M41-42")));
		
		mvc.perform(post(createUrl(port, "/car"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(car)))
				.andExpect(status().is(500))
				.andExpect(jsonPath("message", is(containsString("could not execute statement;"))));
	}
	
	@Test
	public void findVehicleByPlateTest() throws Exception {
		
		Car car = createVehicle("M41-43", "Combustion");
		
		assertThat(vehicleService.save(car).isPresent()).isEqualTo(true);
		
		mvc.perform(get(createUrl(port, "/{plate}"), "M41-43")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());
	}
	
	@Test
	public void registerCarTest() throws Exception {
		Car car = createVehicle("M41-44", "Combustion");
		
		assertThat(vehicleService.save(car).isPresent()).isEqualTo(true);
		
		mvc.perform(post(createUrl(port, "/car/register"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(car)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("state", is("ACTIVE")));		
	}
	
	
	
	@Test
	public void registerMotorbikeTest() throws Exception {
		Motorbike motorbike = createVehicle("M41-45", 1000);
		
		assertThat(vehicleService.save(motorbike).isPresent()).isEqualTo(true);
		
		mvc.perform(post(createUrl(port, "/motorbike/register"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(motorbike)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("state", is("ACTIVE")));
				
	}
	
	@Test
	public void findAllVehicleTest() throws Exception {
		
		 mvc.perform(get(createUrl(port, "/"))
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());

	}
	
	
	
	
}
