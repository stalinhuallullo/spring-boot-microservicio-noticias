package pe.gob.munisantanita.MicroservicioNoticias.Galeria.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.munisantanita.MicroservicioNoticias.Galeria.Model.Imagen;
import pe.gob.munisantanita.MicroservicioNoticias.Galeria.Repository.GaleriaRepository;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Mensaje;

@Service
public class GaleriaServiceImpl implements GaleriaService{

	@Autowired
	private GaleriaRepository galeriaRepository;
	
	@Override
	public Imagen crearImagen(Imagen imagen) {
		return galeriaRepository.crearImagen(imagen);
	}

	@Override
	public Mensaje eliminarImagen(long id) {
		return galeriaRepository.eliminarImagen(id);
	}

}
