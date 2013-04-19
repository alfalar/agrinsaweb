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
import co.geographs.agrinsa.dao.business.Areaxvereda;
import co.geographs.agrinsa.dao.business.Sembradoporcultivo;
import co.geographs.agrinsa.util.FacesUtil;
import co.geographs.agrinsa.util.SpringUtils;

@ManagedBean(name = "consultasBean")
@SessionScoped
public class ConsultasBean implements Serializable {
	private List<SelectItem> tiposconsulta;
	private String selectedTiposconsulta;
	//VARIABLES AREA POR CIUDAD
	private List<Areaxciudad> areaxciudad;	
	private boolean areaxciudadenabled=false;
	//VARIABLES AREA POR VEREDA
	private List<Areaxvereda> areaxvereda;	
	private boolean areaxveredaenabled=false;
	//VARIABLES AREA X CULTIVO
	private boolean areaxcultivoenabled=false;
	private List<Sembradoporcultivo> areaxcultivo;
	
	
	public void cambioconsulta() {
		ConsultasDao consultasDao = (ConsultasDao) SpringUtils
				.getBean("consultasDao");
		if(selectedTiposconsulta.equalsIgnoreCase("areaxciudad")){
			areaxciudad = consultasDao.getAreaxciudad();
			areaxciudadenabled=true;
			areaxveredaenabled=false;
			areaxcultivoenabled=false;
		}else if(selectedTiposconsulta.equalsIgnoreCase("areaxvereda")){
			areaxvereda=consultasDao.getAreaxvereda();
			areaxciudadenabled=false;
			areaxcultivoenabled=false;
			areaxveredaenabled=true;
		}else if(selectedTiposconsulta.equalsIgnoreCase("totalxcultivo")){
			areaxcultivo=consultasDao.getSembradoporcultivo();
			areaxciudadenabled=false;
			areaxcultivoenabled=true;
			areaxveredaenabled=false;
			
		}else{
			areaxciudadenabled=false;
			areaxveredaenabled=false;
			areaxcultivoenabled=false;
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

	public boolean isAreaxveredaenabled() {
		return areaxveredaenabled;
	}

	public void setAreaxveredaenabled(boolean areaxveredaenabled) {
		this.areaxveredaenabled = areaxveredaenabled;
	}

	public List<Areaxvereda> getAreaxvereda() {
		return areaxvereda;
	}

	public boolean isAreaxcultivoenabled() {
		return areaxcultivoenabled;
	}

	
	
}
