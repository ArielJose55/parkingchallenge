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
@Table(name="MOTORBIKES")
@Data
@AllArgsConstructor
public class MotorbikeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_MOTORBIKE")
	private Long id;
	
	@Column(name = "DISPLACEMENT", nullable = false)
	private String displacement;
	
	public MotorbikeEntity() {
		super();
	}
}
