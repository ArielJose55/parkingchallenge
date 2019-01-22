package co.com.ceiba.parkingchallenge.unityTests.services.calculation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

import co.com.ceiba.parkingchallenge.entities.MotorbikeEntity;
import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.entities.StateType;
import co.com.ceiba.parkingchallenge.entities.TariffEntity;
import co.com.ceiba.parkingchallenge.exceptions.ViolatedConstraintException;
import co.com.ceiba.parkingchallenge.models.Rule;
import co.com.ceiba.parkingchallenge.models.RuleDisplacement;
import co.com.ceiba.parkingchallenge.services.calculation.CalculationPaymentCar;
import co.com.ceiba.parkingchallenge.services.calculation.CalculationPaymentMotobike;
import co.com.ceiba.parkingchallenge.services.calculation.ICalculation;
import co.com.ceiba.parkingchallenge.util.ReaderContraintXml;

@RunWith(SpringRunner.class)
public class CalculationTest {

	private List<TariffEntity> tariffs;
	
	@org.junit.Rule
	public final JUnitSoftAssertions calForDay = new JUnitSoftAssertions();
	
	@org.junit.Rule
	public final JUnitSoftAssertions calForHour = new JUnitSoftAssertions();
	
	@org.junit.Rule
	public final JUnitSoftAssertions calForHourAndDays = new JUnitSoftAssertions();
	
	
	@Before
	public void setUp() {
		this.tariffs = Lists.newArrayList(
				createTariff(1, 1000.0, "Car"),
				createTariff(24, 8000.0, "Car"),
				createTariff(1, 500.0, "Motorbike"),
				createTariff(24, 4000.0, "Motorbike"));
	}
	
	@Test
	public void calculationPayByDay() {
		ICalculation calculator = new CalculationPaymentCar();
		
		RegistrationEntity registrationEntity = new RegistrationEntity(1L,
													LocalDateTime.now().minusDays(1), // para comparar 24 horas
													StateType.ACTIVE, null);  
		
		double payCar = calculator.calculateAmountToPay(registrationEntity,
						null,
						tariffs.stream()
							.filter(r -> r.getTypeVehicle().equals("Car"))
							.filter(t -> t.getNumberHours() == 24) //hay tariff aplicadas a 24 horas para carros
							.collect(Collectors.toList()));
		
		double payMoto = calculator.calculateAmountToPay(registrationEntity,
				null,
				tariffs.stream()
					.filter(r -> r.getTypeVehicle().equals("Motorbike"))
					.filter(t -> t.getNumberHours() == 24) //hay tariff aplicadas a 24 horas motos
					.collect(Collectors.toList()));
		
		calForDay.assertThat(payCar).isEqualTo(8000.0);
		calForDay.assertThat(payMoto).isEqualTo(4000.0);
	}

	
	@Test
	public void calculationPayByHour() {
		ICalculation calculator = new CalculationPaymentCar();
		
		RegistrationEntity registrationEntity = new RegistrationEntity(1L,
													LocalDateTime.now().minusHours(1), // para comparar 1 hora
													StateType.ACTIVE, null);  
		
		double payCar = calculator.calculateAmountToPay(registrationEntity,
						null,
						tariffs.stream()
							.filter(r -> r.getTypeVehicle().equals("Car"))
							.filter(t -> t.getNumberHours() == 1) //hay tariff aplicadas a 1 hora para carros
							.collect(Collectors.toList()));
		
		double payMoto = calculator.calculateAmountToPay(registrationEntity,
				null,
				tariffs.stream()
					.filter(r -> r.getTypeVehicle().equals("Motorbike"))
					.filter(t -> t.getNumberHours() == 1) //hay tariff aplicadas a 1 hora para motos
					.collect(Collectors.toList()));
		
		calForHour.assertThat(payCar).isEqualTo(1000.0);
		calForHour.assertThat(payMoto).isEqualTo(500.0);
	}
	
	
	@Test
	public void calculationPayByHoursAndDays() {
		ICalculation calculator = new CalculationPaymentCar();
		
		RegistrationEntity registrationEntity = new RegistrationEntity(1L,
													LocalDateTime.now().minusDays(1).minusHours(3), // para comparar 1 horas y 
													StateType.ACTIVE, null);  
		
		double payCar = calculator.calculateAmountToPay(registrationEntity,
						null,
						tariffs.stream()
							.filter(r -> r.getTypeVehicle().equals("Car"))
							.collect(Collectors.toList()));
		
		double payMoto = calculator.calculateAmountToPay(registrationEntity,
				null,
				tariffs.stream()
					.filter(r -> r.getTypeVehicle().equals("Motorbike"))
					.collect(Collectors.toList()));
		
		calForDay.assertThat(payCar).isEqualTo(8000.0 + 3000.0);
		calForDay.assertThat(payMoto).isEqualTo(4000.0 + 1500.0);
	}
	
	
	@Test
	public void calculationPayByHoursWhenNotFoundTariffs() {
		ICalculation calculator = new CalculationPaymentCar();
		RegistrationEntity registrationEntity = new RegistrationEntity(1L,
													LocalDateTime.now().minusHours(1), // para comparar 1 hora
													StateType.ACTIVE, null);  
		try {
			calculator.calculateAmountToPay(registrationEntity,
					null,
					tariffs.stream()
						.filter(t -> t.getNumberHours() == 24) // No hay tariff aplicadas para 1 hora
						.collect(Collectors.toList())); // Aqui se filtran las tarifas para que queden solo las de 1 hora
			
			fail("ViolatedConstraintException experada porque no existen tarrifas de 1 horas aplicables");
		}catch (ViolatedConstraintException e) {
			assertThat(e)
				.hasMessageStartingWith("No existe tarrifa aplicable para este vehiculo para ");
		}
	}
	
	
	@Test
	public void calculationPayByDayWhenNotFoundTariffs() {
		ICalculation calculator = new CalculationPaymentCar();
		
		RegistrationEntity registrationEntity = new RegistrationEntity(1L,
													LocalDateTime.now().minusDays(1), // para comparar 24 horas
													StateType.ACTIVE, null);  
		try {
			calculator.calculateAmountToPay(registrationEntity,
					null,
					tariffs.stream()
						.filter(t -> t.getNumberHours() == 1) // No hay tariff aplicadas para 24 horas
						.collect(Collectors.toList())); // Aqui se filtran las tarifas para que queden solo las de 1 hora
			
			fail("ViolatedConstraintException experada porque no existen tarrifas de 24 horas aplicables");
		}catch (ViolatedConstraintException e) {
			assertThat(e)
				.hasMessageStartingWith("No existe tarrifa aplicable para este vehiculo para ");
		}
	}
	
