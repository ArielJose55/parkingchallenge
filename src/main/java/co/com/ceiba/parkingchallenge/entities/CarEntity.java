package co.com.ceiba.parkingchallenge.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="CARS")
@Setter
@Getter
@AllArgsConstructor
public class CarEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_CARS")
	private Long id;
	
	@Column(name="MOTOR_TYPE", nullable=false)
	private String motorType;
	
	@OneToOne(cascade = CascadeType.ALL,optional=false)
	@JoinColumn(name="VEHICLE_ID_FK", unique=true)
	private VehicleEntity vehicleEntity;
	
	public CarEntity() {
		super();
	}
}
