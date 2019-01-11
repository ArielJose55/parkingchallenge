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
@Table(name="PARKINGS")
@Data
@AllArgsConstructor
public class ParkingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_PARKING")
	private Long id;
	
	@Column(name="NAME_PARKING", nullable=false)
	private String name;
	
	public ParkingEntity() {
		super();
	}
}
