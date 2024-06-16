package pe.gob.munisantanita.MicroservicioNoticias.Noticias.Repository;



import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.swing.tree.RowMapper;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;

import pe.gob.munisantanita.MicroservicioNoticias.Base.Utils.F;
import pe.gob.munisantanita.MicroservicioNoticias.Exception.ApiException;
import pe.gob.munisantanita.MicroservicioNoticias.Galeria.Model.Imagen;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Mensaje;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Noticia;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.ResponseNoticia;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Resultado;


@Repository
public class NoticiaRepositoryImpl implements NoticiaRepository{
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@Autowired
	 NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	public ResponseNoticia all(HttpServletRequest request, Model model, @RequestBody String json) {

		//int draw = Integer.parseInt(json.get("draw"));
		

		
		try {
			JSONObject obj = new JSONObject(json);
			
			int draw = obj.getInt("draw");
			int row =  obj.getInt("start");
			int rowperpage = obj.getInt("length"); // Números de filas por pag.
			
			JSONArray arrOrden = obj.getJSONArray("order");
			JSONObject arrOrden_ = arrOrden.getJSONObject(0);
			int columnIndex = arrOrden_.getInt("column"); // Columna index

			JSONArray arrColumnas = obj.getJSONArray("columns");
			JSONObject arrColumnas_ = arrColumnas.getJSONObject(columnIndex);
			String columnName = arrColumnas_.getString("data"); // Columna nombre
			
			JSONArray arrColumnSortOrder = obj.getJSONArray("order");
			JSONObject arrColumnSortOrder_ = arrColumnSortOrder.getJSONObject(0);
			String columnSortOrder = arrColumnSortOrder_.getString("dir"); // Columna nombre
			
			JSONObject searchObj = obj.getJSONObject("search");
			String searchValue = searchObj.getString("value"); // Search value
			
			


	    	SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("noticias").withProcedureName("SP_NOTICIAS_LISTAR");
	    	
			Map<String, Object> inParamMap = new HashMap<String, Object>();

			inParamMap.put("desde", row);
			inParamMap.put("hasta", row + rowperpage);
			inParamMap.put("orderby", columnName +" "+columnSortOrder);
			inParamMap.put("buscar", searchValue);
			
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);

			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
			

			ArrayList<Object> arrayList = (ArrayList) simpleJdbcCallResult.get("#result-set-1");
	       
	        ArrayList<Object> arrayTotal = (ArrayList) simpleJdbcCallResult.get("#result-set-2");
	        Map<String, Object> total = (Map) arrayTotal.get(0);
	        
	        int totalRegistro = Integer.parseInt(total.get("total").toString());
	        
	        System.out.println(arrayTotal);
	        
			ResponseNoticia r = new ResponseNoticia();
			r.setDraw(draw);
			r.setiTotalDisplayRecords(totalRegistro);
			r.setiTotalRecords(totalRegistro);

			
			List<Noticia> aaData = new ArrayList<>();
	        for(int i = 0; i < arrayList.size(); i++) {
	        	Map<String, Object> rs = (Map<String, Object>) arrayList.get(i);
	    		Noticia n = new Noticia();

	    		n.setId(Long.parseLong(rs.get("id").toString()));
	    		n.setTitulo(rs.get("titulo") != null ? rs.get("titulo").toString() : "");
	    		n.setSubtitulo(rs.get("subtitulo") != null ? rs.get("subtitulo").toString() : "");
	    		n.setCategoria(rs.get("categoria_nombre").toString() ); //columns.get("columns[0][data]")
	    		n.setFecha( rs.get("fecha") != null ? rs.get("fecha").toString() : "");
	    		n.setImagen(rs.get("imagen") != null ? rs.get("imagen").toString() : "");
	    		aaData.add(n);
	        }
	        r.setAaData(aaData);
	        return r;

		}catch (JSONException err){
			System.out.println("Error"+ err.toString());
		}
		
		
		return null;

	}


	
	public Mensaje crearNoticia(Noticia noticia) {
		
		Mensaje m = new Mensaje();
		
		if(noticia != null) {
    	SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("noticias").withProcedureName("SP_NOTICIAS_CREAR");
    	
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		
		
	    StringBuilder XML = new StringBuilder();
	    if(noticia.getImagenes() != null)
	    noticia.getImagenes().stream().forEach((i)-> {
				XML.append("<fila><archivo_url>"+i.getArchivo_url()+"</archivo_url><token>"+i.getToken()+"</token></fila>");
		});
	    
		inParamMap.put("titulo", noticia.getTitulo());
		inParamMap.put("slug", noticia.getSlug());
		inParamMap.put("subtitulo", noticia.getSubtitulo());
		inParamMap.put("resumen", noticia.getResumen());
		inParamMap.put("contenido", noticia.getContenido());
		inParamMap.put("url_youtube", noticia.getUrl_youtube());
		inParamMap.put("categoria_id", noticia.getCategoria_id());
		inParamMap.put("usuario_id", noticia.getUsuario_id());
		inParamMap.put("portada_token", noticia.getPortada_token());
		inParamMap.put("XML", XML);
		
		
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		

		if(simpleJdbcCallResult.get("sp_error") == null) {
			
			m.setMensaje(simpleJdbcCallResult.get("sp_aviso").toString());
			m.setId(Long.parseLong(simpleJdbcCallResult.get("sp_id").toString()));
			m.setEstado("OK");
			
		} else {
			m.setMensaje(simpleJdbcCallResult.get("sp_error").toString());
			m.setEstado("ERR");
		}
		
		}
		return m;
	


		
		
		
		/*
		noticia.setFecha(F.getTimeStamp());

		String query = "INSERT INTO noticias.noticias (titulo, subtitulo, resumen, contenido, url_youtube, fecha, categoria_id, fotos_portada_id, estado) VALUES (:titulo, :subtitulo, :resumen, :contenido, :url_youtube, :fecha, :categoria_id, 0, 1) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		BeanPropertySqlParameterSource parametros = new BeanPropertySqlParameterSource(noticia);
		parametros.registerSqlType("fecha", Types.TIMESTAMP); 
		parametros.registerSqlType("categoria_id", Types.INTEGER); 
		namedParameterJdbcTemplate.update(query, parametros, keyHolder);
		
		Mensaje m = new Mensaje();
		m.setId(keyHolder.getKey().intValue());
			    
		return m;
		*/
		
		
	}

	public Mensaje editarNoticia(Noticia noticia) {
		
    	SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("noticias").withProcedureName("SP_NOTICIAS_EDITAR");
    	
		Map<String, Object> inParamMap = new HashMap<String, Object>();

		
	    StringBuilder XML = new StringBuilder();
	    noticia.getImagenes().stream().forEach((i)-> {
				XML.append("<fila><archivo_url>"+i.getArchivo_url()+"</archivo_url><token>"+i.getToken()+"</token></fila>");
		});
		
		inParamMap.put("id", noticia.getId());
		inParamMap.put("titulo", noticia.getTitulo());
		inParamMap.put("slug", noticia.getSlug());
		inParamMap.put("subtitulo", noticia.getSubtitulo());
		inParamMap.put("resumen", noticia.getResumen());
		inParamMap.put("contenido", noticia.getContenido());
		inParamMap.put("url_youtube", noticia.getUrl_youtube());
		inParamMap.put("categoria_id", noticia.getCategoria_id());
		inParamMap.put("portada_token", noticia.getPortada_token());
		inParamMap.put("XML", XML);
		
		
		
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		Mensaje m = new Mensaje();
		
		if(simpleJdbcCallResult.get("sp_error") == null) {
			m.setMensaje(simpleJdbcCallResult.get("sp_aviso").toString());
			m.setId(noticia.getId());
			m.setEstado("OK");
			
		} else {
			m.setMensaje(simpleJdbcCallResult.get("sp_error").toString());
			m.setEstado("ERR");
		}
		
		return m;
		
		/*
		String query = "update noticias.noticias set titulo=:titulo, subtitulo=:subtitulo, resumen=:resumen, contenido=:contenido, url_youtube=:url_youtube, categoria_id=:categoria_id, fotos_portada_id=:fotos_portada_id where id=:id";

		BeanPropertySqlParameterSource parametros = new BeanPropertySqlParameterSource(noticia);
		//KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(query, parametros);
		
		
		for (long[] elm : noticia.getOrden_imagenes()) {
			      SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("orden", elm[0]).addValue("id", elm[1]);
			      namedParameterJdbcTemplate.update("update noticias.fotos set orden = :orden where id = :id", namedParameters); 
		}
		
		Mensaje m = new Mensaje();
		m.setId(noticia.getId());
		m.setMensaje("Se ha editado con éxito!");

		return m;
		*/
		
	}


	@Override
	public Noticia findId(int id) {
		
		   Noticia noti = jdbcTemplate.queryForObject("select N.fecha, N.id, N.titulo, N.subtitulo, N.resumen, N.contenido, N.slug, N.url_youtube, N.categoria_id, N.usuario_id, CASE  WHEN N.fotos_portada_id is NULL THEN 0 ELSE N.fotos_portada_id END AS fotos_portada_id, F.archivo_url as portada_archivo from noticias.noticias N left join noticias.fotos F on F.id = N.fotos_portada_id where N.id = ? ", BeanPropertyRowMapper.newInstance(Noticia.class), id);
			
		   List<Imagen> img = jdbcTemplate.query("select * from noticias.fotos where noticia_id = ? order by orden asc", BeanPropertyRowMapper.newInstance(Imagen.class), id);
		   noti.setImagenes(img);
		   
		   return noti;

	}


	@Override
	public Noticia findSlug(String slug) {
		

		   Noticia noti = jdbcTemplate.queryForObject("select top 1 N.fecha, N.id, N.titulo, N.subtitulo, N.resumen, N.contenido, N.slug, N.url_youtube, N.categoria_id, N.usuario_id, CASE  WHEN N.fotos_portada_id is NULL THEN 0 ELSE N.fotos_portada_id END AS fotos_portada_id, C.nombre as categoria, C.slug as categoria_slug from noticias.noticias N inner join noticias.categorias C on C.id = N.categoria_id  where N.slug = ? ", BeanPropertyRowMapper.newInstance(Noticia.class), slug);

			String sql = "select N.*, (select TOP 1 f.archivo_url from noticias.fotos F where F.id = N.fotos_portada_id) as imagen, (select TOP 1 f.archivo_url from noticias.fotos F where F.noticia_id = N.id) as imagen2 from (SELECT X.*, C.nombre categoria, C.slug slugcategoria, ROW_NUMBER() OVER (ORDER BY X.id desc) AS fila FROM noticias.noticias X inner join noticias.categorias C on C.id = X.categoria_id where X.estado = 1) N where N.fila >= ? and N.fila <= ?  order by N.fecha desc";

		   List<Imagen> img = jdbcTemplate.query("select * from noticias.fotos where noticia_id = ? order by orden asc", BeanPropertyRowMapper.newInstance(Imagen.class), noti.getId());
		   noti.setImagenes(img);
		   
		   return noti;
	}
		 
		 
	
	@Override
	public Mensaje eliminarNoticia(long id){
		  Mensaje m = new Mensaje();
	      SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
	      int status = namedParameterJdbcTemplate.update("update noticias.noticias set estado = 0 where id = :id", namedParameters); 
	      if(status != 0){
	    	  m.setMensaje("Se ha elimiando correctamente la noticia");
	    	  m.setId(id);
	      }
	      
		return m;
	}




	@Override
	public Resultado findByCategory(String categoria, int pag, int mostrar) {

		 int resultado_pag = mostrar;
			
		 int total = jdbcTemplate.queryForObject("SELECT count(1) FROM noticias.noticias X inner join noticias.categorias C on C.id = X.categoria_id where C.slug = ? and X.estado = 1", new Object[] { categoria }, Integer.class);
		 		 
		 int desde = (resultado_pag * pag) - resultado_pag + 1;
		 int hasta = (resultado_pag * pag);
		 
		 String sql = "select N.fecha, N.*, (select TOP 1 f.archivo_url from noticias.fotos F where F.id = N.fotos_portada_id) as imagen, (select TOP 1 f.archivo_url from noticias.fotos F where F.noticia_id = N.id) as imagen2 from (SELECT X.*, C.nombre categoria, C.slug slugcategoria, ROW_NUMBER() OVER (ORDER BY X.id desc) AS fila FROM noticias.noticias X inner join noticias.categorias C on C.id = X.categoria_id where X.estado = 1 and c.slug = ?) N where N.fila >= ? and N.fila <= ?  order by N.fecha desc";

        return jdbcTemplate.query(sql, new Object[]{categoria, desde, hasta}, (rs) ->{ 
        	Resultado res = new Resultado();
        	
    		List<Noticia> noticias = new ArrayList<>();
            while(rs.next()){
            	Noticia not = new Noticia();
            	not.setId(rs.getLong("id"));
              	not.setCategoria_id(rs.getInt("categoria_id"));
              	not.setCategoria(rs.getString("categoria"));
              	not.setCategoria_slug(rs.getString("slugcategoria"));
            	not.setTitulo(rs.getString("titulo"));
            	not.setSubtitulo(rs.getString("subtitulo"));
            	not.setResumen(rs.getString("resumen"));
            	not.setSlug(rs.getString("slug"));
            	not.setFecha(rs.getString("fecha"));
            	not.setImagen(rs.getString("imagen") != null ? rs.getString("imagen") : rs.getString("imagen2"));
            	noticias.add(not);
            }
        	res.setNoticias(noticias);
        	res.setTotal_paginas((int)Math.ceil((double)total/(double)resultado_pag));//(int)Math.ceil(total/resultado_pag)
        	res.setPag_actual(pag);
          	res.setTotal_resultados(total);
            return res;

        } ); 
		 
		 
		 
		 
		 
		 
	}



	@Override
	public Resultado findByAll(int pag, int mostrar) {

		
		 int resultado_pag = mostrar;
			
		 int total = jdbcTemplate.queryForObject("SELECT count(1) FROM noticias.noticias X inner join noticias.categorias C on C.id = X.categoria_id where X.estado = 1", null, Integer.class);
		 		 
		 int desde = (resultado_pag * pag) - resultado_pag + 1;
		 int hasta = (resultado_pag * pag);
		 
		 String sql = "select  N.*, (select TOP 1 f.archivo_url from noticias.fotos F where F.id = N.fotos_portada_id) as imagen, (select TOP 1 f.archivo_url from noticias.fotos F where F.noticia_id = N.id) as imagen2 from (SELECT X.*, C.nombre categoria, C.slug slugcategoria, ROW_NUMBER() OVER (ORDER BY X.id desc) AS fila FROM noticias.noticias X inner join noticias.categorias C on C.id = X.categoria_id where X.estado = 1) N where N.fila >= ? and N.fila <= ?  order by N.fecha desc";

      return jdbcTemplate.query(sql, new Object[]{desde, hasta}, (rs) ->{ 
      	Resultado res = new Resultado();
      	
  		List<Noticia> noticias = new ArrayList<>();
          while(rs.next()){
          	Noticia not = new Noticia();
          	not.setId(rs.getLong("id"));
          	not.setCategoria_id(rs.getInt("categoria_id"));
          	not.setCategoria(rs.getString("categoria"));
          	not.setCategoria_slug(rs.getString("slugcategoria"));
          	not.setTitulo(rs.getString("titulo"));
          	not.setSubtitulo(rs.getString("subtitulo"));
          	not.setResumen(rs.getString("resumen"));
          	not.setSlug(rs.getString("slug"));
          	not.setFecha(rs.getString("fecha"));
          	not.setImagen(rs.getString("imagen") != null ? rs.getString("imagen") : rs.getString("imagen2"));
          	noticias.add(not);
          }
      	res.setNoticias(noticias);
      	res.setTotal_paginas((int)Math.ceil((double)total/(double)resultado_pag));
      	res.setPag_actual(pag);
      	res.setTotal_resultados(total);
          return res;

      } ); 
		
		
		
		
	}






}
