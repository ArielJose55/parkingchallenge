package co.com.ceiba.parkingchallenge.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import co.com.ceiba.parkingchallenge.entities.UserEntity;
import co.com.ceiba.parkingchallenge.models.User;
import co.com.ceiba.parkingchallenge.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public Optional<User> loginUser(String identification, String password) {
		return Optional.of(mapOfEntity(userRepository.findByIdentificationAndPassword(identification, password)));
	}
	
	public Optional<User> saveUser(User user){
		return Optional.of(mapOfEntity(userRepository.save(mapOfUser(user))));
	}
	
	
	public Optional<List<User>> listAll(){
		return Optional.of(
				Lists.newArrayList(userRepository.findAll())
					.stream()
						.map(e -> mapOfEntity(e))
							.collect(Collectors.toList()));
	}
	
	
	private User mapOfEntity(UserEntity userEntity) {
		return new User(
				userEntity.getIdentification(),
				userEntity.getPassword(),
				userEntity.getName(),
				userEntity.getLastName());
	}
	
	private User mapOfEntity(Optional<UserEntity> userOptional) {
		if(userOptional.isPresent()) {
			UserEntity userEntity = userOptional.get();
			return new User(
					userEntity.getIdentification(),
					userEntity.getPassword(),
					userEntity.getName(),
					userEntity.getLastName());
		}else {
			return null;
		}
	}
	
	private UserEntity mapOfUser(User user) {
		return new UserEntity(
				user.getIdentification(),
				user.getPassword(),
				user.getName(),
				user.getLastName());
	}
}
