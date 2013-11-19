package co.geographs.agrinsa.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import co.geographs.agrinsa.dao.UsuariosDao;
import co.geographs.agrinsa.dao.business.Permisos;
import co.geographs.agrinsa.dao.business.Roles;
import co.geographs.agrinsa.dao.business.Usuarios;
import co.geographs.agrinsa.util.SpringUtils;

@ManagedBean(name = "administracionBean")
@SessionScoped
public class AdministracionBean {
	@ManagedProperty(value="#{constantesBean}")
	private ConstantesBean constantesBean;
	
	private DashboardModel model;  
	private String mensaje;
	//VARIABLES ROLES
	private List<Roles> roles;
	private int totalroles=0; 
	private String nombrerol;
	private Roles selectedRol;
	//VARIABLES USUARIOS
	private List<Usuarios> usuarios;
	private int totalusuarios=0; 
	private Usuarios selectedUsuario;
	private String nombreusuario;
	private String pwduser;
	private String primernombre;
	private String segundonombre;
	private String primerapellido;
	private String segundoapellido;
	private boolean habilitado=false;	
	private int vendedor;
	//VARIABLES USUARIOS ROL
	private List<Usuarios> usuariosrol;
	//VARIABLES PERMISOS
	private List<Permisos> recursos;
	//VARIABLES RECURSOS DEL ROL
	private List<Permisos> recursosrol;
	private List<SelectItem> tiposrecurso;
	private Permisos selectedRecurso;
	private String selectedTiposrecurso;
	private String recursoopcion;
	private String recursovalor;
	private String recursodesc;
	
	public List<Roles> getRoles() {
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		roles=usuariosDao.getRoles();
		totalroles=roles.size();
		return roles;
	}
	
	public int getTotalroles() {		
		return totalroles;
	}

	public void addRol(){
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		if(nombrerol.indexOf("ROLE_")!=-1){
			mensaje=usuariosDao.addRol(nombrerol);
			if(mensaje.equalsIgnoreCase("OK")){
				constantesBean.mostrarMensaje("Rol Agregado", "INFO");	
			}else{
				constantesBean.mostrarMensaje(mensaje, "ERROR");
			}								
		}else{
			constantesBean.mostrarMensaje("El rol debe comenzar con la palabra ROLE_", "WARN");
		}
		nombrerol="";
	}
	
	public void deleteRol(){
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		if(selectedRol!=null){			
			mensaje=usuariosDao.deleteRol(selectedRol);
			if(mensaje.equalsIgnoreCase("OK")){
				constantesBean.mostrarMensaje("Rol Eliminado", "INFO");	
			}else{
				constantesBean.mostrarMensaje(mensaje, "ERROR");
			}			
		}		
	}
	
	
	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	public String getNombrerol() {
		return nombrerol;
	}


