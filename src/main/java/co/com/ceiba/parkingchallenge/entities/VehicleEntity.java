package co.com.ceiba.parkingchallenge.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="VEHICLES")
@Getter
@Setter
@AllArgsConstructor
public class VehicleEntity {
	
	@Id
	@Size(max=10, min=4, message="La placa debe tener una longitud menor a 10 y mayor a 4 caracteres")
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
