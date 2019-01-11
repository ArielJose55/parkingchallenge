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
@Table(name="TARIFFS")
@Data
@AllArgsConstructor
public class TariffEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_TARIFF")
	private Long id;
	
	@Column(name="NUMBER_HOUR", nullable=false, unique=true)
	private Integer numberHours;
	
	@Column(name = "VALUE", nullable=false)
	private Double value;

	public TariffEntity() {
		super();
	}
}
