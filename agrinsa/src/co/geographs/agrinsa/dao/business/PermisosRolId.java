package co.geographs.agrinsa.dao.business;

// default package
// Generated 01-mar-2013 8:02:10 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PermisosRolId generated by hbm2java
 */
@Embeddable
public class PermisosRolId implements java.io.Serializable {

	private int rolId;
	private int permisoId;

	public PermisosRolId() {
	}

	public PermisosRolId(int rolId, int permisoId) {
		this.rolId = rolId;
		this.permisoId = permisoId;
	}

	@Column(name = "rol_id", nullable = false)
	public int getRolId() {
		return this.rolId;
	}

	public void setRolId(int rolId) {
		this.rolId = rolId;
	}

	@Column(name = "permiso_id", nullable = false)
	public int getPermisoId() {
		return this.permisoId;
	}

	public void setPermisoId(int permisoId) {
		this.permisoId = permisoId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PermisosRolId))
			return false;
		PermisosRolId castOther = (PermisosRolId) other;

		return (this.getRolId() == castOther.getRolId())
				&& (this.getPermisoId() == castOther.getPermisoId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getRolId();
		result = 37 * result + this.getPermisoId();
		return result;
	}

}
