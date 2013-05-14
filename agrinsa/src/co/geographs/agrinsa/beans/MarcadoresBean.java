package co.geographs.agrinsa.beans;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.context.RequestContext;

import co.geographs.agrinsa.dao.MarcadoresDao;
import co.geographs.agrinsa.dao.business.Marcadores;
import co.geographs.agrinsa.dao.business.NuevosLotes;
import co.geographs.agrinsa.util.SpringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@ManagedBean(name = "marcadoresBean")
@SessionScoped
public class MarcadoresBean {
	@ManagedProperty(value="#{constantesBean}")
	private ConstantesBean constantesBean;

	public void addMarcador(String marcador) {		
		List<Marcadores> data = new Gson().fromJson(marcador, new TypeToken<List<Marcadores>>(){}.getType());		
		MarcadoresDao marcadoresdao=(MarcadoresDao)SpringUtils.getBean("marcadoresDao");
		marcadoresdao.deleteMarcador();
		for(Marcadores s: data){		
			System.out.println("MARCADOR ID="+s.getMarcadorId());
			String mensaje=marcadoresdao.addMarcador(s);
			if(mensaje.equalsIgnoreCase("OK")){
				constantesBean.mostrarMensaje("Marcador Agregado", "INFO");	
			}else{
				constantesBean.mostrarMensaje(mensaje, "ERROR");
			}											
		}
	}

	public void getMarcadores(){
		System.out.println("CONSULTANDO MARCADORES...");
		MarcadoresDao marcadoresdao=(MarcadoresDao)SpringUtils.getBean("marcadoresDao");
		List<Marcadores> marcadores=marcadoresdao.getMarcadores();
		System.out.println("RETORNADOS "+marcadores.size()+" MARCADORES.");
		RequestContext context = RequestContext.getCurrentInstance();
		String rjson=new Gson().toJson(marcadores);
		context.addCallbackParam("listamarcadores",rjson); 
		
	}
	public ConstantesBean getConstantesBean() {
		return constantesBean;
	}

	public void setConstantesBean(ConstantesBean constantesBean) {
		this.constantesBean = constantesBean;
	}	
	
	
}
