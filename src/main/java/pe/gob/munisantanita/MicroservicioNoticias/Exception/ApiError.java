package pe.gob.munisantanita.MicroservicioNoticias.Exception;

import org.springframework.http.HttpStatus;

public class ApiError {

	private final String mensaje;
	private final Throwable throwable;
	private final HttpStatus estado;
	
	
	public ApiError(String mensaje, Throwable throwable, HttpStatus estado) {
		super();
		this.mensaje = mensaje;
		this.throwable = throwable;
		this.estado = estado;
	}
	public String getMensaje() {
		return mensaje;
	}
	public Throwable getThrowable() {
		return throwable;
	}
	public HttpStatus getEstado() {
		return estado;
	}
	
	
	
}