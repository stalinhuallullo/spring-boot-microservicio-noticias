package pe.gob.munisantanita.MicroservicioNoticias.Galeria.Service;

import pe.gob.munisantanita.MicroservicioNoticias.Galeria.Model.Imagen;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Mensaje;

public interface GaleriaService {

	Imagen crearImagen(Imagen imagen);
	Mensaje eliminarImagen(long id);

}
