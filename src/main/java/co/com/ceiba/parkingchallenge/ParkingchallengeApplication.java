package co.com.ceiba.parkingchallenge;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import co.com.ceiba.parkingchallenge.models.Rule;
import co.com.ceiba.parkingchallenge.util.ReaderContraint;

@SpringBootApplication
public class ParkingchallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingchallengeApplication.class, args);
	}
	
	@Bean
	public ApplicationRunner verifi() {
		return args->{
			ReaderContraint reader = new ReaderContraint();
			reader.readerRules(Rule.Type.DISPLACEMENT).forEach(System.err::println);
		};
	}
}
