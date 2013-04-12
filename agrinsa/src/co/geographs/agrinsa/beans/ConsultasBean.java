package co.geographs.agrinsa.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import co.geographs.agrinsa.dao.ConsultasDao;
import co.geographs.agrinsa.dao.business.Areaxciudad;
import co.geographs.agrinsa.dao.business.TiposConsulta;
import co.geographs.agrinsa.util.FacesUtil;
import co.geographs.agrinsa.util.SpringUtils;

@ManagedBean(name = "consultasBean")
@SessionScoped
public class ConsultasBean implements Serializable {
	private List<SelectItem> tiposconsulta;
	private List<Areaxciudad> areaxciudad;
	private String selectedTiposconsulta;
	private boolean areaxciudadenabled=false;
	public void cambioconsulta() {
		System.out.println("*******************************:"
				+ selectedTiposconsulta);
		if(selectedTiposconsulta.equalsIgnoreCase("areaxciudad")){
			areaxciudadenabled=true;
		}else{
			areaxciudadenabled=false;
		}
		
	}
	
	public void setTiposconsulta(ArrayList<SelectItem> tiposconsulta) {
		this.tiposconsulta = tiposconsulta;
	}



	/**
	 * Retorna la lista de tipos de consulta
	 * 
	 * @return
	 */
	public List<SelectItem> getTiposconsulta() {
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			ConsultasDao consultasDao = (ConsultasDao) SpringUtils
					.getBean("consultasDao");
			List l = consultasDao.getConsultas(userDetails.getUsername());
			tiposconsulta = (ArrayList) FacesUtil.entityToSelectItem(l,
					"getValue", "getLabel");
			return tiposconsulta;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Consulta1: Area x ciudad
	 * 
	 * @return
	 */
	public List<Areaxciudad> getAreaxciudad() {
		ConsultasDao consultasDao = (ConsultasDao) SpringUtils
				.getBean("consultasDao");
		areaxciudad = consultasDao.getAreaxciudad();
		return areaxciudad;
	}

	


	public String getSelectedTiposconsulta() {
		return selectedTiposconsulta;
	}



	public void setSelectedTiposconsulta(String selectedTiposconsulta) {
		this.selectedTiposconsulta = selectedTiposconsulta;
	}



	public boolean isAreaxciudadenabled() {
		return areaxciudadenabled;
	}



	public void setAreaxciudadenabled(boolean areaxciudadenabled) {
		this.areaxciudadenabled = areaxciudadenabled;
	}

}
