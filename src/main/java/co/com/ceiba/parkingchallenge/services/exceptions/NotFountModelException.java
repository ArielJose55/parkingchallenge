package co.com.ceiba.parkingchallenge.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * 
 * @author ariel.arnedo
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public final class NotFountModelException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public NotFountModelException(Class<?> model) {
		super("The " + model.getName() + " with that attributes could be found");
	}
	
}
