package pe.gob.munisantanita.MicroservicioNoticias.Categorias.Service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.gob.munisantanita.MicroservicioNoticias.Categorias.Model.Categoria;
import pe.gob.munisantanita.MicroservicioNoticias.Categorias.Model.CategoriaResponse;
import pe.gob.munisantanita.MicroservicioNoticias.Categorias.Repository.CategoriaRepository;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Mensaje;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.ResponseNoticia;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Override
	public List<Categoria> all() {
		return categoriaRepository.all();
	}

	@Override
	public CategoriaResponse filter(HttpServletRequest request, Model model, String json) {
		return categoriaRepository.filter(request, model, json);
	}

	@Override
	public Mensaje crearCategoria(Categoria categoria) {
		return categoriaRepository.crearCategoria(categoria);
	}

	@Override
	public Categoria findId(long id) {
		return categoriaRepository.findId(id);
	}

	@Override
	public Mensaje editarCategoria(Categoria categoria) {

		return categoriaRepository.editarCategoria(categoria);
	}

	@Override
	public Mensaje eliminarCategoria(long id) {
		return categoriaRepository.eliminarCategoria(id);
	}

	@Override
	public Categoria findSlug(String categoria) {
		return categoriaRepository.findSlug(categoria);
	}

	
}
