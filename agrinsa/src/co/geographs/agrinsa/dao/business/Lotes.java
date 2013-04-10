package co.geographs.agrinsa.dao.business;

import java.sql.Date;

public class Lotes {
	private int loteid;
	private int usuarioid;
	private String agricultor;
	private String nomlote;
	private String semestre;
	private String fecsiembra;
	private String fecgerminacion;
	private String feccorte;
	private String siembra;
	private int numvisita;
	
	public int getLoteid() {
		return loteid;
	}
	public void setLoteid(int loteid) {
		this.loteid = loteid;
	}
	public int getUsuarioid() {
		return usuarioid;
	}
	public void setUsuarioid(int usuarioid) {
		this.usuarioid = usuarioid;
	}
	public String getAgricultor() {
		return agricultor;
	}
	public void setAgricultor(String agricultor) {
		this.agricultor = agricultor;
	}
	public String getNomlote() {
		return nomlote;
	}
	public void setNomlote(String nomlote) {
		this.nomlote = nomlote;
	}
	public String getSemestre() {
		return semestre;
	}
	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}
	public String getFecsiembra() {
		return fecsiembra;
	}
	public void setFecsiembra(String fecsiembra) {
		this.fecsiembra = fecsiembra;
	}
	public String getFecgerminacion() {
		return fecgerminacion;
	}
	public void setFecgerminacion(String fecgerminacion) {
		this.fecgerminacion = fecgerminacion;
	}
	public String getFeccorte() {
		return feccorte;
	}
	public void setFeccorte(String feccorte) {
		this.feccorte = feccorte;
	}
	public String getSiembra() {
		return siembra;
	}
	public void setSiembra(String siembra) {
		this.siembra = siembra;
	}
	public int getNumvisita() {
		return numvisita;
	}
	public void setNumvisita(int numvisita) {
		this.numvisita = numvisita;
	}
	
	
}
