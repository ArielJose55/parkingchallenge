package co.com.ceiba.parkingchallenge.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.ceiba.parkingchallenge.entities.CarEntity;

@Repository
public interface CarRepository extends CrudRepository<CarEntity, Long>{
	
	@Query(value = "SELECT * FROM CARS c JOIN REGISTRATIONS r ON c.vehicle_id_fk = r.vehicle_id_fk WHERE r.state='ACTIVE'",
			nativeQuery = true)
	public List<CarEntity> findAllActiveVehicles();
	
	@Query(value = "SELECT * FROM CARS c WHERE c.vehicle_id_fk = :plate",
			nativeQuery = true)
	public CarEntity findByPlate(@Param("plate") String plate);
}
