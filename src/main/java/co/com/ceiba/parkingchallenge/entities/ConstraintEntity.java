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
@Table(name="CONSTRAINTS")
@Data
@AllArgsConstructor
public class ConstraintEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_CONSTRAINT")
	private Long id;
	
	@Column(name = "MAX_AMOUNT")
	private Integer maximumAmount;
	
	@Column(name = "MIN_AMOUNT", nullable = false)
	private Integer minimunAmount;
	
	public ConstraintEntity() {
		super();
	}
}
