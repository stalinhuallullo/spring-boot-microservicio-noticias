package pe.gob.munisantanita.MicroservicioNoticias.Categorias.Service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import pe.gob.munisantanita.MicroservicioNoticias.Categorias.Model.Categoria;
import pe.gob.munisantanita.MicroservicioNoticias.Categorias.Model.CategoriaResponse;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Mensaje;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Noticia;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.ResponseNoticia;

public interface CategoriaService {
	
	public List<Categoria> all();

	public CategoriaResponse filter(HttpServletRequest request, Model model, String json);

	public Mensaje crearCategoria(Categoria categoria);
	public Mensaje editarCategoria(Categoria categoria);

	public Categoria findId(long id);

	public Mensaje eliminarCategoria(long id);

	public Categoria findSlug(String categoria);



	
}
