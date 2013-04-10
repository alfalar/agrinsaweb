package co.geographs.agrinsa.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;

import co.geographs.agrinsa.security.UserData;
import co.geographs.agrinsa.util.FacesUtil;

@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean {
	private String userName;
	private String password;
	private String grupo;
	private boolean visibleBadCredentials = false;
	private String mensaje;
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public boolean isVisibleBadCredentials() {
		return visibleBadCredentials;
	}

	public void setVisibleBadCredentials(boolean visibleBadCredentials) {
		this.visibleBadCredentials = visibleBadCredentials;
	}

	public void doLogin() throws Exception {
		final FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		
		this.visibleBadCredentials = false;
		
		externalContext.dispatch("/j_spring_security_check?j_username="
				+ this.userName + "&j_password=" + this.password);

		if (externalContext.getRemoteUser() == null) {
			Exception loginError = (Exception) externalContext.getSessionMap()
					.get(WebAttributes.AUTHENTICATION_EXCEPTION);
			if (loginError instanceof BadCredentialsException) {
				this.visibleBadCredentials = true;
				mensaje=loginError.getMessage();
			}

			if (loginError instanceof AuthenticationServiceException) {
				mensaje=loginError.getMessage();
				this.visibleBadCredentials = true;
			}

		} else {
			mensaje="";
			this.visibleBadCredentials = false;
			UserData userData = (UserData) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();			
			
		}
		context.renderResponse();
	}

	public String getMensaje() {
		return mensaje;
	}

	public String logout() {
		FacesUtil.logout();
		return "GO_LOGIN";
	}


}
