package co.com.ceiba.parkingchallenge.mappers;

import co.com.ceiba.parkingchallenge.entities.InvoiceEntity;
import co.com.ceiba.parkingchallenge.models.Invoice;

public class InvoiceMapper {

	private InvoiceMapper() {}
	
	public static Invoice mapperToModel(InvoiceEntity entity) {
		Invoice invoice = new Invoice();
		invoice.setId(entity.getId());
		invoice.setDepartureDate(entity.getDepartureDate());
		invoice.setAmount(entity.getAmount());
		invoice.setRegistration(RegistrationMapper.mapperToModel(entity.getRegistrationEntity()));
		return invoice;
	}
}
