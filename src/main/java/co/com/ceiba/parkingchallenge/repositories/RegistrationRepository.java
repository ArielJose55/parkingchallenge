package co.com.ceiba.parkingchallenge.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.ceiba.parkingchallenge.entities.RegistrationEntity;

@Repository
public interface RegistrationRepository extends CrudRepository<RegistrationEntity, Long>{
	
	@Query(value="SELECT count(*) FROM registrations r JOIN motorbikes m ON r.vehicle_id_fk = m.vehicle_id_fk JOIN vehicles v ON v.vehicle_plate = m.vehicle_id_fk WHERE\r\n" + 
			"r.state = 'ACTIVE'",
			nativeQuery= true)
	public Integer countActiveMotorbike();
	
	@Query(value="SELECT count(*) FROM registrations r JOIN vehicles v ON r.vehicle_id_fk=v.vehicle_plate JOIN cars c ON v.vehicle_plate=c.vehicle_id_fk WHERE r.state='ACTIVE'",
			nativeQuery= true)
	public Integer countActiveCar();
	
	@Query(value = "SELECT * FROM registrations r WHERE r.vehicle_id_fk = :vehicle AND r.state = 'ACTIVE'",
			nativeQuery = true)
	public RegistrationEntity findRegistrationActive(@Param("vehicle") String vehicle);
}
