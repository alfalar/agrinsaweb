package co.geographs.agrinsa.dao.business;

public class Areaxciudad {
	private String ciudad;
	private double area;
	
	public Areaxciudad() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Areaxciudad(String ciudad, long area) {
		super();
		this.ciudad = ciudad;
		this.area = area;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	
}
