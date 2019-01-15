package co.com.ceiba.parkingchallenge.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.parkingchallenge.entities.InvoiceEntity;

@Repository
public interface InvoiceRepository extends CrudRepository<InvoiceEntity, Long>{}
