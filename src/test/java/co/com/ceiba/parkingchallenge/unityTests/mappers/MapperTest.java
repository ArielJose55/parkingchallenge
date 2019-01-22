package co.com.ceiba.parkingchallenge.unityTests.mappers;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

import co.com.ceiba.parkingchallenge.entities.CarEntity;
import co.com.ceiba.parkingchallenge.entities.InvoiceEntity;
import co.com.ceiba.parkingchallenge.entities.MotorbikeEntity;
import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.entities.StateType;
import co.com.ceiba.parkingchallenge.entities.VehicleEntity;
import co.com.ceiba.parkingchallenge.exceptions.ViolatedConstraintException;
import co.com.ceiba.parkingchallenge.mappers.InvoiceMapper;
import co.com.ceiba.parkingchallenge.mappers.RegistrationMapper;
import co.com.ceiba.parkingchallenge.mappers.VehicleMapper;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.Motorbike;
import co.com.ceiba.parkingchallenge.models.Registration;
import co.com.ceiba.parkingchallenge.models.Vehicle;

@RunWith(SpringRunner.class)
public class MapperTest {

	@Rule
	public final JUnitSoftAssertions mapperEntitysNulls = new JUnitSoftAssertions();
	
	@Rule
	public final JUnitSoftAssertions mapperEntitisNotNulls = new JUnitSoftAssertions();
	
	@Rule
	public final JUnitSoftAssertions mapperModelsNull = new JUnitSoftAssertions();
	
	@Rule
	public final JUnitSoftAssertions mapperModelNotNull = new JUnitSoftAssertions();
	
	@Test
	public void mapperExpetedOfEntityNull () {
		InvoiceEntity invoiceEntity = null;
		CarEntity carEntity = null;
		MotorbikeEntity motorbikeEntity = null;
		mapperEntitysNulls.assertThat(InvoiceMapper.mapperToModel(invoiceEntity)).isNull();
		mapperEntitysNulls.assertThat(VehicleMapper.mapperToModel(carEntity)).isNull();
		mapperEntitysNulls.assertThat(VehicleMapper.mapperToModel(motorbikeEntity)).isNull();
		mapperEntitysNulls.assertThat(VehicleMapper.mapperOfEntity(carEntity)).isNull();
		mapperEntitysNulls.assertThat(VehicleMapper.mapperOfEntity(motorbikeEntity)).isNull();
	}
	
	@Test
	public void mapperExpetedOfEntityNotNull () {
		InvoiceEntity invoiceEntity = new InvoiceEntity(1L, LocalDateTime.now(), 10D, null);
		VehicleEntity vehicle = new VehicleEntity("XXX", "YYY", "ZZZ");
		CarEntity carEntity = new CarEntity(12L, "Combustion", vehicle);
		MotorbikeEntity motorbikeEntity = new MotorbikeEntity(2L, 1000, vehicle);
		RegistrationEntity registrationEntity = new RegistrationEntity(2L, LocalDateTime.now(), StateType.ACTIVE, vehicle);
	
		mapperEntitisNotNulls.assertThat( InvoiceMapper.mapperToModel( invoiceEntity ).getId() ).isEqualTo( 1L );
		mapperEntitisNotNulls.assertThat( VehicleMapper.mapperToModel( carEntity ).getPlate() ).isEqualTo( "XXX" );
		mapperEntitisNotNulls.assertThat( VehicleMapper.mapperToModel( motorbikeEntity ).getBrand() ).isEqualTo( "ZZZ" );
		mapperEntitisNotNulls.assertThat( VehicleMapper.mapperOfEntity( carEntity) ).isInstanceOf(Car.class);
		mapperEntitisNotNulls.assertThat( VehicleMapper.mapperOfEntity( motorbikeEntity) ).isInstanceOf(Motorbike.class);
		mapperEntitisNotNulls.assertThat( RegistrationMapper.mapperToModel( registrationEntity ).getState() ).isEqualTo(StateType.ACTIVE);
	}
	
