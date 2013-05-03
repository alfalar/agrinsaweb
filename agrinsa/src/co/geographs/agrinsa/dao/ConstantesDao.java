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

import co.geographs.agrinsa.dao.business.Dominio;
import co.geographs.agrinsa.dao.business.Permisos;
import co.geographs.agrinsa.dao.business.Totallotesxareaxvendedor;

import com.google.gson.Gson;

public class ConstantesDao {
	private HibernateTemplate hibernateTemplate;
	private String constantesJson;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	public void getConstantesJson(String usuario){
		String consulta="select p.permisoId,p.tipo, p.opcion,p.nombreRecurso,p.descripcion "+
				"from Usuarios u,MiembrosRol mr,Roles r,Permisos p,PermisosRol pr "+
				"where "+
				"u.usuarioId= mr.usuarios.usuarioId "+
				"and r.rolId= mr.roles.rolId "+
				"and r.rolId = pr.roles.rolId "+
				"and p.permisoId= pr.permisos.permisoId "+
				"and u.usuario='"+usuario+"'";
		ArrayList<Object> lista=(ArrayList)this.hibernateTemplate.find(consulta);
		List<Permisos> permisos=new ArrayList<Permisos>();
		for(int i=0;i<=lista.size()-1;i++){
			Object[] obj = (Object[]) lista.get(i);				
			permisos.add(new Permisos((Integer)obj[0],(String)obj[1],(String)obj[2],(String)obj[3],(String)obj[4]));
		}
		constantesJson=new Gson().toJson(permisos);
	}

	public String getConstantesJson() {
		return constantesJson;
	}
	/**
	 * Retorna valores de un dominio
	 * @param dominio
	 * @return
	 */
	public List<Dominio> getValoresDominio(){
		Session sesion = this.hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		List<Dominio> dominios=new ArrayList<Dominio>();
		String consulta="(SELECT codedValue.value('Code[1]','nvarchar(max)') AS Code,"+
						"codedValue.value('Name[1]', 'nvarchar(max)') AS Value ,"+
						"items.Name "+
						"FROM agrinsagdb.dbo.GDB_ITEMS AS items "+ 
						"INNER JOIN agrinsagdb.dbo.GDB_ITEMTYPES AS itemtypes "+
						"ON items.Type = itemtypes.UUID "+
						"CROSS APPLY items.Definition.nodes "+
						"('/GPCodedValueDomain2/CodedValues/CodedValue') AS CodedValues(codedValue) "+
						"WHERE itemtypes.Name = 'Coded Value Domain' "+
						"AND items.Name IN('DomCalifiLote','DomTipoVisita','DomPQCalifica','DomSiembra','DomLugarEntrega')) ";
		Query query = sesion
				.createSQLQuery(consulta)
				.addScalar("Code", Hibernate.INTEGER)
				.addScalar("Value", Hibernate.STRING)
				.addScalar("Name", Hibernate.STRING);
		List<Object> lista=query.list();
		Iterator iterator = lista.iterator();
		while (iterator.hasNext()) {
				Object[] row = (Object[]) iterator.next();
				Dominio domi=new Dominio();					
				domi.setCode((Integer)row[0]);
				domi.setValue((String)row[1]);
				domi.setDominio((String)row[2]);
				dominios.add(domi);
		}								
		
		return dominios;
	}
	
}
