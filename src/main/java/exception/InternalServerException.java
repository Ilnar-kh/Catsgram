package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Бросается, если что-то пошло не так на стороне сервера при работе с БД
 */
public class InternalServerException extends ResponseStatusException {
    public InternalServerException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }
}