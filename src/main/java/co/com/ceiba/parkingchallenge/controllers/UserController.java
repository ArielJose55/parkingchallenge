package co.com.ceiba.parkingchallenge.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.parkingchallenge.models.User;
import co.com.ceiba.parkingchallenge.services.UserService;
import co.com.ceiba.parkingchallenge.services.exceptions.NotFountModelException;
import co.com.ceiba.parkingchallenge.services.exceptions.NotSaveModelException;

@RestController("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public User login(@PathVariable String identification, @PathVariable String password) {
		return userService.loginUser(identification, password)
				.orElseThrow(() -> new NotFountModelException(User.class));
	}
	
	@PostMapping("/save")
	public User save(@RequestBody User user) {
		return userService.saveUser(user)
				.orElseThrow(() -> new NotSaveModelException(User.class));
	}
	
	@GetMapping	
	public List<User> getUserAll(){
		return userService.listAll()
				.orElseThrow(() -> new NotFountModelException(User.class));
	}
}
