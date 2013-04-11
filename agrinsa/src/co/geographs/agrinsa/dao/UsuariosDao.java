package co.geographs.agrinsa.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.orm.hibernate3.HibernateTemplate;

import co.geographs.agrinsa.dao.business.Roles;
import co.geographs.agrinsa.dao.business.Usuarios;
import co.geographs.agrinsa.dao.business.UsuariosWS;

public class UsuariosDao {
	
    private HibernateTemplate hibernateTemplate;

    public void setSessionFactory(SessionFactory sessionFactory) {    
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }
    /**
     * Retorna un objeto usario completo
     * @param usuario
     * @return
     */
	public Usuarios getUsuario(String usuario){
		List<Usuarios> usuarios= this.hibernateTemplate.find("from Usuarios u where u.usuario='"+usuario+"'");
		return usuarios.get(0);
	}
	
	 /**
     * Retorna la lista de usuarios
     * @return
     */
	public List<UsuariosWS> getUsuariosRest(){		
		List<Object> usuarios=this.hibernateTemplate.find("select u.usuario,u.password,u.primerNombre,u.segundoNombre,u.primerApellido,u.segundoApellido,u.habilitado,u.usuarioId from Usuarios u");		
		List<UsuariosWS> usuariosws=new ArrayList<UsuariosWS>();
		for(int i=0;i<=usuarios.size()-1;i++){
			Object[] obj = (Object[]) usuarios.get(i);				
			usuariosws.add(new UsuariosWS((Integer)obj[7],(String)obj[0],(String)obj[1],(String)obj[2],(String)obj[3],(String)obj[4],(String)obj[5],(Boolean)obj[6]));
		}
		return usuariosws;
	}
	/**
	 * Validacion del usuario
	 * @param usuario
	 * @param password
	 * @return
	 */
	public boolean getUsuario(String usuario, String password){
		List<Usuarios> u=this.hibernateTemplate.find("from Usuarios u where u.usuario='"+usuario+"' and u.password='"+password+"'");
		if(u.size()>0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * Retorna los recursos permitidos para un usuario
	 * @param usuario
	 * @return
	 */
	public List<String> getRecursosPermitidos(String usuario){
		String consulta="select mr.roles.rol as rol,p.nombreRecurso as recurso "+
				"from Usuarios u,MiembrosRol mr,Roles r,Permisos p,PermisosRol pr "+
				"where "+
				"u.usuarioId= mr.usuarios.usuarioId "+
				"and r.rolId= mr.roles.rolId "+
				"and r.rolId = pr.roles.rolId "+
				"and p.permisoId= pr.permisos.permisoId "+
				"and u.usuario='"+usuario+"'";
		ArrayList<Object> lista=(ArrayList)this.hibernateTemplate.find(consulta);
		List<String> recursos=new ArrayList<String>();
		for(int i=0;i<=lista.size()-1;i++){
			Object[] obj = (Object[]) lista.get(i);				
			recursos.add((String) obj[1]);
		}
		return recursos;
	}
	/*
	 * Retorna la lista de roles de un usuario
	 */
	public List<Roles> getRoles(String usuario){
		String consulta="select mr.roles as roles "+
				"from Usuarios u, MiembrosRol mr "+
				"where u.usuarioId= mr.usuarios.usuarioId "+
				"and u.usuario='"+usuario+"'";
		List<Roles> lista=this.hibernateTemplate.find(consulta);
		return lista;
	}
	/**
	 * Retorna la lista de roles en el sistema
	 * @return
	 */
	public List<Roles> getRoles(){
		String consulta="from Roles";
		List<Roles> lista=this.hibernateTemplate.find(consulta);
		return lista;
	}
	
	/**
	 * 
	 * @param nombrerol
	 * @return
	 */
	public String addRol(String nombrerol) {
		try {
			Roles rol = new Roles();
			rol.setRol(nombrerol);
			this.hibernateTemplate.save(rol);
			this.hibernateTemplate.flush();
			((SessionFactoryImplementor) this.hibernateTemplate
					.getSessionFactory()).getConnectionProvider()
					.getConnection().commit();
			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public String deleteRol(Roles rol) {
		try {						
			this.hibernateTemplate.delete(rol);
			this.hibernateTemplate.flush();
			((SessionFactoryImplementor) this.hibernateTemplate
					.getSessionFactory()).getConnectionProvider()
					.getConnection().commit();
			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

}
