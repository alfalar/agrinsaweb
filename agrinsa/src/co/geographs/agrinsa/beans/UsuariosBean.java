package co.geographs.agrinsa.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import co.geographs.agrinsa.dao.UsuariosDao;
import co.geographs.agrinsa.dao.business.Usuarios;
import co.geographs.agrinsa.util.SpringUtils;

@ManagedBean(name="usuariosBean")
@SessionScoped
public class UsuariosBean implements Serializable {
	
	private List<Usuarios> listadoUsuarios;

	public List<Usuarios> getListadoUsuarios() {
		//UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		//listadoUsuarios=usuariosDao.getUsuarios();
		//return listadoUsuarios;
		return null;
	}

	public void setListadoUsuarios(ArrayList<Usuarios> listadoUsuarios) {
		this.listadoUsuarios = listadoUsuarios;
	}

}
