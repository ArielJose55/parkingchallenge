package co.com.ceiba.parkingchallenge.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.parkingchallenge.entities.VehicleEntity;

@Repository
public interface VehicleRepository extends CrudRepository<VehicleEntity, String>{}
