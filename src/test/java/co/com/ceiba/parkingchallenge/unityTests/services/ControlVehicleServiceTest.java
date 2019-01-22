package co.com.ceiba.parkingchallenge.unityTests.services;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.exceptions.ViolatedConstraintException;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.Motorbike;
import co.com.ceiba.parkingchallenge.models.Rule;
import co.com.ceiba.parkingchallenge.models.RuleDay;
import co.com.ceiba.parkingchallenge.models.RuleDay.PlaceKey;
import co.com.ceiba.parkingchallenge.models.Vehicle;
import co.com.ceiba.parkingchallenge.repositories.ConstraintRepository;
import co.com.ceiba.parkingchallenge.repositories.RegistrationRepository;
import co.com.ceiba.parkingchallenge.services.ControlVehicle;
import co.com.ceiba.parkingchallenge.services.IControlVehicle;
import co.com.ceiba.parkingchallenge.util.ReaderContraintXml;

@RunWith(SpringRunner.class)
public class ControlVehicleServiceTest {
	
	 @TestConfiguration
		static class ControlVehicleServiceTestContextConfiguration {
	              
	        @Bean
	        public IControlVehicle controlVehicleService() {
	        	return new ControlVehicle();
	        }
	    }
	
	@Autowired
	private ControlVehicle controlVehicle;

	@Mock
	private ConstraintRepository constraintRepository;
	
	@Mock
	private RegistrationRepository registrationRepository;
	
	@Mock
	private ReaderContraintXml reader;
	
	private List<Rule> rules; //
	
	@Before
	public void setUp() {
		this.rules = Lists.newArrayList( createRule("A", "SATURDAY", "SUNDAY") );
	}
	
	@Test
	public void applyRuleWhereVehicleIsAlreadyRegistered() {
		
		when(registrationRepository.findRegistrationActive("M1")).thenReturn(new RegistrationEntity());
		
		try {
			controlVehicle.validateRegister(createVehicle("M1"), constraintRepository, registrationRepository);
			
			fail("ViolatedConstraintException experada porque ya se encontro un vehiclo con esta placa activo");
		}catch (ViolatedConstraintException e) {
			assertThat(e)
				.hasMessage("Este Vehiculo ya se encuentra en el parqueadero");
		}
	}
	
	@Test
	public void applyRuleWhereVehicleIsNotRegisteredAndIsCar(){
		
		when(registrationRepository.findRegistrationActive("XXX")).thenReturn( null ); // el vihiculo no esta registrado
		when(constraintRepository.numberMaxNumberVehicle("Car")).thenReturn( 20 );  //Retorna el maximo de carros permitidos
		when(registrationRepository.countActiveCar()).thenReturn( 5 ); //Cantidad de carros activos en el parqueadero
		when(reader.readerRules(new File("rules.xml"), Rule.Type.PLATE)).thenReturn( rules );
		
		Vehicle vehicle = new Car("XXX", "YYY", "ZZZ");
		
		RegistrationEntity registrationEntity = controlVehicle
				.validateRegister(vehicle, constraintRepository, registrationRepository);
		
		assertThat(registrationEntity.getVehicleEntity().getPlate())
			.isEqualTo( vehicle.getPlate() );
	}
	
	@Test
	public void applyRuleWhereVehicleIsNotRegisteredAndIsMotorbike(){
		when(registrationRepository.findRegistrationActive("XXX")).thenReturn( null ); // el vihiculo no esta registrado
		when(constraintRepository.numberMaxNumberVehicle("Motorbike")).thenReturn( 10 );  //Retorna el maximo de carros permitidos
		when(registrationRepository.countActiveMotorbike()).thenReturn( 5 ); //Cantidad de motos activas en el parqueadero

		
		Vehicle vehicle = new Motorbike("XXX", "YYY", "ZZZ");
		
		RegistrationEntity registrationEntity = controlVehicle
				.validateRegister(vehicle, constraintRepository, registrationRepository);
		
		assertThat(registrationEntity.getVehicleEntity().getPlate())
			.isEqualTo( vehicle.getPlate() );
	}
	
