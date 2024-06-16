package pe.gob.munisantanita.MicroservicioNoticias.Noticias.Model;

import java.util.List;

public class Resultado {

	private List<Noticia> noticias;
	private int total_resultados;
	private int total_paginas;
	private int pag_actual;
	
	public List<Noticia> getNoticias() {
		return noticias;
	}
	public void setNoticias(List<Noticia> noticias) {
		this.noticias = noticias;
	}

	public int getPag_actual() {
		return pag_actual;
	}
	public void setPag_actual(int pag_actual) {
		this.pag_actual = pag_actual;
	}
	public int getTotal_resultados() {
		return total_resultados;
	}
	public void setTotal_resultados(int total_resultados) {
		this.total_resultados = total_resultados;
	}
	public int getTotal_paginas() {
		return total_paginas;
	}
	public void setTotal_paginas(int total_paginas) {
		this.total_paginas = total_paginas;
	}


}
