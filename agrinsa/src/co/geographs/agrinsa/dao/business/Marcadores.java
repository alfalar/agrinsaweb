package co.geographs.agrinsa.dao.business;

// default package
// Generated 01-mar-2013 8:02:10 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Marcadores", schema = "dbo", catalog = "agrinsagdb")
public class Marcadores implements java.io.Serializable {

	private int marcadorId;
	private String marcador;
	
	public Marcadores() {
	}

	public Marcadores(int marcadorId, String marcador) {
		this.marcadorId = marcadorId;
		this.marcador = marcador;
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "marcador_id", unique = true, nullable = false)
	public int getMarcadorId() {
		return marcadorId;
	}

	public void setMarcadorId(int marcadorId) {
		this.marcadorId = marcadorId;
	}	

	@Column(name = "marcador", nullable = false)
	public String getMarcador() {
		return marcador;
	}


	public void setMarcador(String marcador) {
		this.marcador = marcador;
	}

}
