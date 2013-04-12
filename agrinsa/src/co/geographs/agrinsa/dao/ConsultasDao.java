package co.geographs.agrinsa.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import co.geographs.agrinsa.dao.business.Areaxciudad;
import co.geographs.agrinsa.dao.business.TiposConsulta;

public class ConsultasDao {
	private HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	/**
	 * 
	 * @param usuario
	 * @return
	 */
	public List<TiposConsulta> getConsultas(String usuario){
		String consulta="select p.opcion as value,p.nombreRecurso as label "+
				"from Usuarios u,MiembrosRol mr,Roles r,Permisos p,PermisosRol pr "+
				"where "+
				"u.usuarioId= mr.usuarios.usuarioId "+
				"and r.rolId= mr.roles.rolId "+
				"and r.rolId = pr.roles.rolId "+
				"and p.permisoId= pr.permisos.permisoId "+
				"and p.tipo='CONSULTAS' "+
				"and u.usuario='"+usuario+"'";
		ArrayList<Object> lista=(ArrayList)this.hibernateTemplate.find(consulta);
		List<TiposConsulta> recursos=new ArrayList<TiposConsulta>();
		recursos.add(new TiposConsulta("ND","Seleccione una consulta"));
		for(int i=0;i<=lista.size()-1;i++){
			Object[] obj = (Object[]) lista.get(i);				
			recursos.add(new TiposConsulta((String) obj[0],(String) obj[1]));
		}
		return recursos;
	}
	
	/**
	 * 
	 */
	public List<Areaxciudad> getAreaxciudad(){
		List<Areaxciudad> arealist=new ArrayList<Areaxciudad>();
		Session sesion = this.hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		String consulta="select nlote.CiudadID as ciudad, SUM(nlote.Area) as areasembrada "+
						"from LOTE_VW nlote "+
						"group by nlote.CiudadID";
		Query query = sesion
				.createSQLQuery(consulta);
		List<Object> lista=query.list();
		Iterator iterator = lista.iterator();
		while (iterator.hasNext()) {
				Object[] row = (Object[]) iterator.next();
				Areaxciudad areaxciudad=new Areaxciudad();
				areaxciudad.setCiudad(String.valueOf(row[0]));
				areaxciudad.setArea( ((BigDecimal)row[1]).doubleValue() );
				arealist.add(areaxciudad);
		}						
		return arealist;		
	}
}
