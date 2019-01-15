package co.com.ceiba.parkingchallenge.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import co.com.ceiba.parkingchallenge.entities.ConstraintEntity;

public interface ConstraintRepository extends CrudRepository<ConstraintEntity, Long>{
	
	@Query(value = "SELECT max_amount FROM constraints c WHERE c.type_vehicle = :typeVehicle"
			,nativeQuery = true)
	public Integer numberMaxNumberVehicle(@Param("typeVehicle")String typeVehicle);
}
