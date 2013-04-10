package co.geographs.agrinsa.dao.business;

import java.util.ArrayList;

public class NuevosLotes {
	private int lotenuevoid;
	private int califilote;
	private int usuarioid;
	private ArrayList<Coordenadas> coordenadas;

	public int getLotenuevoid() {
		return lotenuevoid;
	}

	public void setLotenuevoid(int lotenuevoid) {
		this.lotenuevoid = lotenuevoid;
	}

	public int getCalifilote() {
		return califilote;
	}

	public void setCalifilote(int califilote) {
		this.califilote = califilote;
	}

	public int getUsuarioid() {
		return usuarioid;
	}

	public void setUsuarioid(int usuarioid) {
		this.usuarioid = usuarioid;
	}

	public ArrayList<Coordenadas> getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(ArrayList<Coordenadas> coordenadas) {
		this.coordenadas = coordenadas;
	}

}
