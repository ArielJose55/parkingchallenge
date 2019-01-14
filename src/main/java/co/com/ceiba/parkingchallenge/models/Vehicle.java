package co.com.ceiba.parkingchallenge.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Vehicle {

	private String plate;
	
	private String model;
	
	private String brand;
	
	public Vehicle() {
		super();
	}
	
	public Vehicle(String plate) {
		this.plate = plate;
	}
}
