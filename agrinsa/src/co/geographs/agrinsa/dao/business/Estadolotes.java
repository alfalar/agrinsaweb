package co.geographs.agrinsa.dao.business;

import java.io.Serializable;

public class Estadolotes implements Serializable {
	private int activos;
	private int inactivos;
	public int getActivos() {
		return activos;
	}
	public void setActivos(int activos) {
		this.activos = activos;
	}
	public int getInactivos() {
		return inactivos;
	}
	public void setInactivos(int inactivos) {
		this.inactivos = inactivos;
	}
	
	
}
