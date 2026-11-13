package fr.esgi.fx.travel.exception;

public class CompagnieInexistanteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CompagnieInexistanteException(String message) {
		super(message);
	}
}