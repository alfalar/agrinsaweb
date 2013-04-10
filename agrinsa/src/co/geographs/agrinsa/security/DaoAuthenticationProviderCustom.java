package co.geographs.agrinsa.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import co.geographs.agrinsa.dao.UsuariosDao;
import co.geographs.agrinsa.util.SpringUtils;

public class DaoAuthenticationProviderCustom extends
		AbstractUserDetailsAuthenticationProvider {

	private UserDetailsServiceCustom userDetailsService;
	private PasswordEncoder passwordEncoder = new PlaintextPasswordEncoder();
	private SaltSource saltSource;

	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		Object salt = null;

		if (saltSource != null) {
			salt = saltSource.getSalt(userDetails);
		}

		if (!passwordEncoder.isPasswordValid(userDetails.getPassword(),
				authentication.getCredentials().toString(), salt)) {
			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"));
		}
	}

	protected final UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		UserDetails loadedUser;

		try {
			UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
			if(usuariosDao.getUsuario(authentication.getPrincipal().toString(), authentication.getCredentials().toString())==false){
				throw new BadCredentialsException(messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.badCredentials",
						"Usuario y/o contraseña no validos"), username);													
			}			
			loadedUser = userDetailsService.loadUserByUsername(username);
			
			
		} catch (DataAccessException repositoryProblem) {
			throw new AuthenticationServiceException(
					repositoryProblem.getMessage(), repositoryProblem);
		}

		if (loadedUser == null) {
			throw new AuthenticationServiceException(
					"AuthenticationDao returned null, which is an interface contract violation");
		}

		return loadedUser;
	}

	public UserDetailsServiceCustom getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(
			UserDetailsServiceCustom userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

}
