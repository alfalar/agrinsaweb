package co.geographs.agrinsa.security;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.geographs.agrinsa.dao.ConstantesDao;
import co.geographs.agrinsa.dao.UsuariosDao;
import co.geographs.agrinsa.dao.business.Usuarios;
import co.geographs.agrinsa.util.SpringUtils;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {

	@Resource
	private UsuariosDao usuarios;
	@Resource
	private ConstantesDao constantes;

	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {
		UserData user = null;
		try {
			usuarios = (UsuariosDao)SpringUtils.getBean("usuariosDao");
			Usuarios usuario = usuarios.getUsuario(userName);
			//List<Roles> roles= usuarios.getRoles(userName);
			List<String> recursos=usuarios.getRecursosPermitidos(userName);	
			constantes=(ConstantesDao)SpringUtils.getBean("constantesDao");
			constantes.getConstantesJson(userName);
			if (usuario != null) {
				user = new UserData();
				user.setUsername(userName.toUpperCase());
				user.setPassword(usuario.getPassword());
				user.setEnabled(usuario.isHabilitado());
				user.setRecursos(recursos);
				user.setDetalleUsuario(usuario);
			}
			System.out.println(user.toString());
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return user;
	}

}
