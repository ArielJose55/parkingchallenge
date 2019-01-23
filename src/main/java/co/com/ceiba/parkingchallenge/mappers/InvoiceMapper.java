package co.com.ceiba.parkingchallenge.mappers;

import co.com.ceiba.parkingchallenge.entities.InvoiceEntity;
import co.com.ceiba.parkingchallenge.models.Invoice;

public final class InvoiceMapper {

	private InvoiceMapper() {}
	
	public static Invoice mapperToModel(InvoiceEntity entity) {
		return entity != null?
				new Invoice(entity.getId(), entity.getDepartureDate(), entity.getAmount(), RegistrationMapper.mapperToModel(entity.getRegistrationEntity()))
				: null;
	}
}
