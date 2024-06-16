package pe.gob.munisantanita.MicroservicioNoticias.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(value = {ApiException.class})
	public ResponseEntity<Object> handlerApiException(ApiException e){
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		
		ApiError apiError = new ApiError(
				e.getMessage(),
				e,
				badRequest
				);
		
		return new ResponseEntity<>(apiError, badRequest);
	}
	
	
}
