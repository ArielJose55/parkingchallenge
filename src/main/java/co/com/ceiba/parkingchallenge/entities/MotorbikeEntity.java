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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="MOTORBIKES")
@Setter
@Getter
@AllArgsConstructor
public class MotorbikeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_MOTORBIKE")
	private Long id;
	
	@Min(value=49, message="Cilindrada muy pequena")
	@Max(value=1500, message="Cilindraje no demasiado grande para ser legal")
	@Column(name = "DISPLACEMENT", nullable = false)
	private Integer displacement;
	
	@OneToOne(cascade = CascadeType.ALL,optional=false)
	@JoinColumn(name="VEHICLE_ID_FK", unique=true,referencedColumnName="VEHICLE_PLATE")
	private VehicleEntity vehicleEntity;
	
	public MotorbikeEntity() {
		super();
	}
}
