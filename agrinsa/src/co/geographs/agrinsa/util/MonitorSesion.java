package co.geographs.agrinsa.util;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

@ManagedBean(name = "monitorBean")
@SessionScoped
public class MonitorSesion {
	private int sessiontimeout;
	private int sumaminutossesion=0;
	public int getSessiontimeout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		sessiontimeout=session.getMaxInactiveInterval();
		System.out.println("sessiontimeout:"+sessiontimeout);
		return sessiontimeout;
	}

	public void setSessiontimeout(int sessiontimeout) {
		this.sessiontimeout = sessiontimeout;
	}

	public void idleListener() {
		getSessiontimeout();
		sumaminutossesion=sumaminutossesion+5;
		System.out.println("sumaminutossesion:"+sumaminutossesion);
		if(sumaminutossesion>=(this.sessiontimeout/60)){
			 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,   
					 "Su sesion expiró", "Ha estado inactivo mucho tiempo."));
			 RequestContext context = RequestContext.getCurrentInstance();
			 context.execute("location.href=\"j_spring_security_logout\"");		 			
		}
	}
	
	public void activeListener() {
		System.out.println("EJECUTA active");
		 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,   
                 "Bienvenido de nuevo", "Ha estado inactivo mucho tiempo."));		
	}
	
}
