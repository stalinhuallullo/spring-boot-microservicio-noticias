package pe.gob.munisantanita.MicroservicioNoticias.Categorias.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;

import pe.gob.munisantanita.MicroservicioNoticias.Categorias.Model.Categoria;
import pe.gob.munisantanita.MicroservicioNoticias.Categorias.Model.CategoriaResponse;
import pe.gob.munisantanita.MicroservicioNoticias.Galeria.Model.Imagen;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Mensaje;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.Noticia;
import pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model.ResponseNoticia;

@Repository
public class CategoriaRepositoryImpl implements CategoriaRepository {
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Categoria> all() {
		 String sql = "select * from noticias.categorias where estado = 1";
		 List<Categoria> categorias = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Categoria>(Categoria.class));
		 return categorias;
	}


	public CategoriaResponse filter(HttpServletRequest request, Model model, @RequestBody String json) {

	
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
			

	    	SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("noticias").withProcedureName("SP_CATEGORIAS_LISTAR");
	    	
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

	        
			CategoriaResponse r = new CategoriaResponse();
			r.setDraw(draw);
			r.setiTotalDisplayRecords(totalRegistro);
			r.setiTotalRecords(totalRegistro);

			
			List<Categoria> aaData = new ArrayList<>();
	        for(int i = 0; i < arrayList.size(); i++) {
	        	Map<String, Object> rs = (Map<String, Object>) arrayList.get(i);
	    		Categoria c = new Categoria();

	    		c.setId(Long.parseLong(rs.get("id").toString()));
	    		c.setNombre(rs.get("nombre") != null ? rs.get("nombre").toString() : "");
	    		c.setSlug(rs.get("slug") != null ? rs.get("slug").toString() : "");
	    		c.setFecha(rs.get("fecha") != null ? rs.get("fecha").toString() : "");
	    		c.setEstado(Integer.parseInt(rs.get("estado").toString()));
	    		c.setUsuario(rs.get("usuario") != null ? rs.get("usuario").toString() : "");
	    		
	    		aaData.add(c);
	        }
	        
	        r.setAaData(aaData);
	        return r;

			
		}catch (JSONException err){
			System.out.println("Error"+ err.toString());
		}
		
		
		return null;
	}


	@Override
	public Mensaje crearCategoria(Categoria categoria) {
    	SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("noticias").withProcedureName("SP_CATEGORIAS_CREAR");
    	
		Map<String, Object> inParamMap = new HashMap<String, Object>();

		inParamMap.put("nombre", categoria.getNombre());
		inParamMap.put("slug", categoria.getSlug());
		inParamMap.put("usuario_id", categoria.getUsuario_id());

		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		
		Mensaje m = new Mensaje();
		
		if(simpleJdbcCallResult.get("sp_error") == null) {
			m.setMensaje(simpleJdbcCallResult.get("sp_aviso").toString());
			m.setEstado("OK");
		} else {
			m.setMensaje(simpleJdbcCallResult.get("sp_error").toString());
			m.setEstado("ERR");
		}
		
		return m;
	}


	@Override
	public Categoria findId(long id) {
		
		return jdbcTemplate.queryForObject("select C.*, U.cuenta usuario from noticias.categorias C inner join usuarios.usuarios U on U.id = C.usuario_id where C.id = ? ", BeanPropertyRowMapper.newInstance(Categoria.class), id);

	}

	@Override
	public Categoria findSlug(String categoria) {
		
		return jdbcTemplate.queryForObject("select C.*, U.cuenta usuario from noticias.categorias C inner join usuarios.usuarios U on U.id = C.usuario_id where C.slug = ? ", BeanPropertyRowMapper.newInstance(Categoria.class), categoria);

	}


	@Override
	public Mensaje editarCategoria(Categoria categoria) {
    	SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("noticias").withProcedureName("SP_CATEGORIAS_EDITAR");
    	
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		
		inParamMap.put("id", categoria.getId());
		inParamMap.put("nombre", categoria.getNombre());
		inParamMap.put("slug", categoria.getSlug());

		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		
		Mensaje m = new Mensaje();
		
		if(simpleJdbcCallResult.get("sp_error") == null) {
			m.setMensaje(simpleJdbcCallResult.get("sp_aviso").toString());
			m.setEstado("OK");
		} else {
			m.setMensaje(simpleJdbcCallResult.get("sp_error").toString());
			m.setEstado("ERR");
		}
		
		return m;
		
	}

	@Override
	public Mensaje eliminarCategoria(long id) {
    	SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("noticias").withProcedureName("SP_CATEGORIAS_ELIMINAR");
    	
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		
		inParamMap.put("id", id);

		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		
		Mensaje m = new Mensaje();
		
		if(simpleJdbcCallResult.get("sp_error") == null) {
			m.setMensaje(simpleJdbcCallResult.get("sp_aviso").toString());
			m.setEstado("OK");
		} else {
			m.setMensaje(simpleJdbcCallResult.get("sp_error").toString());
			m.setEstado("ERR");
		}
		return m;
	}


}