	@Test
	public void mapperExpectedOfModelNull() {
		Vehicle vehicle = null;
		Car car = null;
		Motorbike moto = null;
		mapperModelsNull.assertThat(VehicleMapper.mapperToEntity(vehicle)).isNull();
		mapperModelsNull.assertThat(VehicleMapper.mapperToEntity(car)).isNull();
		mapperModelsNull.assertThat(VehicleMapper.mapperToEntity(moto)).isNull();
		
	}
	
	@Test
	public void mapperExpectedOfModelNotNull() {
		Car car = new Car("XXX", "YYY", "ZZZ", "Combustion");
		Motorbike moto = new Motorbike("XXXM", "YYYM", "ZZZM", 1000);
		Vehicle vehicle = new Car("XXX", "YYY", "ZZZ", "Combustion");
		
		mapperModelNotNull.assertThat(VehicleMapper.mapperToEntity( car ).getMotorType() ).isEqualTo("Combustion");
		mapperModelNotNull.assertThat(VehicleMapper.mapperToEntity(moto).getDisplacement()).isEqualTo(1000);
		mapperModelNotNull.assertThat(VehicleMapper.mapperToEntity( vehicle )).isInstanceOf(VehicleEntity.class);
	}
	
	@Test
	public void mapperToModelRegistrationFail() {
		
		List<CarEntity> listCars = Lists.newArrayList(
				new CarEntity(1L, "Combustion", createVehicleAleatory("M1")), //carro misma placa
				new CarEntity(2L, "Combustion", createVehicleAleatory("M2")),
				new CarEntity(3L, "Combustion", createVehicleAleatory("M3")));
		
		List<MotorbikeEntity> listBikes = Lists.newArrayList(
				new MotorbikeEntity(1L, 500, createVehicleAleatory("M1")), //moto misma placa
				new MotorbikeEntity(2L, 600, createVehicleAleatory("M4")),
				new MotorbikeEntity(3L, 1000, createVehicleAleatory("M5")));
		
		RegistrationEntity registrationEntity = new RegistrationEntity(1L, LocalDateTime.now(), StateType.ACTIVE, createVehicleAleatory("M1"));
		
		try {
			RegistrationMapper.mapperToModel(registrationEntity, listCars, listBikes);
			fail("ViolatedConstraintException experada porque hay una moto y un caro con la misma placa: M1");
		}catch (ViolatedConstraintException e) {
			assertThat(e).hasMessage("Existe un carro y una moto esta misma placa");
		}
	}
	
	@Test
	public void mapperToModelRegistrationFailByManyCars() {
		
		List<CarEntity> listCars = Lists.newArrayList(
				new CarEntity(1L, "Combustion", createVehicleAleatory("M1")), //misma placa
				new CarEntity(2L, "Combustion", createVehicleAleatory("M1")), //misma placa
				new CarEntity(3L, "Combustion", createVehicleAleatory("M2")));
		
		List<MotorbikeEntity> listBikes = Lists.newArrayList(
				new MotorbikeEntity(2L, 600, createVehicleAleatory("M3")),
				new MotorbikeEntity(3L, 1000, createVehicleAleatory("M4")));
		
		RegistrationEntity registrationEntity = new RegistrationEntity(1L, LocalDateTime.now(), StateType.ACTIVE, createVehicleAleatory("M1"));
		
		try {
			RegistrationMapper.mapperToModel(registrationEntity, listCars, listBikes);
			fail("ViolatedConstraintException experada porque hay registrados mas de una carro con la misma placa: M1");
		}catch (ViolatedConstraintException e) {
			assertThat(e).hasMessageStartingWith("Hay registrados mas de un carro con esta placa: " + 
					registrationEntity.getVehicleEntity().getPlate() );
		}
	}
	
