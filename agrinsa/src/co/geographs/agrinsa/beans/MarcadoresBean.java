package co.geographs.agrinsa.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.context.RequestContext;

import co.geographs.agrinsa.dao.MarcadoresDao;
import co.geographs.agrinsa.dao.business.Marcadores;
import co.geographs.agrinsa.util.SpringUtils;

import com.google.gson.Gson;

@ManagedBean(name = "marcadoresBean")
@SessionScoped
public class MarcadoresBean {
	@ManagedProperty(value="#{constantesBean}")
	private ConstantesBean constantesBean;

	public void addMarcador(String marcador) {
		MarcadoresDao marcadoresdao=(MarcadoresDao)SpringUtils.getBean("marcadoresDao");
		Marcadores marcadores=new Marcadores();
		marcadores.setMarcador(marcador);
		String mensaje=marcadoresdao.addMarcador(marcadores);
		if(mensaje.equalsIgnoreCase("OK")){
			constantesBean.mostrarMensaje("Marcador Agregado", "INFO");	
		}else{
			constantesBean.mostrarMensaje(mensaje, "ERROR");
		}								
	}

	public void getMarcadores(){
		MarcadoresDao marcadoresdao=(MarcadoresDao)SpringUtils.getBean("marcadoresDao");
		List<Marcadores> marcadores=marcadoresdao.getMarcadores();
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
