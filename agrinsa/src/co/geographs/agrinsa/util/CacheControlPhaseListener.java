package co.geographs.agrinsa.util;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

public class CacheControlPhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhaseId getPhaseId()
	{
		return PhaseId.RENDER_RESPONSE;
	}



	public void beforePhase(final PhaseEvent event)
	{
		//System.out.println("beforePhase CacheControlPhaseListener");
		final FacesContext facesContext = event.getFacesContext();
		final HttpServletResponse response = (HttpServletResponse) facesContext
				.getExternalContext().getResponse();
		response.addHeader("pragma", "no-cache");
		response.addHeader("cache-Control", "no-cache");
		response.addHeader("cache-Control", "no-store");
		response.addHeader("cache-Control", "must-revalidate");
		response.addHeader("expires", "Mon, 8 Aug 2006 10:00:00 GMT");
	}
	
	@Override	
	public void afterPhase(final PhaseEvent event)
	{
		/**
		 * Funcion sobrecargada
		 */
	}
	

}
