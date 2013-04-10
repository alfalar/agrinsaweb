package co.geographs.agrinsa.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

public class SecurityMetadataSourceCustom implements
		FilterInvocationSecurityMetadataSource {

	public List<ConfigAttribute> getAttributes(Object object) {
		StringBuilder access = new StringBuilder();
		FilterInvocation fi = (FilterInvocation) object;
		String url = fi.getRequestUrl();
		
		try {						
			if (url.contains("secure")) {
				return null;
			}
			//System.out.println("RECURSO PEDIDO:"+url);
			UserData userData = (UserData) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			List<String> recursos=userData.getRecursos();
			for(String recurso:recursos){
				//System.out.println("RECURSO CON PERMISO:"+recurso);
				if (url.contains(recurso)) {
					//System.out.println("RECURSO ACEPTADO:"+recurso);
					return null;
				}				
			}

			if (url.contains("swf") || url.contains("gif")
					|| url.contains("png") || url.contains("jpg")
					|| url.contains("primefaces")
					|| url.contains("xml")) {
				return null;
			}

			access.append(url);

		} catch (ClassCastException ex) {
			access.append(url);
		}

		return SecurityConfig.createListFromCommaDelimitedString(access
				.toString());
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
