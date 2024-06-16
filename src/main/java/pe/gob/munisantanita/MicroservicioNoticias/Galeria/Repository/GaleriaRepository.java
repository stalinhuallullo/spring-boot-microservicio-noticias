package pe.gob.munisantanita.MicroservicioNoticias.Galeria.Repository;

import pe.gob.munisantanita.MicroservicioNoticias.Galeria.Model.Imagen;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Mensaje;

public interface GaleriaRepository {

	Imagen crearImagen(Imagen imagen);

	Mensaje eliminarImagen(long id);

}
