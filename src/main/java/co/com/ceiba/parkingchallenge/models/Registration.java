package co.com.ceiba.parkingchallenge.models;

import java.time.LocalDateTime;

import co.com.ceiba.parkingchallenge.entities.StateType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Registration {
	private Long id;
	private LocalDateTime registrationDate;
	private StateType state;
	private Vehicle vehicle;
	
	public Registration() {
		super();
	}
}
