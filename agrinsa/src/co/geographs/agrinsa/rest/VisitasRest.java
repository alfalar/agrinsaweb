package co.geographs.agrinsa.rest;

import java.lang.reflect.Type;
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

import co.geographs.agrinsa.dao.VisitasDao;
import co.geographs.agrinsa.dao.business.Lotes;
import co.geographs.agrinsa.dao.business.NuevosLotes;
import co.geographs.agrinsa.dao.business.Visita;
import co.geographs.agrinsa.util.SpringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Path("/visitas")
public class VisitasRest {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	@Resource
	private VisitasDao visitasdao;
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Lotes> getLotes(@FormParam("usuarioid") String usuarioid) {
		visitasdao = (VisitasDao) SpringUtils.getBean("visitasDao");
		List<Lotes> lotes=visitasdao.getLotesRest(usuarioid);
		return lotes;
	}
	
	@POST
	@Path("setVisitas")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String setVisitas(@FormParam("visitas") String visitas) {
		visitasdao = (VisitasDao) SpringUtils.getBean("visitasDao");
		Gson gson = new Gson();
		Type listavisitas = new TypeToken<List<Visita>>() {}.getType();
		ArrayList<Visita> visitaslist = gson.fromJson(visitas,listavisitas);
		String resultado = visitasdao.crearVisita(visitaslist);
		return resultado;
	}
}
