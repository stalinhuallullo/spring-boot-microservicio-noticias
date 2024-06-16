package pe.gob.munisantanita.MicroservicioNoticias.Categorias.Model;

import java.util.List;

public class CategoriaResponse {

	private int draw;
	private int iTotalDisplayRecords;
	private int iTotalRecords;
	
	private List<Categoria> aaData;

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public List<Categoria> getAaData() {
		return aaData;
	}

	public void setAaData(List<Categoria> aaData) {
		this.aaData = aaData;
	}
	

	
	
}
