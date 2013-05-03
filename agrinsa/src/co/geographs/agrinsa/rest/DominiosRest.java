package co.geographs.agrinsa.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import co.geographs.agrinsa.dao.ConstantesDao;
import co.geographs.agrinsa.dao.business.Dominio;
import co.geographs.agrinsa.util.SpringUtils;

@Path("/dominio")
public class DominiosRest {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	@Resource
	private ConstantesDao constantes;
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Dominio> getUsuarios() {
		constantes = (ConstantesDao)SpringUtils.getBean("constantesDao");
		List<Dominio> dominioslist=constantes.getValoresDominio();
		return dominioslist;
	}
}
