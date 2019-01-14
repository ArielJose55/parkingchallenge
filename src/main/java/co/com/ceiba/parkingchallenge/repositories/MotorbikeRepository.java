package co.com.ceiba.parkingchallenge.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.ceiba.parkingchallenge.entities.MotorbikeEntity;

@Repository
public interface MotorbikeRepository extends CrudRepository<MotorbikeEntity, Long>{
	
	@Query(value = "SELECT * FROM MOTORBIKES c JOIN REGISTRATIONS r ON c.vehicle_id_fk = r.vehicle_id_fk WHERE r.state='ACTIVE'",
			nativeQuery = true)
	public List<MotorbikeEntity> findAllActiveVehicles();
	
	@Query(value = "SELECT * FROM MORTORBIKES m WHERE m.vehicle_id_fk = :plate",
			nativeQuery = true)
	public MotorbikeEntity findByPlate(@Param("plate") String plate);
}