	@Test
	public void applyRuleWhereVehicleIsNotRegisteredAndIsCarIsFail(){
		when(registrationRepository.findRegistrationActive("XXX")).thenReturn( null ); // el vihiculo no esta registrado
		when(constraintRepository.numberMaxNumberVehicle("Car")).thenReturn( 20 );  //Retorna el maximo de carros permitidos
		when(registrationRepository.countActiveCar()).thenReturn( 20 ); //Cantidad de carros activas en el parqueadero excede el limite
		
		Vehicle vehicle = new Car("XXX", "YYY", "ZZZ");
		
		try{
			controlVehicle
			.validateRegister(vehicle, constraintRepository, registrationRepository);
			fail("ViolatedConstraintException experada porque la cantidad de carros activos es el parqueadero ya fue alcanzada");
		}catch (ViolatedConstraintException e) {
			assertThat(e)
				.hasMessage("Cantidad maxima de Carros alcanzados");
		}
	}
	
//	@Test
//	public void applyRuleWhereVehicleIsNotRegisteredAndIsCarIsFailByRule(){
//		when(registrationRepository.findRegistrationActive("XXX")).thenReturn( null ); // el vihiculo no esta registrado
//		when(constraintRepository.numberMaxNumberVehicle("Car")).thenReturn( 20 );  //Retorna el maximo de carros permitidos
//		when(registrationRepository.countActiveCar()).thenReturn( 15 ); //Cantidad de carros activas en el parqueadero
//		when(reader.readerRules(new File("rules.xml"), Rule.Type.PLATE)).thenReturn( rules );
//		when(todayMock.now().getDayOfWeek().name()).thenReturn("SATURDAY");
//		
//		
//		Vehicle vehicle = new Car("XXX", "YYY", "ZZZ");
//		
//		try{
//			controlVehicle
//			.validateRegister(vehicle, constraintRepository, registrationRepository);
//			fail("ViolatedConstraintException experada porque la placa del vehiculo viola una restriccion");
//		}catch (ViolatedConstraintException e) {
//			assertThat(e)
//				.hasMessage("Hoy, el vehiculo con esta: "+ vehicle.getPlate() +" NO esta autorizado para ingresar");
//		}
//	}
	
	@Test
	public void applyRuleWhereVehicleIsNotRegisteredAndIsMotorbikeIsFail(){
		when(registrationRepository.findRegistrationActive("XXX")).thenReturn( null ); // el vihiculo no esta registrado
		when(constraintRepository.numberMaxNumberVehicle("Motorbike")).thenReturn( 10 );  //Retorna el maximo de carros permitidos
		when(registrationRepository.countActiveMotorbike()).thenReturn( 10 ); //Cantidad de motos activas en el parqueadero excede el limite

		
		Vehicle vehicle = new Motorbike("XXX", "YYY", "ZZZ");
		
		try{
			controlVehicle
			.validateRegister(vehicle, constraintRepository, registrationRepository);
			fail("ViolatedConstraintException experada porque la cantidad de motos activas es el parqueadero ya fue alcanzada");
		}catch (ViolatedConstraintException e) {
			assertThat(e)
				.hasMessage("Cantidad maxima de Motos alcanzados");
		}
	}
	
	/**
	 * 
	 * @param plate
	 * @return
	 */
	private Vehicle createVehicle(String plate) {
		return new Car(
				plate, RandomStringUtils.random(10, true, false), RandomStringUtils.random(4, true, false));
	}
	
	/**
	 * 
	 * @param key
	 * @param valueAdded
	 * @return
	 */
	private Rule createRule(String key, String... days) {
		RuleDay rule = new RuleDay(key);
		rule.setPlace(PlaceKey.START);
		rule.setDays(Lists.newArrayList(days));
		return rule;
	}
}