	@Test
	public void mapperToModelRegistrationFailByManyBikes() {
		
		List<CarEntity> listCars = Lists.newArrayList(
				new CarEntity(1L, "Combustion", createVehicleAleatory("M1")),
				new CarEntity(2L, "Combustion", createVehicleAleatory("M2")),
				new CarEntity(3L, "Combustion", createVehicleAleatory("M3")));
		
		List<MotorbikeEntity> listBikes = Lists.newArrayList(
				new MotorbikeEntity(2L, 600, createVehicleAleatory("M4")), //misma placa
				new MotorbikeEntity(3L, 1000, createVehicleAleatory("M4")), //misma placa
				new MotorbikeEntity(3L, 1000, createVehicleAleatory("M5")));
		
		RegistrationEntity registrationEntity = new RegistrationEntity(1L, LocalDateTime.now(), StateType.ACTIVE, createVehicleAleatory("M4"));
		
		try {
			RegistrationMapper.mapperToModel(registrationEntity, listCars, listBikes);
			fail("ViolatedConstraintException experada porque hay registrados mas de una moto con la misma placa: M1");
		}catch (ViolatedConstraintException e) {
			assertThat(e).hasMessageStartingWith("Hay registrados mas de una motocicleta con esta placa: "
					+ registrationEntity.getVehicleEntity().getPlate());
		}
	}
	
	@Test
	public void mapperToCarRegistrarionNotFail() {
		
		List<CarEntity> listCars = Lists.newArrayList(
				new CarEntity(1L, "Combustion", createVehicleAleatory("M1")),
				new CarEntity(2L, "Combustion", createVehicleAleatory("M2")),
				new CarEntity(3L, "Combustion", createVehicleAleatory("M3")));
		
		List<MotorbikeEntity> listBikes = Lists.newArrayList(
				new MotorbikeEntity(2L, 600, createVehicleAleatory("M4")), 
				new MotorbikeEntity(3L, 1000, createVehicleAleatory("M5")), 
				new MotorbikeEntity(3L, 1000, createVehicleAleatory("M6")));
		
		RegistrationEntity registrationEntity =
				new RegistrationEntity(1L, LocalDateTime.now(), StateType.ACTIVE, createVehicleAleatory("M3"));
		
		Registration registration = RegistrationMapper.mapperToModel(registrationEntity, listCars, listBikes);
		
		assertThat(registration.getVehicle().getPlate())
			.isEqualTo("M3");
	}
	
	@Test
	public void mapperToBikeRegistrationNotFail() {
		List<CarEntity> listCars = Lists.newArrayList(
				new CarEntity(1L, "Combustion", createVehicleAleatory("M1")),
				new CarEntity(2L, "Combustion", createVehicleAleatory("M2")),
				new CarEntity(3L, "Combustion", createVehicleAleatory("M3")));
		
		List<MotorbikeEntity> listBikes = Lists.newArrayList(
				new MotorbikeEntity(2L, 600, createVehicleAleatory("M4")), 
				new MotorbikeEntity(3L, 1000, createVehicleAleatory("M5")), 
				new MotorbikeEntity(3L, 1000, createVehicleAleatory("M6")));
		
		RegistrationEntity registrationEntity =
				new RegistrationEntity(1L, LocalDateTime.now(), StateType.ACTIVE, createVehicleAleatory("M4"));
		
		Registration registration = RegistrationMapper.mapperToModel(registrationEntity, listCars, listBikes);
		
		assertThat(registration.getVehicle().getPlate())
			.isEqualTo("M4");
	}
	
	/**
	 * utilidad para crear carros con algunos atributos aleatorios
	 * 
	 * @param plate
	 * @return
	 */
	private VehicleEntity createVehicleAleatory(String plate) {
		return new VehicleEntity(plate,
				RandomStringUtils.random(10, true, false),
				RandomStringUtils.random(10, true, false));
	}
}
