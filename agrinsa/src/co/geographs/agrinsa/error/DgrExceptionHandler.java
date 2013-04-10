package co.geographs.agrinsa.error;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.log4j.Logger;

public class DgrExceptionHandler extends ExceptionHandlerWrapper {
	private transient final ExceptionHandler wrapped;
	private static final Logger LOG = Logger.getLogger(DgrExceptionHandler.class); 
	
	public DgrExceptionHandler(final ExceptionHandler aWrapped) {     
		super();
		this.wrapped = aWrapped;   
	} 
	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {
		// Iterate over all unhandeled exceptions
		@SuppressWarnings("rawtypes")
		final Iterator iter = getUnhandledExceptionQueuedEvents().iterator();
		while (iter.hasNext()) {
			final ExceptionQueuedEvent event =(ExceptionQueuedEvent)iter.next();
			final ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event
					.getSource();
			// obtain throwable object
			final Throwable throwable = context.getException();
			LOG.error("Un error ha ocurrido", throwable);
			// here you do what ever you want with exception
			try { // log error
				LOG.error("Un error serio ha ocurrido!", throwable);
				// redirect to error view etc....
			} finally {
				// after exception is handeled, remove it from queue
				iter.remove();
			}
		}
		// let the parent handle the rest
		getWrapped().handle();
	}

}
