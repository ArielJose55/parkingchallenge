package co.com.ceiba.parkingchallenge.models;

import lombok.Data;

@Data
public abstract class Vehicle {
	
	private String plate;
	private String model;
	private String brand;
	
	
	public Vehicle() {
		super();
	}

	public Vehicle(String model) {
		super();
		this.model = model;
	}

	public Vehicle(String model, String brand) {
		super();
		this.model = model;
		this.brand = brand;
	}

	public Vehicle(String plate, String model, String brand) {
		super();
		this.plate = plate;
		this.model = model;
		this.brand = brand;
	}
}
