package co.com.ceiba.parkingchallenge.unityTests.services;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

import co.com.ceiba.parkingchallenge.common.UtilUnit;
import co.com.ceiba.parkingchallenge.entities.CarEntity;
import co.com.ceiba.parkingchallenge.entities.MotorbikeEntity;
import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.entities.StateType;
import co.com.ceiba.parkingchallenge.entities.VehicleEntity;
import co.com.ceiba.parkingchallenge.exceptions.ViolatedConstraintException;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.Motorbike;
import co.com.ceiba.parkingchallenge.models.Rule;
import co.com.ceiba.parkingchallenge.models.RuleDay;
import co.com.ceiba.parkingchallenge.models.RuleDay.PlaceKey;
import co.com.ceiba.parkingchallenge.models.Vehicle;
import co.com.ceiba.parkingchallenge.repositories.CarRepository;
import co.com.ceiba.parkingchallenge.repositories.ConstraintRepository;
import co.com.ceiba.parkingchallenge.repositories.MotorbikeRepository;
import co.com.ceiba.parkingchallenge.repositories.RegistrationRepository;
import co.com.ceiba.parkingchallenge.services.ControlVehicle;
import co.com.ceiba.parkingchallenge.util.ReaderContraintXml;


@RunWith(SpringRunner.class)
public class ControlVehicleTest extends UtilUnit{
	
	@Mock
	private ConstraintRepository constraintRepository;
	
	@Mock
	private RegistrationRepository registrationRepository;
	
	@Mock
	private CarRepository carRepository;
	
	@Mock
	private MotorbikeRepository motorbikeRepository;
	
	@Mock
	private ReaderContraintXml reader;
	
	@InjectMocks
	private ControlVehicle controlVehicle;
	
	private List<Rule> rules; //
	
	@Before
	public void setUp() {
		this.rules = Lists.newArrayList( createRule("A", "SATURDAY", "SUNDAY") );
	}
	
	@Test
	public void applyRuleWhereVehicleIsAlreadyRegistered() {
		
		when(registrationRepository.findRegistrationActive("M1")).thenReturn(new RegistrationEntity());
		
		try {
			controlVehicle.validateRegister(createVehicleCar("M1"), constraintRepository, registrationRepository);
			
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
	
	@Test
	public void verifyActiveRegistrationTest() {
		
		RegistrationEntity registrationEntity = new RegistrationEntity(1L, LocalDateTime.now(), StateType.ACTIVE, 
				new VehicleEntity("XXX","YYY","ZZZ"));
		
		when(registrationRepository.findRegistrationActive("XXX"))
			.thenReturn(registrationEntity);
		
		assertThat(controlVehicle.verifyActiveRegistration("XXX", registrationRepository).getVehicleEntity().getPlate())
			.isEqualTo( "XXX" );
	}
	
	@Test
	public void applyRuleWhereVehicleIsNotRegisteredAndIsCarIsFailByRule(){
		RuleDay ruleForToday = new RuleDay("A"); // Regla aplicada a los matriculos que empiecen por A y para el dia de hoy
		ruleForToday.setDays(Lists.newArrayList(LocalDateTime.now().getDayOfWeek().name()));
		ruleForToday.setPlace(PlaceKey.START);
		
		Vehicle car = createVehicleCar("AAMM-4");
		try {
			controlVehicle.applyRulesOfParking(car, Lists.newArrayList(ruleForToday));
		}catch (ViolatedConstraintException e) {
			assertThat(e)
				.hasMessage("Hoy, el vehiculo con esta: " + car.getPlate() + " NO esta autorizado para ingresar");
		}
	}
	
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
	
	@Test
	public void findVehicleByPlateTestIsNull() {
		when(carRepository.findByPlate("M1")).thenReturn( null ); // el vihiculo no esta registrado
		when(motorbikeRepository.findByPlate("M1")).thenReturn( null );
		
		assertThat(controlVehicle.findVehicleByPlate("M1", carRepository, motorbikeRepository))
			.isNull();
	}
	
	@Test
	public void findVehicleByPlateTestCarIsNotNull() {
		when(carRepository.findByPlate("M1"))
			.thenReturn( new CarEntity(1L, "Combustion", new VehicleEntity("M1", "XX", "YY")) ); // el vihiculo esta registrado
		when(motorbikeRepository.findByPlate("M1")).thenReturn( null );
		
		assertThat(controlVehicle.findVehicleByPlate("M1", carRepository, motorbikeRepository))
			.isInstanceOf(Car.class);
	}
	
	@Test
	public void findVehicleByPlateTestMotorbikeIsNotNull() {
		when(carRepository.findByPlate("M1")).thenReturn( null );
		when(motorbikeRepository.findByPlate("M1"))
			.thenReturn( new MotorbikeEntity(1L, 1000, new VehicleEntity("M1", "XX", "YY")) ); // el vihiculo esta registrado
		
		
		assertThat(controlVehicle.findVehicleByPlate("M1", carRepository, motorbikeRepository))
			.isInstanceOf(Motorbike.class);
	}
	
	@Test
	public void listAllVehiclesTest() {
		List<CarEntity> carList = Lists.newArrayList(createCarEntity("M1"), createCarEntity("M2")); // Lista de carros
		List<MotorbikeEntity> motoList = Lists.newArrayList(createMotorbikeEntity("M3"), createMotorbikeEntity("M4"));
		
		when(carRepository.findAllActiveVehicles()).thenReturn( carList );
		when(motorbikeRepository.findAllActiveVehicles()).thenReturn( motoList );
		
		assertThat(controlVehicle.listAllVehicles(carRepository, motorbikeRepository))
			.size()
			.isEqualTo(4);
		
	}

}
