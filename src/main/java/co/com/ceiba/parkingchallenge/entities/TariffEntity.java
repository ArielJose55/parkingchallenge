package co.com.ceiba.parkingchallenge.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TARIFFS")
@Getter
@Setter
@AllArgsConstructor
public class TariffEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_TARIFF")
	private Long id;
	
	@Column(name="NUMBER_HOUR", nullable=false)
	private Integer numberHours;
	
	@Column(name = "VALUE", nullable=false)
	private Double value;
	
	@Column(name="TYPE_VEHICLE", nullable = false)
	private String typeVehicle;
	
	public TariffEntity() {
		super();
	}
}
