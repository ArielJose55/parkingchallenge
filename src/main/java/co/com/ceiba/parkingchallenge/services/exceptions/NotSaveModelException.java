package co.com.ceiba.parkingchallenge.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NotSaveModelException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public NotSaveModelException(Class<?> model) {
		super("!Oouups! We could not save the " + model.getName());
	}
}
