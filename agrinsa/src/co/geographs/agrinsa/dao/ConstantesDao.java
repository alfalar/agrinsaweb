package co.geographs.agrinsa.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import co.geographs.agrinsa.dao.business.Permisos;

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
	
	
}