	public void setNombrerol(String nombrerol) {
		this.nombrerol = nombrerol;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Roles getSelectedRol() {
		return selectedRol;
	}

	public void setSelectedRol(Roles selectedRol) {		
		this.selectedRol = selectedRol;
	}

	public void setConstantesBean(ConstantesBean constantesBean) {
		this.constantesBean = constantesBean;
	}

	
	public List<Usuarios> getUsuarios() {
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		usuarios=usuariosDao.getUsuarios();
		totalusuarios=usuarios.size();
		return usuarios;
	}

	public void setUsuarios(List<Usuarios> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuarios getSelectedUsuario() {
		return selectedUsuario;
	}

	public void setSelectedUsuario(Usuarios selectedUsuario) {
		this.selectedUsuario = selectedUsuario;
	}

	public int getTotalusuarios() {
		return totalusuarios;
	}

	public void addUsuario(){
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		Usuarios usuario=new Usuarios();
		usuario.setUsuario(nombreusuario);
		usuario.setPassword(pwduser);
		usuario.setPrimerNombre(primernombre);
		usuario.setSegundoNombre(segundonombre);
		usuario.setPrimerApellido(primerapellido);
		usuario.setSegundoApellido(segundoapellido);
		usuario.setHabilitado(isHabilitado());
		usuario.setVendedor(vendedor);
		
		mensaje=usuariosDao.addUsuario(usuario);
			if(mensaje.equalsIgnoreCase("OK")){
				constantesBean.mostrarMensaje("Usuario Agregado", "INFO");	
			}else{
				constantesBean.mostrarMensaje(mensaje, "ERROR");
			}								
		
		nombrerol="";
	}
	
	public void deleteUsuario(){
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		if(selectedUsuario!=null){			
			mensaje=usuariosDao.deleteUsuario(selectedUsuario);
			selectedUsuario=null;
			if(mensaje.equalsIgnoreCase("OK")){
				constantesBean.mostrarMensaje("Usuario Eliminado", "INFO");	
			}else{
				constantesBean.mostrarMensaje(mensaje, "ERROR");
			}			
		}		
	}
	public String getNombreusuario() {
		return nombreusuario;
	}

	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}

	public String getPwduser() {
		return pwduser;
	}

	public void setPwduser(String pwduser) {
		this.pwduser = pwduser;
	}

	public String getPrimernombre() {
		return primernombre;
	}

	public void setPrimernombre(String primernombre) {
		this.primernombre = primernombre;
	}

	public String getSegundonombre() {
		return segundonombre;
	}

	public void setSegundonombre(String segundonombre) {
		this.segundonombre = segundonombre;
	}

	public String getPrimerapellido() {
		return primerapellido;
	}

	public void setPrimerapellido(String primerapellido) {
		this.primerapellido = primerapellido;
	}

	public String getSegundoapellido() {
		return segundoapellido;
	}

	public void setSegundoapellido(String segundoapellido) {
		this.segundoapellido = segundoapellido;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	
	public int getVendedor() {
		return vendedor;
	}

	public void setVendedor(int vendedor) {
		this.vendedor = vendedor;
	}

	public List<Usuarios> getUsuariosrol() {
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		if(this.selectedRol!=null){
			usuariosrol=usuariosDao.getUsuariosRol(this.selectedRol);	
		}						
		return usuariosrol;
	}

	public void setUsuariosrol(List<Usuarios> usuariosrol) {
		this.usuariosrol = usuariosrol;
	}

	public void onEdit(RowEditEvent event) {            
        Roles rol=((Roles) event.getObject());
        UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
        mensaje=usuariosDao.updateRol(rol);
		if(mensaje.equalsIgnoreCase("OK")){
			constantesBean.mostrarMensaje("Rol Actualizado", "INFO");	
		}else{
			constantesBean.mostrarMensaje(mensaje, "ERROR");
		}	
    }
	public void onEditUsuario(RowEditEvent event) {            
        Usuarios usuario=((Usuarios) event.getObject());
        UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
        mensaje=usuariosDao.updateUsuario(usuario);
		if(mensaje.equalsIgnoreCase("OK")){
			constantesBean.mostrarMensaje("Usuario Actualizado", "INFO");	
		}else{
			constantesBean.mostrarMensaje(mensaje, "ERROR");
		}	
    }        

	public void onEditRecurso(RowEditEvent event) {            
        Permisos permiso=((Permisos) event.getObject());
        permiso.setTipo(selectedTiposrecurso);
        UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
        mensaje=usuariosDao.updatePermiso(permiso);
		if(mensaje.equalsIgnoreCase("OK")){
			constantesBean.mostrarMensaje("Recurso Actualizado", "INFO");	
		}else{
			constantesBean.mostrarMensaje(mensaje, "ERROR");
		}	
    }        
	
    public void onCancel(RowEditEvent event) {  
    }

	public DashboardModel getModel() {
		model = new DefaultDashboardModel();  
		DashboardColumn column1 = new DefaultDashboardColumn();  
        DashboardColumn column2 = new DefaultDashboardColumn();    
          
        column1.addWidget("rolessistema");  
        column1.addWidget("usuariossistema");  
        column1.addWidget("recursos");     
        column2.addWidget("usuariosroles");  
        column2.addWidget("permisosroles");            
  
        model.addColumn(column1);  
        model.addColumn(column2);  
  
		return model;
	}  
    
	public void handleReorder(DashboardReorderEvent event) {  
    }  	
      
	public void addUsertoRol(Usuarios usuarioselec){
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		if(this.selectedRol!=null){
			mensaje=usuariosDao.addUserToRol(usuarioselec, this.selectedRol);
			if(mensaje.equalsIgnoreCase("OK")){
				constantesBean.mostrarMensaje("Usuario agregado al rol", "INFO");	
			}else{
				constantesBean.mostrarMensaje(mensaje, "ERROR");
			}	
			
		}else{
			constantesBean.mostrarMensaje("No hay un rol seleccionado para agregarle este usuario", "INFO");		
		}
	}
	public void deleteUserfromRol(Usuarios usuarioselec){
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		if(this.selectedRol!=null){
			mensaje=usuariosDao.deleteUserFromRol(usuarioselec, this.selectedRol);
			if(mensaje.equalsIgnoreCase("OK")){
				constantesBean.mostrarMensaje("Usuario quitado del rol", "INFO");	
			}else{
				constantesBean.mostrarMensaje(mensaje, "ERROR");
			}	
			
		}else{
			constantesBean.mostrarMensaje("No hay un rol seleccionado para quitarle este usuario", "INFO");		
		}		
	}

	public List<Permisos> getRecursos() {
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		recursos= usuariosDao.getPermisos();
		return recursos;
	}

	public void setRecursos(List<Permisos> recursos) {
		this.recursos = recursos;
	}

	public List<Permisos> getRecursosrol() {
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		if(this.selectedRol!=null){
			recursosrol=usuariosDao.getRecursosRol(this.selectedRol);	
		}								
		return recursosrol;
	}

	public void setRecursosrol(List<Permisos> recursosrol) {
		this.recursosrol = recursosrol;
	}

	public void deleteRecursofromRol(Permisos permiso){
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		if(this.selectedRol!=null){
			mensaje=usuariosDao.deletePermisoFromRol(permiso, this.selectedRol);
			if(mensaje.equalsIgnoreCase("OK")){
				constantesBean.mostrarMensaje("Permiso quitado del rol", "INFO");	
			}else{
				constantesBean.mostrarMensaje(mensaje, "ERROR");
			}	
			
		}else{
			constantesBean.mostrarMensaje("No hay un rol seleccionado para quitarle este permiso", "INFO");		
		}				
	}

	public void addRecursotoRol(Permisos permiso){
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		if(this.selectedRol!=null){
			mensaje=usuariosDao.addRecursoToRol(permiso, this.selectedRol);
			if(mensaje.equalsIgnoreCase("OK")){
				constantesBean.mostrarMensaje("Recurso agregado al rol", "INFO");	
			}else{
				constantesBean.mostrarMensaje(mensaje, "ERROR");
			}	
			
		}else{
			constantesBean.mostrarMensaje("No hay un rol seleccionado para agregarle este recurso", "INFO");		
		}
	}

	public List<SelectItem> getTiposrecurso() {
		tiposrecurso=new ArrayList<SelectItem>();
		SelectItem select=new SelectItem("CONSULTAS","CONSULTAS");
		tiposrecurso.add(select);
		select=new SelectItem("GENERAL","GENERAL");
		tiposrecurso.add(select);
		select=new SelectItem("SERVICIO_GEOGRAFICO","SERVICIO_GEOGRAFICO");
		tiposrecurso.add(select);

		return tiposrecurso;
	}

	public void setTiposrecurso(List<SelectItem> tiposrecurso) {
		this.tiposrecurso = tiposrecurso;
	}

	public String getSelectedTiposrecurso() {
		return selectedTiposrecurso;
	}

	public void setSelectedTiposrecurso(String selectedTiposrecurso) {
		this.selectedTiposrecurso = selectedTiposrecurso;
	}
	
	public void addRecurso(){
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		Permisos permiso = new Permisos();
		permiso.setTipo(selectedTiposrecurso);
		permiso.setOpcion(recursoopcion);
		permiso.setNombreRecurso(recursovalor);
		permiso.setDescripcion(recursodesc);
		
		mensaje=usuariosDao.addRecurso(permiso);
			if(mensaje.equalsIgnoreCase("OK")){
				constantesBean.mostrarMensaje("Permiso Agregado", "INFO");	
			}else{
				constantesBean.mostrarMensaje(mensaje, "ERROR");
			}								
		
		nombrerol="";
	}

	public String getRecursoopcion() {
		return recursoopcion;
	}

	public void setRecursoopcion(String recursoopcion) {
		this.recursoopcion = recursoopcion;
	}

	public String getRecursovalor() {
		return recursovalor;
	}

	public void setRecursovalor(String recursovalor) {
		this.recursovalor = recursovalor;
	}

	public String getRecursodesc() {
		return recursodesc;
	}

	public void setRecursodesc(String recursodesc) {
		this.recursodesc = recursodesc;
	}

	
	
	public Permisos getSelectedRecurso() {
		return selectedRecurso;
	}

	public void setSelectedRecurso(Permisos selectedRecurso) {
		this.selectedRecurso = selectedRecurso;
	}

	public void deleteRecurso(){
		UsuariosDao usuariosDao = (UsuariosDao)SpringUtils.getBean("usuariosDao");
		if(selectedRecurso !=null){			
			mensaje=usuariosDao.deleteRecurso(selectedRecurso);
			if(mensaje.equalsIgnoreCase("OK")){
				constantesBean.mostrarMensaje("Recurso Eliminado", "INFO");	
			}else{
				constantesBean.mostrarMensaje(mensaje, "ERROR");
			}			
		}		
	}

 }
