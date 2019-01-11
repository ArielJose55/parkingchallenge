package co.com.ceiba.parkingchallenge.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.parkingchallenge.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String>{
	Optional<UserEntity> findByIdentificationAndPassword(String identification, String password);
}
