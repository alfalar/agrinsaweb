package co.geographs.agrinsa.util;

import org.springframework.context.ApplicationContext;

public final class SpringUtils {

	private SpringUtils(){
	}
	
	public static Object getBean(final String nombre){
    	final ApplicationContext ctx = AppCtxHolder.getApplicationContext();
    	return ctx.getBean(nombre);
    }
}
