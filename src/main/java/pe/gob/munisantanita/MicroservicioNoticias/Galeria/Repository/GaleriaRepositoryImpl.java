package pe.gob.munisantanita.MicroservicioNoticias.Galeria.Repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import pe.gob.munisantanita.MicroservicioNoticias.Galeria.Model.Imagen;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Mensaje;

@Repository
public class GaleriaRepositoryImpl implements GaleriaRepository{
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@Autowired
	 NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	

	public Imagen crearImagen(Imagen imagen) {

		String query = "INSERT INTO noticias.fotos (archivo_url, noticia_id) VALUES (:archivo_url, :noticia_id) ";
	
		KeyHolder keyHolder = new GeneratedKeyHolder();
		BeanPropertySqlParameterSource parametros = new BeanPropertySqlParameterSource(imagen);

		namedParameterJdbcTemplate.update(query, parametros, keyHolder);
		
		imagen.setId(keyHolder.getKey().intValue());

		return imagen;
		
	}



	@Override
	public Mensaje eliminarImagen(long id) {
		
			Mensaje m = new Mensaje();
	
	      SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
	      int status = namedParameterJdbcTemplate.update("delete from noticias.fotos where id = :id", namedParameters); 
	      if(status != 0){
	    	  m.setCodigo(200);
	    	  m.setMensaje("Se ha eliminado correctamente la imagen con ID: "+id);
	    	  m.setId(id);
	      }else{
	    	  m.setCodigo(500);
	    	  m.setMensaje("Ha ocurrido un error al eliminar la imagen.");
	      }
	        
		
		return m;
	}

}
