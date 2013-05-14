package co.geographs.agrinsa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.orm.hibernate3.HibernateTemplate;

import co.geographs.agrinsa.dao.business.Marcadores;

public class MarcadoresDao {
	private HibernateTemplate hibernateTemplate;
	private String constantesJson;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	public List<Marcadores> getMarcadores(){		
		List<Marcadores> marcadores=this.hibernateTemplate.find("from Marcadores m");		
		return marcadores;
	}
	
	public String addMarcador(Marcadores marcador) {
		try {
			this.hibernateTemplate.save(marcador);
			this.hibernateTemplate.flush();
			Connection con=((SessionFactoryImplementor) this.hibernateTemplate
					.getSessionFactory()).getConnectionProvider()
					.getConnection();
			con.commit();
			con.close();
			return "OK";		
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public String deleteMarcador(Marcadores marcador) {
		try {		
			Connection conection = ((SessionFactoryImplementor) this.hibernateTemplate
					.getSessionFactory()).getConnectionProvider()
					.getConnection();
			conection.setAutoCommit(false);
			PreparedStatement query = conection.prepareStatement("delete from Marcadores");
			query.executeUpdate();			
			conection.commit();
			conection.close();
			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public String updateMarcador(Marcadores marcador) {
		try {						
			this.hibernateTemplate.update(marcador);
			this.hibernateTemplate.flush();
			Connection con=((SessionFactoryImplementor) this.hibernateTemplate
					.getSessionFactory()).getConnectionProvider()
					.getConnection();
			con.commit();
			con.close();
			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	
}
