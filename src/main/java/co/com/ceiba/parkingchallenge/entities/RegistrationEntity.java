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
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;

import com.fasterxml.jackson.annotation.JsonFormat;

import co.com.ceiba.parkingchallenge.models.StateType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name="REGISTRATIONS")
@Data
@AllArgsConstructor
public class RegistrationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_REGISTRATION")
	private Long id;
	
	@Column(name="REGISTRATION_DATE", nullable=false)
	@Convert(converter = LocalDateTimeConverter.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDateTime registrationDate;
	
	@Enumerated(EnumType.STRING)
	private StateType state;
	
	public RegistrationEntity() {
		super();
	}
}