	@Test
	public void calculationPayByDayAndHoursWhenNotFoundTariffs() {
		ICalculation calculator = new CalculationPaymentCar();
		
		RegistrationEntity registrationEntity = new RegistrationEntity(1L,
													LocalDateTime.now().minusDays(1).minusHours(4), // para comparar 1 dia y 4 horas
													StateType.ACTIVE, null);  
		try {
			calculator.calculateAmountToPay(registrationEntity,
					null,
					tariffs.stream()
						.filter(t -> t.getNumberHours() == 3) // No hay tariff aplicadas para 3 horas, las cuales no existen
						.collect(Collectors.toList())); // Aqui se filtran las tarifas para que queden solo las de 1 hora
			
			fail("ViolatedConstraintException experada porque no existen tarrifas de 24 horas aplicables, ni tampoco de una hora");
		}catch (ViolatedConstraintException e) {
			assertThat(e)
				.hasMessageStartingWith("No existen tarrifas aplicables para este vehiculo");
		}
	}
	
	@Test
	public void calculationPayTariffByRuleDisplacement() {
		ReaderContraintXml reader = mock(ReaderContraintXml.class);
		CalculationPaymentMotobike calculator = new CalculationPaymentMotobike();
		
		File file = new File("rules.xml");
		MotorbikeEntity motorbikeEntity = new MotorbikeEntity(1L, 1000, null); // Motor mayor a 500 CC
		RegistrationEntity registrationEntity = new RegistrationEntity(1L,
				LocalDateTime.now().minusDays(1), // para comparar 1 dia 
				StateType.ACTIVE, null);
		
		when(reader.readerRules(file, Rule.Type.DISPLACEMENT))
			.thenReturn( Lists.newArrayList( createRule( "500", 2000.0 ))); //debe retornar una Regla para motos 500 pagen 2000 mas
		
		double pay = calculator.calculateAmountToPay(registrationEntity, motorbikeEntity, 
				tariffs.stream()
				.filter(t -> t.getTypeVehicle().equals("Motorbike"))
				.collect(Collectors.toList()));
		
		assertThat(pay)
			.isEqualTo(6000.0);
	}
	
	private TariffEntity createTariff(int hours, double value, String type) {
		return new TariffEntity(1L, hours, value, type);
	}
	
	private Rule createRule(String key, double valueAdded) {
		RuleDisplacement rule = new RuleDisplacement(key);
		rule.setValueAdded(valueAdded);
		return rule;
	}
	
	
}
