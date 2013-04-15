package co.geographs.agrinsa.dao.business;

public class Areaxvereda {
	private String vereda;
	private double area;
	
	
	public Areaxvereda() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Areaxvereda(String vereda, double area) {
		super();
		this.vereda = vereda;
		this.area = area;
	}
	public String getVereda() {
		return vereda;
	}
	public void setVereda(String vereda) {
		this.vereda = vereda;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	
	
}
