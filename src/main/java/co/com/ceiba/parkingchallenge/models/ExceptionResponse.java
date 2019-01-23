package co.com.ceiba.parkingchallenge.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {

	private LocalDateTime timestamp;
	
	private String message;
	
	private String detalls;
	
	public ExceptionResponse() {
		super();
	}
}
