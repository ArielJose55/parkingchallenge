package co.com.ceiba.parkingchallenge.services;

import java.util.List;

import co.com.ceiba.parkingchallenge.models.Invoice;
import co.com.ceiba.parkingchallenge.models.Registration;
import co.com.ceiba.parkingchallenge.models.Vehicle;
import co.com.ceiba.parkingchallenge.repositories.CarRepository;
import co.com.ceiba.parkingchallenge.repositories.InvoiceRepository;
import co.com.ceiba.parkingchallenge.repositories.MotorbikeRepository;
import co.com.ceiba.parkingchallenge.repositories.RegistrationRepository;
import co.com.ceiba.parkingchallenge.repositories.TariffRepository;

interface IControlRegistration {
	
	List<Registration> listAllRegistrations(RegistrationRepository registrationRepository, CarRepository carRepository,
			MotorbikeRepository motorbikeRepository);
	
	Invoice registerCheckOutVehicular(Vehicle vehicle ,RegistrationRepository registrationRepository, CarRepository carRepository,
			MotorbikeRepository motorbikeRepository, InvoiceRepository invoiceRepository, TariffRepository tariffRepository);
}
