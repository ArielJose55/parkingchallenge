package co.com.ceiba.parkingchallenge.models;

public class Motorbike extends Vehicle{
	
	private String displacement;

	public Motorbike() {
		super();
	}
	
	public Motorbike(String model) {
		super(model);
	}
	
	public Motorbike(String model, String brand) {
		super(model, brand);
	}
	
	public Motorbike(String plate, String model, String brand) {
		super(plate, model, brand);
	}

	public Motorbike(String plate, String model, String brand, String displacement) {
		super(plate, model, brand);
		this.displacement = displacement;
	}

	public String getDisplacement() {
		return displacement;
	}

	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}
}
