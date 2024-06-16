package pe.gob.munisantanita.MicroservicioNoticias.Noticias.Service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;

import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Mensaje;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Noticia;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.ResponseNoticia;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Resultado;


public interface NoticiaService {
	
	public Mensaje editarNoticia(Noticia noticia);
	public Mensaje crearNoticia(Noticia noticia);
	public ResponseNoticia all(HttpServletRequest request, Model model, @RequestBody String json);
	public Noticia findId(int id);
	public Mensaje eliminarNoticia(long id);
	public Resultado findByCategory(String categoria, int pag, int mostrar);
	public Resultado findByAll(int pag, int mostrar);
	public Noticia findSlug(String slug);

	
	
}
