package co.geographs.agrinsa.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import co.geographs.agrinsa.dao.business.MiembrosRol;
import co.geographs.agrinsa.dao.business.MiembrosRolId;
import co.geographs.agrinsa.dao.business.Permisos;
import co.geographs.agrinsa.dao.business.PermisosRol;
import co.geographs.agrinsa.dao.business.PermisosRolId;
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
	
	public List<Usuarios> getUsuarios(){		
		List<Usuarios> usuarios=this.hibernateTemplate.find("from Usuarios u");		
		return usuarios;
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
	 * 
	 * @param nombrerol
	 * @return
	 */
	public String addUsuario(Usuarios usuario) {
		try {
			this.hibernateTemplate.save(usuario);
			this.hibernateTemplate.flush();
			((SessionFactoryImplementor) this.hibernateTemplate
					.getSessionFactory()).getConnectionProvider()
					.getConnection().commit();
			return "OK";
		} catch (DataIntegrityViolationException e) {
			return "El nombre de usuario ya existe";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public String deleteUsuario(Usuarios usuario) {
		try {						
			this.hibernateTemplate.delete(usuario);
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
	
	public String updateUsuario(Usuarios usuario) {
		try {						
			this.hibernateTemplate.update(usuario);
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
		} catch (DataIntegrityViolationException e) {
			return "El nombre de rol ya existe";
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
	
	public String updateRol(Roles rol) {
		try {						
			this.hibernateTemplate.update(rol);
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
	/**
	 * Trae los usuarios de un rol
	 * @param rolId
	 * @return
	 */
	public List<Usuarios> getUsuariosRol(Roles selectedRol){
		try{
			String consulta="select us from MiembrosRol mr, Usuarios us, Roles ro "+
							"where "+ 
							"mr.usuarios.usuarioId= us.usuarioId "+
							"and mr.roles.rolId=ro.rolId "+
							"and ro.rolId=:rolId";
			List<Usuarios> usuarios=this.hibernateTemplate.findByNamedParam(consulta, "rolId", selectedRol.getRolId());
			return usuarios;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

	}

	public String addUserToRol(Usuarios usuario,Roles rol){
		try {
			MiembrosRol miembro=new MiembrosRol();
			miembro.setRoles(rol);
			miembro.setUsuarios(usuario);
			MiembrosRolId mid= new MiembrosRolId(rol.getRolId(), usuario.getUsuarioId());
			miembro.setId(mid);
			this.hibernateTemplate.save(miembro);
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
	
	public String deleteUserFromRol(Usuarios usuario,Roles rol){
		try {
			MiembrosRol miembro=new MiembrosRol();
			miembro.setRoles(rol);
			miembro.setUsuarios(usuario);
			MiembrosRolId mid= new MiembrosRolId(rol.getRolId(), usuario.getUsuarioId());
			miembro.setId(mid);
			this.hibernateTemplate.delete(miembro);
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
	
	public List<Permisos> getPermisos(){
		List<Permisos> permisos = this.hibernateTemplate.find("from Permisos");
		return permisos;
	}
	
	public List<Permisos> getRecursosRol(Roles selectedRol){
		try{
			String consulta="select permiso from Roles rol,Permisos permiso, PermisosRol permirol "+
							"where rol.rolId=permirol.id.rolId "+
							"and permiso.permisoId=permirol.id.permisoId "+
							"and rol.rolId=:rolId";
			List<Permisos> permirol=this.hibernateTemplate.findByNamedParam(consulta, "rolId", selectedRol.getRolId());
			return permirol;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public String deletePermisoFromRol(Permisos permiso,Roles rol){
		try {
			PermisosRol permisorol= new PermisosRol();
			permisorol.setRoles(rol);
			permisorol.setPermisos(permiso);
			PermisosRolId  pid= new PermisosRolId(rol.getRolId(), permiso.getPermisoId());
			permisorol.setId(pid);
			this.hibernateTemplate.delete(permisorol);
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
	
	public String addRecursoToRol(Permisos permiso,Roles rol){
		try {
			PermisosRol permisorol= new PermisosRol();
			permisorol.setRoles(rol);
			permisorol.setPermisos(permiso);
			PermisosRolId  pid= new PermisosRolId(rol.getRolId(), permiso.getPermisoId());
			permisorol.setId(pid);
			this.hibernateTemplate.save(permisorol);
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
	
	public String updatePermiso(Permisos permiso) {
		try {						
			this.hibernateTemplate.update(permiso);
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
	
	public String addRecurso(Permisos permiso) {
		try {
			this.hibernateTemplate.save(permiso);
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
	
	public String deleteRecurso(Permisos permiso) {
		try {						
			this.hibernateTemplate.delete(permiso);
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
