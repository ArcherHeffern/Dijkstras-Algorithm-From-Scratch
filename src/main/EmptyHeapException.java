package main;

/**
 * Exception to be throw when trying to remove element from an empty heap
 * @author Archer Heffern
 * @gmail hefferna@brandeis.edu
 */

public class EmptyHeapException extends RuntimeException {

	private static final long serialVersionUID = 2129172102123053924L;

	/**
	 * Constructs new EmptyHeapException
	 */
	public EmptyHeapException() {
		super();
	}
	
	/**
	 * Constructs new EmptyHeapException with custom message
	 * @param errorMessage Custom error message
	 */
	public EmptyHeapException(String errorMessage) {
		super(errorMessage);
	}
}
