package co.com.ceiba.parkingchallenge;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import co.com.ceiba.parkingchallenge.repositories.CarRepository;
import co.com.ceiba.parkingchallenge.repositories.MotorbikeRepository;

@SpringBootApplication
public class ParkingchallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingchallengeApplication.class, args);
	}
	
	@Bean
	public ApplicationRunner xmlPrueba(CarRepository carRepo, MotorbikeRepository motor) {
		return args -> {carRepo.findAllActiveVehicles().forEach(System.err::println);
		motor.findAllActiveVehicles().forEach(System.err::println);
		};
	}
}
