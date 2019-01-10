package co.com.ceiba.parkingchallenge.models;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Parking {
	
	private List<Vehicle> vehicles;
	
	public Parking() {
		this.vehicles = new ArrayList<>();
	}
	
	public Parking(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	
	public void addVehicle(Vehicle vehicle) {
		this.vehicles.add(vehicle);
	}
}
