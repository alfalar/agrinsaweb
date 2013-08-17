package co.geographs.agrinsa.security;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import co.geographs.agrinsa.dao.business.Roles;
import co.geographs.agrinsa.dao.business.Usuarios;

public class UserData implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private List<String> recursos;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private boolean isAdmRol;
	private Usuarios detalleUsuario;
	private List<Roles> listaroles;
	
	
	public boolean isAdmRol() {
		return isAdmRol;
	}

	public void setAdmRol(boolean isAdmRol) {
		this.isAdmRol = isAdmRol;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	

	public List<String> getRecursos() {
		return recursos;
	}

	public void setRecursos(List<String> recursos) {
		this.recursos = recursos;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public Usuarios getDetalleUsuario() {
		return detalleUsuario;
	}

	public void setDetalleUsuario(Usuarios detalleUsuario) {
		this.detalleUsuario = detalleUsuario;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
	public List<Roles> getListaroles() {
		return listaroles;
	}

	public void setListaroles(List<Roles> listaroles) {
		this.listaroles = listaroles;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new LinkedList<GrantedAuthority>();
		for(String r: recursos){
			list.add(new GrantedAuthorityImpl(r));	
		}
		
		return list;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).append(": ");
		sb.append("Username: ").append(this.username).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("Enabled: ").append(this.enabled).append("; ");
		sb.append("AccountNonExpired: ").append(this.accountNonExpired).append(
				"; ");
		sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired)
				.append("; ");
		sb.append("AccountNonLocked: ").append(this.accountNonLocked).append(
				"; ");

		if (this.getAuthorities() != null) {
			sb.append("Granted Authorities: ");

			for (GrantedAuthority g : this.getAuthorities()) {
				sb.append(", ");
				sb.append(g.toString());
			}
		} else {
			sb.append("Not granted any authorities");
		}
		sb.append("; ");
		
		return sb.toString();
	}
}
