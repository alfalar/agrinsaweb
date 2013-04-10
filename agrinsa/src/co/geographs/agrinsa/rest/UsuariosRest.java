package co.geographs.agrinsa.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import co.geographs.agrinsa.dao.UsuariosDao;
import co.geographs.agrinsa.dao.business.UsuariosWS;
import co.geographs.agrinsa.util.SpringUtils;

@Path("/usuarios")
public class UsuariosRest {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	@Resource
	private UsuariosDao usuarios;
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<UsuariosWS> getUsuarios() {
		usuarios = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		List<UsuariosWS> usuarioslist=(ArrayList)usuarios.getUsuariosRest();
		return usuarioslist;
	}
	
}
