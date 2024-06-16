package pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model;

import java.util.List;

import pe.gob.munisantanita.MicroservicioNoticias.Galeria.Model.Imagen;

public class Noticia {
	
	private long id;
	private String titulo;
	private String subtitulo;
	private String resumen;
	private String contenido;
	private String slug;
	private String imagen;
	private String categoria;
	private int categoria_id;
	private String categoria_slug;
	private String fecha;
	private String url_youtube;
	private List<Imagen> imagenes;
	private long orden_imagenes[][];
	private long fotos_portada_id;
	private String portada_token;
	private String portada_archivo;
	private Integer estado;
	private Long usuario_id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getSubtitulo() {
		return subtitulo;
	}
	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}
	public String getResumen() {
		return resumen;
	}
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public String getUrl_youtube() {
		return url_youtube;
	}
	public void setUrl_youtube(String url_youtube) {
		this.url_youtube = url_youtube;
	}

	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public int getCategoria_id() {
		return categoria_id;
	}
	public void setCategoria_id(int categoria_id) {
		this.categoria_id = categoria_id;
	}
	public List<Imagen> getImagenes() {
		return imagenes;
	}
	public void setImagenes(List<Imagen> imagenes) {
		this.imagenes = imagenes;
	}
	public long[][] getOrden_imagenes() {
		return orden_imagenes;
	}
	public void setOrden_imagenes(long orden_imagenes[][]) {
		this.orden_imagenes = orden_imagenes;
	}
	public long getFotos_portada_id() {
		return fotos_portada_id;
	}
	public void setFotos_portada_id(long fotos_portada_id) {
		this.fotos_portada_id = fotos_portada_id;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getCategoria_slug() {
		return categoria_slug;
	}
	public void setCategoria_slug(String categoria_slug) {
		this.categoria_slug = categoria_slug;
	}
	public Long getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(Long usuario_id) {
		this.usuario_id = usuario_id;
	}
	public String getPortada_token() {
		return portada_token;
	}
	public void setPortada_token(String portada_token) {
		this.portada_token = portada_token;
	}
	public String getPortada_archivo() {
		return portada_archivo;
	}
	public void setPortada_archivo(String portada_archivo) {
		this.portada_archivo = portada_archivo;
	}

	
	

}
