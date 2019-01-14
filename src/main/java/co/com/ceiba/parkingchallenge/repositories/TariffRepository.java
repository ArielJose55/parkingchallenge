package co.com.ceiba.parkingchallenge.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.parkingchallenge.entities.Tariff;

@Repository
public interface TariffRepository extends CrudRepository<Tariff, Long>{}
