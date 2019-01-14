package co.com.ceiba.parkingchallenge.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name="CHECKS")
@Data
@AllArgsConstructor
public class Invoice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_CHECK")
	private Long id;
	
	@Column(name="DEPARTURE_DATE", nullable=false)
	@Convert(converter = LocalDateTimeConverter.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDateTime departureDate;
	
	@Column(name="AMOUNT", nullable=false)
	private Double amount;
	
	@OneToOne(optional=false)
	@JoinColumn(name="REGISTRATION_ID_FK")
	private RegistrationEntity registrationEntity;
	
	public Invoice() {
		super();
	}
}

