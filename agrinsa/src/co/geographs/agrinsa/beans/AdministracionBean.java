package co.geographs.agrinsa.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import co.geographs.agrinsa.dao.UsuariosDao;
import co.geographs.agrinsa.dao.business.Roles;
import co.geographs.agrinsa.util.SpringUtils;

@ManagedBean(name = "administracionBean")
@SessionScoped
public class AdministracionBean {
	private List<Roles> roles;
	private int totalroles=0; 
	
	public List<Roles> getRoles() {
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		roles=usuariosDao.getRoles();
		totalroles=roles.size();
		return roles;
	}
	
	

	public int getTotalroles() {		
		return totalroles;
	}



	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

}
