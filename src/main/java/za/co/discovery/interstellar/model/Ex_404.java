package za.co.discovery.interstellar.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class Ex_404 extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public Ex_404(String message) {
		super(message);
	}
}
