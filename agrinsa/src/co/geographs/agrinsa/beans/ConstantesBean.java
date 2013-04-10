package co.geographs.agrinsa.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.context.RequestContext;

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
}
