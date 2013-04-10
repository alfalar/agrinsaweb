package co.geographs.agrinsa.error;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class DgrExceptionHandlerFactory extends ExceptionHandlerFactory {

	private transient final ExceptionHandlerFactory parent;     
	
	// this injection handles jsf   
	public DgrExceptionHandlerFactory(final ExceptionHandlerFactory parent) {  
		super();
		this.parent = parent;   
	}     
	//create your own ExceptionHandler   
	@Override  
	public ExceptionHandler getExceptionHandler() {     
		return  new DgrExceptionHandler(parent.getExceptionHandler());     
	} 
}