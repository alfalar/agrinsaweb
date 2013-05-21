package co.geographs.agrinsa.beans;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import co.geographs.agrinsa.dao.ConsultasDao;
import co.geographs.agrinsa.dao.business.Areaxciudad;
import co.geographs.agrinsa.dao.business.Areaxvereda;
import co.geographs.agrinsa.dao.business.Estadolotes;
import co.geographs.agrinsa.dao.business.Lotes;
import co.geographs.agrinsa.dao.business.Resumen;
import co.geographs.agrinsa.dao.business.Sembradoporcultivo;
import co.geographs.agrinsa.dao.business.Sembradoporvariedad;
import co.geographs.agrinsa.dao.business.Totalhxaxe;
import co.geographs.agrinsa.dao.business.Totallotesxareaxvendedor;
import co.geographs.agrinsa.util.FacesUtil;
import co.geographs.agrinsa.util.SpringUtils;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

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
	//VARIABLES AREA X VARIEDAD
	private boolean areaxvariedadenabled=false;
	private List<Sembradoporvariedad> areaxvariedad;
	//VARIABLES ESTADO LOTES
	private boolean estadoenabled=false;
	private List<Estadolotes> estado;
	//VARIABLES LOTES X VENDEDOR X AREA
	private boolean lotxvenxareaenabled=false;
	private List<Totallotesxareaxvendedor> lotxvenxarea;
	//VARIABLES LOTES X ENTIDAD X AREA
	private boolean lotxentxareaenabled=false;
	private List<Totallotesxareaxvendedor> lotxentxarea;
	//ALERTA DE CORTE
	private List<Lotes> proximoscorte;
	//TOTAL HECTAREAS X AGRICULTOR X ETAPA
	private boolean hxaxeenabled=false;
	private List<Totalhxaxe> hxaxe;
	//TOTAL HECTAREAS X ETAPA
	private boolean hecxetapaenabled=false;
	private List<Totalhxaxe> hecxetapa;
	//RESUMEN
	private boolean resumenenabled=false;
	private List<Resumen> listaresumen;

	
	
	
	public void cambioconsulta() {
		ConsultasDao consultasDao = (ConsultasDao) SpringUtils
				.getBean("consultasDao");
		if(selectedTiposconsulta.equalsIgnoreCase("areaxciudad")){
			areaxciudad = consultasDao.getAreaxciudad();
			areaxciudadenabled=true;
			areaxveredaenabled=false;
			areaxcultivoenabled=false;
			areaxvariedadenabled=false;
			estadoenabled=false;
			lotxvenxareaenabled=false;
			lotxentxareaenabled=false;
			hxaxeenabled=false;
			hecxetapaenabled=false;
		}else if(selectedTiposconsulta.equalsIgnoreCase("areaxvereda")){
			areaxvereda=consultasDao.getAreaxvereda();
			areaxciudadenabled=false;
			areaxcultivoenabled=false;
			areaxvariedadenabled=false;
			areaxveredaenabled=true;
			estadoenabled=false;
			lotxvenxareaenabled=false;
			lotxentxareaenabled=false;
			hxaxeenabled=false;
			hecxetapaenabled=false;
			resumenenabled=false;
		}else if(selectedTiposconsulta.equalsIgnoreCase("totalxcultivo")){
			areaxcultivo=consultasDao.getSembradoporcultivo();
			areaxciudadenabled=false;
			areaxcultivoenabled=true;
			areaxveredaenabled=false;
			areaxvariedadenabled=false;
			estadoenabled=false;
			lotxvenxareaenabled=false;
			lotxentxareaenabled=false;
			hxaxeenabled=false;
			hecxetapaenabled=false;
			resumenenabled=false;
		}else if(selectedTiposconsulta.equalsIgnoreCase("totalxvariedad")){
			areaxvariedad=consultasDao.getSembradoporvariedad();
			areaxciudadenabled=false;
			areaxcultivoenabled=false;
			areaxveredaenabled=false;
			areaxvariedadenabled=true;	
			estadoenabled=false;
			lotxvenxareaenabled=false;
			lotxentxareaenabled=false;
			hxaxeenabled=false;
			hecxetapaenabled=false;
			resumenenabled=false;
		}else if(selectedTiposconsulta.equalsIgnoreCase("TOTALACTINACT")){
			estado=consultasDao.getEstadolotes();
			areaxciudadenabled=false;
			areaxcultivoenabled=false;
			areaxveredaenabled=false;
			areaxvariedadenabled=false;	
			estadoenabled=true;	
			lotxvenxareaenabled=false;
			lotxentxareaenabled=false;
			hxaxeenabled=false;
			hecxetapaenabled=false;
			resumenenabled=false;
		}else if(selectedTiposconsulta.equalsIgnoreCase("TOTALXAREAXVEND")){
			lotxvenxarea=consultasDao.getTotalLotesxAreaxVendedor();
			areaxciudadenabled=false;
			areaxcultivoenabled=false;
			areaxveredaenabled=false;
			areaxvariedadenabled=false;	
			estadoenabled=false;	
			lotxvenxareaenabled=true;	
			lotxentxareaenabled=false;
			hxaxeenabled=false;
			hecxetapaenabled=false;
			resumenenabled=false;
		}else if(selectedTiposconsulta.equalsIgnoreCase("TOTALXENTIDAD")){
			lotxentxarea=consultasDao.getTotalLotesEntidad();
			areaxciudadenabled=false;
			areaxcultivoenabled=false;
			areaxveredaenabled=false;
			areaxvariedadenabled=false;	
			estadoenabled=false;	
			lotxvenxareaenabled=false;	
			lotxentxareaenabled=true;	
			hxaxeenabled=false;
			hecxetapaenabled=false;
			resumenenabled=false;
		}else if(selectedTiposconsulta.equalsIgnoreCase("THXAXE")){
			hxaxe=consultasDao.getHxaxe();
			areaxciudadenabled=false;
			areaxcultivoenabled=false;
			areaxveredaenabled=false;
			areaxvariedadenabled=false;	
			estadoenabled=false;	
			lotxvenxareaenabled=false;	
			lotxentxareaenabled=false;	
			hxaxeenabled=true;	
			hecxetapaenabled=false;
			resumenenabled=false;
		}else if(selectedTiposconsulta.equalsIgnoreCase("TOTALRANGOFECHA")){
			hecxetapa=consultasDao.getTotalxEtapa();
			areaxciudadenabled=false;
			areaxcultivoenabled=false;
			areaxveredaenabled=false;
			areaxvariedadenabled=false;	
			estadoenabled=false;	
			lotxvenxareaenabled=false;	
			lotxentxareaenabled=false;	
			hxaxeenabled=false;	
			hecxetapaenabled=true;
			resumenenabled=false;
		}else if(selectedTiposconsulta.equalsIgnoreCase("RESUMEN")){
			listaresumen=consultasDao.getResumen();
			areaxciudadenabled=false;
			areaxcultivoenabled=false;
			areaxveredaenabled=false;
			areaxvariedadenabled=false;	
			estadoenabled=false;	
			lotxvenxareaenabled=false;	
			lotxentxareaenabled=false;	
			hxaxeenabled=false;	
			hecxetapaenabled=false;
			resumenenabled=true;
		}else{
			areaxvariedadenabled=false;
			areaxciudadenabled=false;
			areaxveredaenabled=false;
			areaxcultivoenabled=false;
			estadoenabled=false;
			lotxvenxareaenabled=false;
			lotxentxareaenabled=false;
			hxaxeenabled=false;
			hecxetapaenabled=false;
			resumenenabled=false;
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

	public List<Sembradoporcultivo> getAreaxcultivo() {
		return areaxcultivo;
	}

	public void setAreaxcultivo(List<Sembradoporcultivo> areaxcultivo) {
		this.areaxcultivo = areaxcultivo;
	}

	public boolean isAreaxvariedadenabled() {
		return areaxvariedadenabled;
	}

	public List<Sembradoporvariedad> getAreaxvariedad() {
		return areaxvariedad;
	}

	public boolean isEstadoenabled() {
		return estadoenabled;
	}

	public List<Estadolotes> getEstado() {
		return estado;
	}

	public List<Totallotesxareaxvendedor> getLotxvenxarea() {
		return lotxvenxarea;
	}

	public boolean isLotxvenxareaenabled() {
		return lotxvenxareaenabled;
	}

	public boolean isLotxentxareaenabled() {
		return lotxentxareaenabled;
	}

	public List<Totallotesxareaxvendedor> getLotxentxarea() {
		return lotxentxarea;
	}
	
	
	public boolean isHxaxeenabled() {
		return hxaxeenabled;
	}

	public List<Totalhxaxe> getHxaxe() {
		return hxaxe;
	}

	
	public boolean isHecxetapaenabled() {
		return hecxetapaenabled;
	}

	public List<Totalhxaxe> getHecxetapa() {
		return hecxetapa;
	}


	public boolean isResumenenabled() {
		return resumenenabled;
	}

	public List<Resumen> getListaresumen() {
		return listaresumen;
	}

	public List<Lotes> getProximoscorte() {
		ConsultasDao consultasDao = (ConsultasDao) SpringUtils
				.getBean("consultasDao");
		proximoscorte=consultasDao.getAlertasCorte();
		return proximoscorte;
	}

	public void setProximoscorte(List<Lotes> proximoscorte) {
		this.proximoscorte = proximoscorte;
	}

	public void ResumenPDF(Object document) throws IOException, BadElementException, DocumentException {  
	    Document pdf = (Document) document; 	    
	    pdf.open();	    	    	    
	    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();  
	    String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "logo_agrinsa_peq.jpg";  	  
	    pdf.add(Image.getInstance(logo)); 
	}  	

	public void GenericPDF(Object document) throws IOException, BadElementException, DocumentException {  
	    Document pdf = (Document) document; 	    
	    pdf.open();	    
	    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();  
	    String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "logo_agrinsa_peq.jpg";  	  
	    pdf.add(Image.getInstance(logo)); 
	}  	
	
	
}
