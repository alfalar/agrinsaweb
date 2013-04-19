package co.geographs.agrinsa.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import co.geographs.agrinsa.dao.business.Areaxciudad;
import co.geographs.agrinsa.dao.business.Areaxvereda;
import co.geographs.agrinsa.dao.business.Sembradoporcultivo;
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
	 * Retorna el area cultivada por ciudad
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
				String ciudad=String.valueOf(row[0]);
				if(ciudad.equalsIgnoreCase("null")){
					ciudad="INDEFINIDA";
				}
				areaxciudad.setCiudad(ciudad);
				areaxciudad.setArea( ((BigDecimal)row[1]).doubleValue() );
				arealist.add(areaxciudad);
		}						
		return arealist;		
	}
	
	/**
	 * Retorna el area cultivada por vereda
	 */
	public List<Areaxvereda> getAreaxvereda(){
		List<Areaxvereda> arealist=new ArrayList<Areaxvereda>();
		Session sesion = this.hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		String consulta="select nlote.Vereda as vereda, SUM(nlote.Area) as areasembrada "+
						"from LOTE_VW nlote "+
						"group by nlote.Vereda";
		Query query = sesion
				.createSQLQuery(consulta)
				.addScalar("vereda", Hibernate.STRING)
				.addScalar("areasembrada", Hibernate.BIG_DECIMAL);
		List<Object> lista=query.list();
		Iterator iterator = lista.iterator();
		while (iterator.hasNext()) {
				Object[] row = (Object[]) iterator.next();
				Areaxvereda areaxvereda=new Areaxvereda();
				String ciudad=String.valueOf(row[0]);
				if(ciudad.equalsIgnoreCase("null")){
					ciudad="INDEFINIDA";
				}	
				areaxvereda.setVereda(ciudad);
				areaxvereda.setArea( ((BigDecimal)row[1]).doubleValue() );
				arealist.add(areaxvereda);
		}						
		return arealist;		
	}
	
	public List<Sembradoporcultivo> getSembradoporcultivo(){
		List<Sembradoporcultivo> arealist=new ArrayList<Sembradoporcultivo>();
		Session sesion = this.hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		String consulta="select tipocultivo.Value as cultivo, SUM(nlote.Area) as areasembrada "+
				"from agrinsagdb.dbo.LOTE_VW nlote, "+ 
				"(SELECT * "+
				"FROM agrinsagdb.dbo.CULTIVOS_VW LEFT OUTER JOIN "+
				"(SELECT "+
				   "codedValue.value('Code[1]','nvarchar(max)') AS Code, "+ 
				   "codedValue.value('Name[1]', 'nvarchar(max)') AS Value "+
				   "FROM agrinsagdb.dbo.GDB_ITEMS AS items INNER JOIN agrinsagdb.dbo.GDB_ITEMTYPES AS itemtypes "+ 
				   "ON items.Type = itemtypes.UUID "+
				   "CROSS APPLY items.Definition.nodes "+
				   "('/GPCodedValueDomain2/CodedValues/CodedValue') AS CodedValues(codedValue) "+
				   "WHERE itemtypes.Name = 'Coded Value Domain' "+ 
				   "AND items.Name = 'DomCultivo') AS CodedValues "+
				   "ON agrinsagdb.dbo.CULTIVOS_VW.Descripcion = CodedValues.Code) as tipocultivo "+
				   "where nlote.LoteID=tipocultivo.LoteID "+ 
				   "group by tipocultivo.Value ";
		Query query = sesion
				.createSQLQuery(consulta)
				.addScalar("cultivo", Hibernate.STRING)
				.addScalar("areasembrada", Hibernate.BIG_DECIMAL);
		List<Object> lista=query.list();
		Iterator iterator = lista.iterator();
		while (iterator.hasNext()) {
				Object[] row = (Object[]) iterator.next();
				Sembradoporcultivo areaxcultivo=new Sembradoporcultivo();
				areaxcultivo.setCultivo(String.valueOf(row[0]));
				areaxcultivo.setArea( ((BigDecimal)row[1]).doubleValue() );
				arealist.add(areaxcultivo);
		}						
		return arealist;			
	}
}
