package co.com.ceiba.parkingchallenge.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name="CARS")
@Data
@AllArgsConstructor
public class CarEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_CARS")
	private Long id;
	
	@Column(name="MOTOR_TYPE", nullable=false)
	private String motorType;
	
	public CarEntity() {
		super();
	}
}
