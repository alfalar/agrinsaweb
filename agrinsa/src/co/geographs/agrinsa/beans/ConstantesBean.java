package co.geographs.agrinsa.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.stereotype.Component;

import co.geographs.agrinsa.dao.ConstantesDao;
import co.geographs.agrinsa.util.SpringUtils;

@ManagedBean(name = "constantesBean")
@SessionScoped
public class ConstantesBean {
	public void getConstantesJson() {
		ConstantesDao constantes=(ConstantesDao)SpringUtils.getBean("constantesDao");
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("constantes", constantes.getConstantesJson());
	}
	
	public void mostrarMensaje(String texto, String tipo){
		FacesContext context = FacesContext.getCurrentInstance();		
		if(tipo.equalsIgnoreCase("INFO")){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Mensaje Informativo", texto));	
		}else if(tipo.equalsIgnoreCase("ERROR")){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", texto));			
		}else{
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia", texto));	
		}
		
	}
}
