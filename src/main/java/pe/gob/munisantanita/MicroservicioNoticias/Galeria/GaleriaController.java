package pe.gob.munisantanita.MicroservicioNoticias.Galeria;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.munisantanita.MicroservicioNoticias.Galeria.Model.Imagen;
import pe.gob.munisantanita.MicroservicioNoticias.Galeria.Service.GaleriaService;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Mensaje;


@RestController
@RequestMapping("/galeria")
public class GaleriaController {

	@Autowired
	GaleriaService galeriaService;

	
	@PostMapping
	@ResponseBody
	public Imagen post(@RequestBody Imagen imagen){	
		System.out.println("postttt------------->");
		return galeriaService.crearImagen(imagen);
	}
	
	/*
	@DeleteMapping("/{id}")
	@ResponseBody
	public Mensaje delete(@RequestParam(value="id", required = true) long id){	
		
		System.out.println("delete------------->"+id);
		return new Mensaje(); //galeriaService.eliminarImagen(id);
		
	}	
	*/
	@DeleteMapping("/{id}")
	@ResponseBody
	public Mensaje delete(@PathVariable(value="id", required = true) long id){	
		//@RequestParam(value="id", required = true) long id
		
		System.out.println("delete2------------->"+id);
		return galeriaService.eliminarImagen(id);
		
	}	
	

}
