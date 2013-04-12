package co.geographs.agrinsa.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import co.geographs.agrinsa.dao.ConsultasDao;
import co.geographs.agrinsa.dao.business.Areaxciudad;
import co.geographs.agrinsa.dao.business.TiposConsulta;
import co.geographs.agrinsa.util.SpringUtils;

@ManagedBean(name = "consultasBean")
@SessionScoped
public class ConsultasBean implements Serializable {
	private List<TiposConsulta> tiposconsulta;
	private List<Areaxciudad> areaxciudad;
	private TiposConsulta selectedTiposconsulta;

	public void cambioconsulta(){
		System.out.println("*******************************:"+selectedTiposconsulta.getValue());
	}
	/**
	 * Retorna la lista de tipos de consulta
	 * 
	 * @return
	 */
	public List<TiposConsulta> getTiposconsulta() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		ConsultasDao consultasDao = (ConsultasDao) SpringUtils
				.getBean("consultasDao");
		tiposconsulta = consultasDao.getConsultas(userDetails.getUsername());
		return tiposconsulta;
	}
	/**
	 * Consulta1: Area x ciudad
	 * @return
	 */
	public List<Areaxciudad> getAreaxciudad() {
		ConsultasDao consultasDao = (ConsultasDao) SpringUtils
				.getBean("consultasDao");
		areaxciudad=consultasDao.getAreaxciudad();		
		return areaxciudad;
	}
	public TiposConsulta getSelectedTiposconsulta() {
		return selectedTiposconsulta;
	}
	public void setSelectedTiposconsulta(TiposConsulta selectedTiposconsulta) {
		this.selectedTiposconsulta = selectedTiposconsulta;
	}
	
	
}
