package co.com.ceiba.parkingchallenge.integrationTests.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.parkingchallenge.ParkingchallengeApplication;
import co.com.ceiba.parkingchallenge.common.UtilIntegration;
import co.com.ceiba.parkingchallenge.controllers.HandleExceptionController;
import co.com.ceiba.parkingchallenge.controllers.VehicleController;
import co.com.ceiba.parkingchallenge.exceptions.NotFountModelException;
import co.com.ceiba.parkingchallenge.exceptions.NotSaveModelException;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.services.VehicleService;
import config.H2TestProfileJPAConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		  	ParkingchallengeApplication.class, 
		  	H2TestProfileJPAConfig.class},
			webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class HandleControllerTest extends UtilIntegration{

	private MockMvc mockMvc;
	
	@LocalServerPort
	private Integer port;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Mock
	private VehicleService vehicleService;
	
	@InjectMocks
    private VehicleController vehicleController;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).setControllerAdvice(new HandleExceptionController())
	            .build();
	}
	
	@Test
	public void hadndleExceptionNotFount() throws Exception{
		when(vehicleService.listAllVehicles()).thenThrow(new NotFountModelException("Unexpected Exception"));
		
		mockMvc.perform(get(createUrl(port, "/"))
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(404)).andReturn();
	}
	
	@Test
	public void handleExceptionNotSave() throws Exception {
		
		Car car = (Car) createVehicleCar("M");
		
		when( vehicleService.save(car) ).thenThrow( new NotSaveModelException("Unexpected Exception") );
		
		mockMvc.perform(post(createUrl(port, "/car"))
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsBytes(car)))
			.andExpect(status().is(400)).andReturn();
	}
}
