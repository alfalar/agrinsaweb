package co.geographs.agrinsa.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.RowEditEvent;

import co.geographs.agrinsa.dao.UsuariosDao;
import co.geographs.agrinsa.dao.business.Roles;
import co.geographs.agrinsa.dao.business.Usuarios;
import co.geographs.agrinsa.util.SpringUtils;

@ManagedBean(name = "administracionBean")
@SessionScoped
public class AdministracionBean {
	@ManagedProperty(value="#{constantesBean}")
	private ConstantesBean constantesBean;
	
	private String mensaje;
	//VARIABLES ROLES
	private List<Roles> roles;
	private int totalroles=0; 
	private String nombrerol;
	private Roles selectedRol;
	//VARIABLES USUARIOS
	private List<Usuarios> usuarios;
	private int totalusuarios=0; 
	private Usuarios selectedUsuario;
	private String nombreusuario;
	private String pwduser;
	private String primernombre;
	private String segundonombre;
	private String primerapellido;
	private String segundoapellido;
	private boolean habilitado=false;	
	public List<Roles> getRoles() {
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		roles=usuariosDao.getRoles();
		totalroles=roles.size();
		return roles;
	}
	
	public int getTotalroles() {		
		return totalroles;
	}

	public void addRol(){
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		if(nombrerol.indexOf("ROLE_")!=-1){
			mensaje=usuariosDao.addRol(nombrerol);
			if(mensaje.equalsIgnoreCase("OK")){
				constantesBean.mostrarMensaje("Rol Agregado", "INFO");	
			}else{
				constantesBean.mostrarMensaje(mensaje, "ERROR");
			}								
		}else{
			constantesBean.mostrarMensaje("El rol debe comenzar con la palabra ROLE_", "WARN");
		}
		nombrerol="";
	}
	
	public void deleteRol(){
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		if(selectedRol!=null){			
			mensaje=usuariosDao.deleteRol(selectedRol);
			if(mensaje.equalsIgnoreCase("OK")){
				constantesBean.mostrarMensaje("Rol Eliminado", "INFO");	
			}else{
				constantesBean.mostrarMensaje(mensaje, "ERROR");
			}			
		}		
	}
	
	
	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	public String getNombrerol() {
		return nombrerol;
	}


	public void setNombrerol(String nombrerol) {
		this.nombrerol = nombrerol;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Roles getSelectedRol() {
		return selectedRol;
	}

	public void setSelectedRol(Roles selectedRol) {		
		this.selectedRol = selectedRol;
	}

	public void setConstantesBean(ConstantesBean constantesBean) {
		this.constantesBean = constantesBean;
	}

	
	public List<Usuarios> getUsuarios() {
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		usuarios=usuariosDao.getUsuarios();
		totalusuarios=usuarios.size();
		return usuarios;
	}

	public void setUsuarios(List<Usuarios> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuarios getSelectedUsuario() {
		return selectedUsuario;
	}

	public void setSelectedUsuario(Usuarios selectedUsuario) {
		this.selectedUsuario = selectedUsuario;
	}

	public int getTotalusuarios() {
		return totalusuarios;
	}

	public void addUsuario(){
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		Usuarios usuario=new Usuarios();
		usuario.setUsuario(nombreusuario);
		usuario.setPassword(pwduser);
		usuario.setPrimerNombre(primernombre);
		usuario.setSegundoNombre(segundonombre);
		usuario.setPrimerApellido(primerapellido);
		usuario.setSegundoApellido(segundoapellido);
		usuario.setHabilitado(isHabilitado());
		
		mensaje=usuariosDao.addUsuario(usuario);
			if(mensaje.equalsIgnoreCase("OK")){
				constantesBean.mostrarMensaje("Rol Agregado", "INFO");	
			}else{
				constantesBean.mostrarMensaje(mensaje, "ERROR");
			}								
		
		nombrerol="";
	}
	
	
	public String getNombreusuario() {
		return nombreusuario;
	}

	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}

	public String getPwduser() {
		return pwduser;
	}

	public void setPwduser(String pwduser) {
		this.pwduser = pwduser;
	}

	public String getPrimernombre() {
		return primernombre;
	}

	public void setPrimernombre(String primernombre) {
		this.primernombre = primernombre;
	}

	public String getSegundonombre() {
		return segundonombre;
	}

	public void setSegundonombre(String segundonombre) {
		this.segundonombre = segundonombre;
	}

	public String getPrimerapellido() {
		return primerapellido;
	}

	public void setPrimerapellido(String primerapellido) {
		this.primerapellido = primerapellido;
	}

	public String getSegundoapellido() {
		return segundoapellido;
	}

	public void setSegundoapellido(String segundoapellido) {
		this.segundoapellido = segundoapellido;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public void onEdit(RowEditEvent event) {            
        Roles rol=((Roles) event.getObject());
        UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
        mensaje=usuariosDao.updateRol(rol);
		if(mensaje.equalsIgnoreCase("OK")){
			constantesBean.mostrarMensaje("Rol Actualizado", "INFO");	
		}else{
			constantesBean.mostrarMensaje(mensaje, "ERROR");
		}	
    }
	public void onEditUsuario(RowEditEvent event) {            
        Usuarios usuario=((Usuarios) event.getObject());
        UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
        //mensaje=
		if(mensaje.equalsIgnoreCase("OK")){
			constantesBean.mostrarMensaje("Usuario Actualizado", "INFO");	
		}else{
			constantesBean.mostrarMensaje(mensaje, "ERROR");
		}	
    }        
	
    public void onCancel(RowEditEvent event) {  
    }  
 }
