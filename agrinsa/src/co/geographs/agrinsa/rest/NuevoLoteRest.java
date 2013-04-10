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

import co.geographs.agrinsa.dao.NuevoLoteDao;
import co.geographs.agrinsa.dao.UsuariosDao;
import co.geographs.agrinsa.dao.business.NuevosLotes;
import co.geographs.agrinsa.util.SpringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Path("/nuevolote")
public class NuevoLoteRest {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	@Resource
	private NuevoLoteDao nuevolotedao;

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String almacenarnuevolote(
			@FormParam("nuevoslotes") String nuevoslotesjson) {
		System.out.println(nuevoslotesjson);
		String resultado = "";
		nuevolotedao = (NuevoLoteDao) SpringUtils.getBean("nuevoloteDao");
		Gson gson = new Gson();
		Type listanuevoslotes = new TypeToken<List<NuevosLotes>>() {
		}.getType();
		ArrayList<NuevosLotes> nuevoslotes = gson.fromJson(nuevoslotesjson,
				listanuevoslotes);
		resultado = nuevolotedao.crearPuntosLote(nuevoslotes);
		return resultado;
	}
}
