package co.com.ceiba.parkingchallenge.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.parkingchallenge.entities.TariffEntity;

@Repository
public interface TariffRepository extends CrudRepository<TariffEntity, Long>{
	public List<TariffEntity> findByTypeVehicle(String typeVehicle);
}
