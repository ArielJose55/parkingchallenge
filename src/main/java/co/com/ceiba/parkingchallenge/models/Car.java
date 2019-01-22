package co.com.ceiba.parkingchallenge.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
public class Car extends Vehicle{

	private String typeMotor;
	
	public Car() {
		super();
	}

	public Car(String plate) {
		super(plate);
	}
	
	public Car(String plate, String model, String brand) {
		super(plate, model, brand);
	}

	public Car(String plate, String model, String brand, String typeMotor) {
		this(plate, model, brand);
		this.typeMotor = typeMotor;
	}	
}
