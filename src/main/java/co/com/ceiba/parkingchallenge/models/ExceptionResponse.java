package co.com.ceiba.parkingchallenge.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
	
	private LocalDateTime timestamp;
	private String message;
	private String detalls;
	
}
