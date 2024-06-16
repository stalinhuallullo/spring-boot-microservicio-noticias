package pe.gob.munisantanita.MicroservicioNoticias.Noticias.Service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;

import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Mensaje;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Noticia;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.ResponseNoticia;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Resultado;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Repository.NoticiaRepository;

@Service
public class NoticiaServiceImpl implements NoticiaService {
	
	@Autowired
	NoticiaRepository noticiaRepository;


	public ResponseNoticia all(HttpServletRequest request, Model model, @RequestBody String json) {
		return noticiaRepository.all(request, model, json);
	}


	public Mensaje crearNoticia(Noticia noticia) {
		return noticiaRepository.crearNoticia(noticia);
	}
	 

	public Mensaje editarNoticia(Noticia noticia) {
		return noticiaRepository.editarNoticia(noticia);
	}


	@Override
	public Noticia findId(int id) {
		return noticiaRepository.findId(id);
	}


	@Override
	public Mensaje eliminarNoticia(long id) {
		return noticiaRepository.eliminarNoticia(id);
	}


	@Override
	public Resultado findByCategory(String categoria, int pag, int mostrar) {
		return noticiaRepository.findByCategory(categoria, pag, mostrar);
	}


	@Override
	public Resultado findByAll(int pag, int mostrar) {
		return noticiaRepository.findByAll(pag, mostrar);

	}


	@Override
	public Noticia findSlug(String slug) {
		return noticiaRepository.findSlug(slug);
	}





}
