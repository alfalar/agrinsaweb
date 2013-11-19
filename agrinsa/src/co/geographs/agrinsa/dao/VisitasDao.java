package co.geographs.agrinsa.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.orm.hibernate3.HibernateTemplate;

import co.geographs.agrinsa.dao.business.Lotes;
import co.geographs.agrinsa.dao.business.Visita;

public class VisitasDao {
	private HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	/**
	 * 
	 * @param usuario_id
	 * @return
	 */
	public List<Lotes> getLotesRest(String usuario_id) {
		try {
			Date fecha;
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
			ArrayList<Lotes> lotes = new ArrayList<Lotes>();
			Session sesion = this.hibernateTemplate.getSessionFactory()
					.getCurrentSession();
			
			Query query = sesion
					.createSQLQuery(
							"select * from agrinsagdb.dbo.LOTE_VW a "
									+ "where a.usuarioid=:usuarioid "
									+ "and a.estado=1 "
									+ "and a.NomLote is not null")
					.addScalar("Agricultor", Hibernate.STRING)
					.addScalar("NomLote", Hibernate.STRING)
					.addScalar("Semestre", Hibernate.STRING)
					.addScalar("FecSiembra", Hibernate.DATE)
					.addScalar("FecGerminacion", Hibernate.DATE)
					.addScalar("FecCorteReal", Hibernate.DATE)
					.addScalar("Siembra", Hibernate.STRING)
					.addScalar("LoteID", Hibernate.INTEGER);
			query.setParameter("usuarioid", usuario_id);
			List<Object> listalotes = query.list();
			Iterator iterator = listalotes.iterator();
			while (iterator.hasNext()) {
				try {
					Object[] row = (Object[]) iterator.next();
					Lotes lote = new Lotes();
					lote.setAgricultor((String) row[0]);
					lote.setNomlote((String) row[1]);
					lote.setSemestre((String) row[2]);					
					fecha=(Date) row[3];	
					if(fecha==null){
						lote.setFecsiembra("");
					}else{
						lote.setFecsiembra(sdf.format(fecha));
					}					
					fecha=(Date) row[4];
					if(fecha==null){
						lote.setFecgerminacion("");
					}else{
						lote.setFecgerminacion(sdf.format(fecha));
					}						
					fecha=(Date) row[5];
					if(fecha==null){
						lote.setFeccorte(null);
					}else{
						lote.setFeccorte(sdf.format(fecha));
					}						
					lote.setSiembra((String) row[6]);
					lote.setLoteid((Integer) row[7]);
					lote.setNumvisita(getNumvisita((Integer) row[7]));
					lotes.add(lote);
				} catch (Exception e) {
				}
			}

			return lotes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private int getNumvisita(int loteid){
		Session sesion = this.hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		Query querymax = sesion
				.createSQLQuery("select MAX(lf.NumVisita) from dbo.VisitaTerreno_vw lf where lf.loteid=:loteid");
		querymax.setParameter("loteid", loteid);
		List<String> maximo = querymax.list();
		Iterator iteratormax = maximo.iterator();
		int numvisita = 0;
		while (iteratormax.hasNext()) {
			try {
				numvisita = (Integer) iteratormax.next();
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		numvisita++;
		return numvisita;
	}
/**
 * Almacena las visitas en la geodatabase
 * @param visitas
 * @return
 */
	public String crearVisita(ArrayList<Visita> visitas) {
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date dateutil;
			java.sql.Date datesql;
			PreparedStatement query = null;
			Connection conection = ((SessionFactoryImplementor) this.hibernateTemplate
					.getSessionFactory()).getConnectionProvider()
					.getConnection();
			conection.setAutoCommit(false);
			for (Visita v : visitas) {
				System.out.println("Numero de visita a guardar:"+v.getNumvisita());
				query = conection
						.prepareStatement("{call guardarvisita (?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				query.setInt(1, v.getLoteid());
				query.setInt(2, v.getSemestre());
				query.setInt(3, v.getNumvisita());
				if(v.getFechasiembra().length()>0){
					dateutil = sdf.parse(v.getFechasiembra());				
	                datesql = new java.sql.Date(dateutil.getTime());
					query.setDate(4, datesql);					
				}else{
					query.setDate(4, null);
				}
				if(v.getFechagerminacion().length()>0){
					dateutil = sdf.parse( v.getFechagerminacion());				
	                datesql = new java.sql.Date(dateutil.getTime());
					query.setDate(5,datesql);					
				}else{
					query.setDate(5,null);
				}
				if(v.getFechacorte().length()>0){
					dateutil = sdf.parse( v.getFechacorte());				
	                datesql = new java.sql.Date(dateutil.getTime());				
					query.setDate(6, datesql);					
				}else{
					query.setDate(6, null);					
				}
				query.setInt(7, v.getTipovisita());
				query.setInt(8, v.getCalificacion());
				query.setInt(9, v.getPorque());
				query.setInt(10, v.getTiposiembra());
				query.setDouble(11, v.getProduccion());
				query.setInt(12, v.getLugarentrega());
				dateutil = sdf.parse( v.getFechavisita());				
                datesql = new java.sql.Date(dateutil.getTime());				
				query.setDate(13, datesql);
				query.executeUpdate();				
			}
			conection.commit();
			conection.close();
			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR:" + e.getMessage();
		}
	}

}
