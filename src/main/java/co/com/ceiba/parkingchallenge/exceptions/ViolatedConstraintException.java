package co.com.ceiba.parkingchallenge.exceptions;

public class ViolatedConstraintException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ViolatedConstraintException(String message) {
		super(message);
	}
	
}
