package pe.gob.munisantanita.MicroservicioNoticias.Categorias;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.munisantanita.MicroservicioNoticias.Categorias.Model.Categoria;
import pe.gob.munisantanita.MicroservicioNoticias.Categorias.Model.CategoriaResponse;
import pe.gob.munisantanita.MicroservicioNoticias.Categorias.Service.CategoriaService;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Mensaje;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Noticia;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.ResponseNoticia;

@RestController
@RequestMapping("/categoria")
public class CategoriasController {
	
	@Autowired
	CategoriaService categoriaService;
	
	@GetMapping
	@ResponseBody
	public List<Categoria> all(){		
		return categoriaService.all();
	}

	
	@GetMapping(value="/{id}")
	@ResponseBody
	public Categoria findId(@PathVariable(name = "id") long id) {	
		return categoriaService.findId(id);
	}
	
	@GetMapping(value="/slug/{categoria}")
	@ResponseBody
	public Categoria findSlug(@PathVariable(name = "categoria") String categoria) {	
		return categoriaService.findSlug(categoria);
	}
	
	@PostMapping(value="/filter")
	@ResponseBody
	public CategoriaResponse filter(HttpServletRequest request, Model model, @RequestBody String json) {	
		return categoriaService.filter(request, model, json);
	}
	
	
	@PostMapping
	@ResponseBody
	public Mensaje crearCategoria(@RequestBody Categoria categoria) {	
		return categoriaService.crearCategoria(categoria);
	}
	
	@PutMapping
	@ResponseBody
	public Mensaje editarCategoria(@RequestBody Categoria categoria) {	
		return categoriaService.editarCategoria(categoria);
	}
	
	
	@DeleteMapping(value="/{id}")
	@ResponseBody
	public Mensaje eliminarCategoria(@PathVariable(name = "id") long id) {	
		return categoriaService.eliminarCategoria(id);
	}
	
}
