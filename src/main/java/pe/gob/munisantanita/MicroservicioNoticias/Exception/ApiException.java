package pe.gob.munisantanita.MicroservicioNoticias.Exception;

public class ApiException extends RuntimeException{
	
    public ApiException(String message){
        super(message);
    }
	
    public ApiException(String message, Throwable cause){
        super(message, cause);
    }
}
