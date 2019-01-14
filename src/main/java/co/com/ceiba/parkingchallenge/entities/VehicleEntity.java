package co.com.ceiba.parkingchallenge.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name="VEHICLES")
@Data
@AllArgsConstructor
public class VehicleEntity {
	
	@Id
	@Column(name="VEHICLE_PLATE", nullable = false)
	private String plate;
	
	@Column(name = "VEHICHE_MODEL", nullable = false)
	private String model;
	
	@Column(name = "VEHICLE_BRAND", nullable = false)
	private String brand;
	
	public VehicleEntity() {
		super();
	}
}
