package co.com.ceiba.parkingchallenge.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name="MOTORBIKES")
@Data
@AllArgsConstructor
public class MotorbikeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_MOTORBIKE")
	private Long id;
	
	@Column(name = "DISPLACEMENT", nullable = false)
	private Integer displacement;
	
	@OneToOne(cascade = CascadeType.ALL,optional=false)
	@JoinColumn(name="VEHICLE_ID_FK", unique=true)
	private VehicleEntity vehicleEntity;
	
	public MotorbikeEntity() {
		super();
	}
}
