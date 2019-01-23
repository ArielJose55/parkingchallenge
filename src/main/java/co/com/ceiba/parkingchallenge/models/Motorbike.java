package co.com.ceiba.parkingchallenge.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Motorbike extends Vehicle{

	private Integer displacement;
	
	public Motorbike() {
		super();
	}

	public Motorbike(String plate) {
		super(plate);
	}
	
	public Motorbike(String plate, String model, String brand) {
		super(plate, model, brand);
	}

	public Motorbike(String plate, String model, String brand, Integer displacement) {
		this(plate, model, brand);
		this.displacement = displacement;
	}
}
