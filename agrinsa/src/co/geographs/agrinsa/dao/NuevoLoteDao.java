package co.geographs.agrinsa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.orm.hibernate3.HibernateTemplate;

import co.geographs.agrinsa.dao.business.Coordenadas;
import co.geographs.agrinsa.dao.business.NuevosLotes;

public class NuevoLoteDao {
	private HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	/**
	 * Inserta una lista de lotes nuevos en la capa nuevolotefeature 
	 * @param nuevoslotes
	 * @return
	 */
	public String crearPuntosLote(ArrayList<NuevosLotes> nuevoslotes ){
		try{
			PreparedStatement query =null;
			Connection conection=((SessionFactoryImplementor)this.hibernateTemplate.getSessionFactory()).getConnectionProvider()
            .getConnection();
			conection.setAutoCommit(false);
			Session sesion=this.hibernateTemplate.getSessionFactory().getCurrentSession();
			Query querymax=sesion.createSQLQuery("select MAX(lf.LoteNuevoID) from dbo.NUEVOLOTEFEATURE_VW lf");
			List<String> maximo=querymax.list();
			Iterator iterator = maximo.iterator();
			int loteid=0;
			while(iterator.hasNext()){ 
				try{
					loteid = (Integer)iterator.next();	
				}catch(Exception e){}				
			}
			for(NuevosLotes nl:nuevoslotes){
				loteid++;
				int numvisita=0;
				int calificacion=nl.getCalifilote();
				int usuarioid=nl.getUsuarioid();
				String agricultor=nl.getAgricultor();
				String nombrelote=nl.getNombrelote();
				int i=0;
				for(Coordenadas co:nl.getCoordenadas()){
					i++;					
					double longitud=co.getLongitud();
					double latitud=co.getLatitud();
					query = conection.prepareStatement("{call guardapunto (?,?,?,?,?,?,?,?)}");
					query.setInt(1, loteid);
					query.setInt(2, numvisita);
					query.setInt(3, calificacion);
					query.setInt(4, usuarioid);
					query.setDouble(5, latitud);
					query.setDouble(6, longitud);
					query.setString(7, agricultor);
					query.setString(8, nombrelote);
					query.executeUpdate();												
					//System.out.println("COORDENADA INSERTADA "+i);
				}
				
			}	
			conection.commit();
			conection.close();
			return "OK";
		}catch(Exception e){
			e.printStackTrace();
			return "ERROR:"+e.getMessage();
		}
	}
}
