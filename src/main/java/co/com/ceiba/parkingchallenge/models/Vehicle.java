package co.com.ceiba.parkingchallenge.models;

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

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
}
