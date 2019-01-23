package co.com.ceiba.parkingchallenge.unityTests.services;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.parkingchallenge.common.UtilUnit;
import co.com.ceiba.parkingchallenge.entities.CarEntity;
import co.com.ceiba.parkingchallenge.entities.MotorbikeEntity;
import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.entities.StateType;
import co.com.ceiba.parkingchallenge.entities.TariffEntity;
import co.com.ceiba.parkingchallenge.exceptions.NotFountModelException;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.Vehicle;
import co.com.ceiba.parkingchallenge.repositories.CarRepository;
import co.com.ceiba.parkingchallenge.repositories.InvoiceRepository;
import co.com.ceiba.parkingchallenge.repositories.MotorbikeRepository;
import co.com.ceiba.parkingchallenge.repositories.RegistrationRepository;
import co.com.ceiba.parkingchallenge.repositories.TariffRepository;
import co.com.ceiba.parkingchallenge.services.ControlRegistration;
import co.com.ceiba.parkingchallenge.util.ReaderContraintXml;

@RunWith(SpringRunner.class)
public class ControlRegistrationTest extends UtilUnit {

	private List<TariffEntity> tariffs;
	
	@Mock
	private RegistrationRepository registrationRepository;
	
	@Mock
	private CarRepository carRepository;
	
	@Mock
	private MotorbikeRepository motorbikeRepository;
	
	@Mock
	private InvoiceRepository invoiceRepository;
	
	@Mock
	private TariffRepository tariffRepository;
	
	@Mock
	private ReaderContraintXml reader;

	@InjectMocks
	private ControlRegistration controlRegistration;

	@Before
	public void setUp() {
		this.tariffs = Lists.newArrayList(
				createTariff(1, 1000.0, "Car"),
				createTariff(24, 8000.0, "Car"),
				createTariff(1, 500.0, "Motorbike"),
				createTariff(24, 4000.0, "Motorbike"));
	}
	
	@Test
	public void listAllRegistrationsTest() {
		
		List<CarEntity> carList = Lists.newArrayList(createCarEntity("M1"), createCarEntity("M2")); // Lista de carros
		List<MotorbikeEntity> motoList = Lists.newArrayList(createMotorbikeEntity("M3"), createMotorbikeEntity("M4")); // Lista de motos
		
		//Lista de vehiculos activos
		List<RegistrationEntity> registrationActiveList = 
				Lists.newArrayList(	createRegistration(StateType.ACTIVE, createCarEntity("M1").getVehicleEntity() ),
									createRegistration(StateType.ACTIVE, createCarEntity("M2").getVehicleEntity() ),
									createRegistration(StateType.ACTIVE, createMotorbikeEntity("M3").getVehicleEntity() )); 
		 
		when(carRepository.findAllActiveVehicles()).thenReturn(carList);
		when(motorbikeRepository.findAllActiveVehicles()).thenReturn(motoList);
		when(registrationRepository.listAllRegistrationActive()).thenReturn(registrationActiveList);
		
		assertThat(controlRegistration.listAllRegistrations(registrationRepository, carRepository, motorbikeRepository))
			.filteredOn(r -> r.getState().equals(StateType.ACTIVE))
			.size()
			.isEqualTo(3);
	}

	@Test
	public void registerCheckOutVehicularFailWhereNotFoundRegistationActives() {
		Vehicle vehicle = new Car("M1");
		when(registrationRepository.findRegistrationActive( vehicle.getPlate() )).thenReturn( null ); //El vehiculo de placa M1 no esta activo en el parqueadero
		
		try {
			
			controlRegistration.registerCheckOutVehicular(vehicle, registrationRepository,
								carRepository, motorbikeRepository, invoiceRepository, tariffRepository);
			
			fail("NotFountModelException experada porque no se encontro el vehiculo con placa M1 en el parqueadero");
		}catch (NotFountModelException e) {
			assertThat(e).
				hasMessage("El vehiculo con esta placa " + vehicle.getPlate() + " no se encuentre en el parqueadero");
		}
	}
}
