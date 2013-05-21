package co.geographs.agrinsa.dao.business;

import java.io.Serializable;

public class Resumen implements Serializable {
	private String usuario;
	private String cedula;
	private String agricultor;
	private String sucursal;
	private String ciudad;
	private String vereda;	
	private String nomlote;
	private String tipocultivo;
	private String area;
	private String variedad;
	private String fecsiembra;
	private String feccorte;
	private String edad;
	private String propietario;
	private String molino;
	private String estadosolicitud;
	private String acta;
	private String vendedor;
	
	
	
	public Resumen(String usuario, String cedula,
			String agricultor, String sucursal, String ciudad, String vereda,
			String nomlote, String tipocultivo, String area, String variedad,
			String fecsiembra, String feccorte, String edad,
			String propietario, String molino, String estadosolicitud,
			String acta, String vendedor) {
		super();
		this.usuario = usuario;
		this.cedula = cedula;
		this.agricultor = agricultor;
		this.sucursal = sucursal;
		this.ciudad = ciudad;
		this.vereda = vereda;
		this.nomlote = nomlote;
		this.tipocultivo = tipocultivo;
		this.area = area;
		this.variedad = variedad;
		this.fecsiembra = fecsiembra;
		this.feccorte = feccorte;
		this.edad = edad;
		this.propietario = propietario;
		this.molino = molino;
		this.estadosolicitud = estadosolicitud;
		this.acta = acta;
		this.vendedor = vendedor;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getAgricultor() {
		return agricultor;
	}
	public void setAgricultor(String agricultor) {
		this.agricultor = agricultor;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getVereda() {
		return vereda;
	}
	public void setVereda(String vereda) {
		this.vereda = vereda;
	}
	public String getNomlote() {
		return nomlote;
	}
	public void setNomlote(String nomlote) {
		this.nomlote = nomlote;
	}
	public String getTipocultivo() {
		return tipocultivo;
	}
	public void setTipocultivo(String tipocultivo) {
		this.tipocultivo = tipocultivo;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getVariedad() {
		return variedad;
	}
	public void setVariedad(String variedad) {
		this.variedad = variedad;
	}
	public String getFecsiembra() {
		return fecsiembra;
	}
	public void setFecsiembra(String fecsiembra) {
		this.fecsiembra = fecsiembra;
	}
	public String getFeccorte() {
		return feccorte;
	}
	public void setFeccorte(String feccorte) {
		this.feccorte = feccorte;
	}
	public String getEdad() {
		return edad;
	}
	public void setEdad(String edad) {
		this.edad = edad;
	}
	public String getPropietario() {
		return propietario;
	}
	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}
	public String getMolino() {
		return molino;
	}
	public void setMolino(String molino) {
		this.molino = molino;
	}
	public String getEstadosolicitud() {
		return estadosolicitud;
	}
	public void setEstadosolicitud(String estadosolicitud) {
		this.estadosolicitud = estadosolicitud;
	}
	public String getActa() {
		return acta;
	}
	public void setActa(String acta) {
		this.acta = acta;
	}
	public String getVendedor() {
		return vendedor;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	
	
}
