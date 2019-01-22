package co.com.ceiba.parkingchallenge.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Invoice {
	private Long id;
	private LocalDateTime departureDate;
	private Double amount;
	private Registration registration;
}
