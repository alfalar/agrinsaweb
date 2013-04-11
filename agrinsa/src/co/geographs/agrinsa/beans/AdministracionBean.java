package co.geographs.agrinsa.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import co.geographs.agrinsa.dao.UsuariosDao;
import co.geographs.agrinsa.dao.business.Roles;
import co.geographs.agrinsa.util.SpringUtils;

@ManagedBean(name = "administracionBean")
@SessionScoped
public class AdministracionBean {
	@ManagedProperty(value="#{constantesBean}")
	private ConstantesBean constantesBean;
	private List<Roles> roles;
	private int totalroles=0; 
	private String nombrerol;
	private String mensaje;
	private Roles selectedRol;

	
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
	
}
