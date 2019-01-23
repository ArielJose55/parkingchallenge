package co.com.ceiba.parkingchallenge.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="REGISTRATIONS")
@Setter
@Getter
@AllArgsConstructor
public class RegistrationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_REGISTRATION")
	private Long id;
	
	@Column(name = "REGISTRATION_DATE", nullable = false)
	@Convert(converter = LocalDateTimeConverter.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDateTime registrationDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATE")
	private StateType state;
	
	@OneToOne(optional = false)
	@JoinColumn(name = "VEHICLE_ID_FK", referencedColumnName = "VEHICLE_PLATE")
	private VehicleEntity vehicleEntity;
	
	public RegistrationEntity() {
		super();
	}
}
