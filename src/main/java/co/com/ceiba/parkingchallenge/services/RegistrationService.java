package co.com.ceiba.parkingchallenge.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.parkingchallenge.entities.CarEntity;
import co.com.ceiba.parkingchallenge.entities.InvoiceEntity;
import co.com.ceiba.parkingchallenge.entities.MotorbikeEntity;
import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;
import co.com.ceiba.parkingchallenge.mappers.InvoiceMapper;
import co.com.ceiba.parkingchallenge.mappers.RegistrationMapper;
import co.com.ceiba.parkingchallenge.models.Car;
import co.com.ceiba.parkingchallenge.models.Invoice;
import co.com.ceiba.parkingchallenge.models.Motorbike;
import co.com.ceiba.parkingchallenge.models.Registration;
import co.com.ceiba.parkingchallenge.models.Vehicle;
import co.com.ceiba.parkingchallenge.repositories.CarRepository;
import co.com.ceiba.parkingchallenge.repositories.InvoiceRepository;
import co.com.ceiba.parkingchallenge.repositories.MotorbikeRepository;
import co.com.ceiba.parkingchallenge.repositories.RegistrationRepository;
import co.com.ceiba.parkingchallenge.repositories.TariffRepository;
import co.com.ceiba.parkingchallenge.services.calculation.CalculationPaymentCar;
import co.com.ceiba.parkingchallenge.services.calculation.CalculationPaymentMotobike;
import co.com.ceiba.parkingchallenge.services.calculation.ICalculation;

@Service
public class RegistrationService {

	@Autowired
	private RegistrationRepository registrationRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private MotorbikeRepository motorbikeRepository;
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private TariffRepository tariffRepository;
	
	public Optional<List<Registration>> listAllRegistrations(){
		List<CarEntity> carList = carRepository.findAllActiveVehicles();
		List<MotorbikeEntity> motorBike = motorbikeRepository.findAllActiveVehicles();
		return Optional.of(registrationRepository.listAllRegistrationActive()
				.stream()
				.map(r -> RegistrationMapper.mapperToModel(r, carList, motorBike))
				.collect(Collectors.toList()));
	}
	
	public Optional<Invoice> registerVehicularExit(Vehicle vehicle){
		RegistrationEntity reEntity = registrationRepository.findRegistrationActive(vehicle.getPlate());
		
		if(reEntity  != null) {
			if(vehicle instanceof Car) {
				
				ICalculation calulator = new CalculationPaymentCar();
				double valueToPay = calulator.calculateAmountToPay(reEntity, carRepository.findByPlate(vehicle.getPlate()), tariffRepository.findByTypeVehicle(Car.class.getSimpleName()));
				InvoiceEntity invoice = new InvoiceEntity(null, LocalDateTime.now(), valueToPay, reEntity);

				return Optional.of(InvoiceMapper
						.mapperToModel(invoiceRepository.save(invoice)));
			}else {
				ICalculation calulator = new CalculationPaymentMotobike();
				double valueToPay = calulator.calculateAmountToPay(reEntity, motorbikeRepository.findByPlate(vehicle.getPlate()), tariffRepository.findByTypeVehicle(Motorbike.class.getSimpleName()));
				
				InvoiceEntity invoice = new InvoiceEntity(null, LocalDateTime.now(), valueToPay, reEntity);

				return Optional.of(InvoiceMapper
						.mapperToModel(invoiceRepository.save(invoice)));
			}
		}
		throw new RuntimeException("El vehiculo con esta placa no se encuentre en el parqueadero");
	}
}
