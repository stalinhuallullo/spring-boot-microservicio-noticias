package pe.gob.munisantanita.MicroservicioNoticias.Noticias;



import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Mensaje;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Noticia;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.ResponseNoticia;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Resultado;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Service.NoticiaService;



@RestController
@RequestMapping("/noticia")
public class NoticiasController {
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	
	@Autowired
	private NoticiaService noticiaService;

	
	@GetMapping
	@ResponseBody
	public Resultado findByAll(@RequestParam(value = "pag", required=false) Integer pag, @RequestParam(value = "mostrar", required=false) Integer mostrar) {
		return noticiaService.findByAll(pag != null ? (int)pag : 1, mostrar != null ? (int)mostrar : 10);
	}
	
	
	@GetMapping(value="/slug/{slug}")
	@ResponseBody
	public Noticia findSlug(@PathVariable(name = "slug") String slug) {
		return noticiaService.findSlug(slug);
	}
	
	
	@GetMapping(value="/{id}")
	@ResponseBody
	public Noticia findId(@PathVariable(name = "id") int id) {
		return noticiaService.findId(id);
	}	
	

	
	/*
	 * @POST /
	 * Filtrar noticias
	 * */
	@PostMapping(value="/all")
	@ResponseBody
	public ResponseNoticia postNoticias(HttpServletRequest request, Model model, @RequestBody String json) {
		return noticiaService.all(request, model, json);
	}
	
	
	@GetMapping(value="/categoria/{categoria}")
	@ResponseBody
	public Resultado findByCategory(@PathVariable(name = "categoria") String categoria, @RequestParam(value = "pag", required=false) Integer pag, @RequestParam(value = "mostrar", required=false) Integer mostrar) {
		
		return noticiaService.findByCategory(categoria, pag != null ? (int)pag : 1, mostrar != null ? (int)mostrar : 10);
	}
	
	
	
	/*
	 * @POST /
	 * Crear un nuevo registro de noticias
	 * */
	@PostMapping
	@ResponseBody
	public Mensaje crearNoticia(@RequestBody Noticia noticia){	
		
		return noticiaService.crearNoticia(noticia);
	}


	/*
	 * @PUT /
	 * Editar un determinado registro de noticias
	 * */
	@PutMapping
	@ResponseBody
	public Mensaje editarNoticia(@RequestBody Noticia noticia){		
		return noticiaService.editarNoticia(noticia);
	}
	

	/*
	 * @DELETE /
	 * Eliminar un determinado registro de noticias
	 * */
	@DeleteMapping("/{id}")
	@ResponseBody
	public Mensaje eliminarNoticia(@PathVariable(value="id", required = true) long id){		
		
	
		return noticiaService.eliminarNoticia(id);
	}
	
	
	/*
	@PostMapping(value="imagen")
	@ResponseBody
	public Mensaje subirImagen(HttpServletRequest request, Model model, @RequestParam("file") ArrayList<MultipartFile> files) throws IOException {		

		return noticiaService.crearNoticia(noticia);
	}
	*/
	
	


}
