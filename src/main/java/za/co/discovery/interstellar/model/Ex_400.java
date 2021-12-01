package za.co.discovery.interstellar.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class Ex_400 extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public Ex_400(String message) {
        super(message);
    }
}
