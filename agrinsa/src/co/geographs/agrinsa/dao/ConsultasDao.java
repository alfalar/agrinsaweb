package co.geographs.agrinsa.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.orm.hibernate3.HibernateTemplate;

import co.geographs.agrinsa.dao.business.Areaxciudad;
import co.geographs.agrinsa.dao.business.Areaxvereda;
import co.geographs.agrinsa.dao.business.Estadolotes;
import co.geographs.agrinsa.dao.business.Lotes;
import co.geographs.agrinsa.dao.business.Resumen;
import co.geographs.agrinsa.dao.business.Sembradoporcultivo;
import co.geographs.agrinsa.dao.business.Sembradoporvariedad;
import co.geographs.agrinsa.dao.business.TiposConsulta;
import co.geographs.agrinsa.dao.business.Totalhxaxe;
import co.geographs.agrinsa.dao.business.Totallotesxareaxvendedor;

public class ConsultasDao {
	private HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	/**
	 * 
	 * @param usuario
	 * @return
	 */
	public List<TiposConsulta> getConsultas(String usuario) {
		String consulta = "select p.opcion as value,p.nombreRecurso as label "
				+ "from Usuarios u,MiembrosRol mr,Roles r,Permisos p,PermisosRol pr "
				+ "where " + "u.usuarioId= mr.usuarios.usuarioId "
				+ "and r.rolId= mr.roles.rolId "
				+ "and r.rolId = pr.roles.rolId "
				+ "and p.permisoId= pr.permisos.permisoId "
				+ "and p.tipo='CONSULTAS' " + "and u.usuario='" + usuario + "'";
		ArrayList<Object> lista = (ArrayList) this.hibernateTemplate
				.find(consulta);
		List<TiposConsulta> recursos = new ArrayList<TiposConsulta>();
		recursos.add(new TiposConsulta("ND", "Seleccione una consulta"));
		for (int i = 0; i <= lista.size() - 1; i++) {
			Object[] obj = (Object[]) lista.get(i);
			recursos.add(new TiposConsulta((String) obj[0], (String) obj[1]));
		}
		return recursos;
	}

	/**
	 * Retorna el area cultivada por ciudad
	 */
	public List<Areaxciudad> getAreaxciudad() {
		List<Areaxciudad> arealist = new ArrayList<Areaxciudad>();
		Session sesion = this.hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		/*
		 * String consulta=
		 * "select nlote.CiudadID as ciudad, SUM(nlote.Area) as areasembrada "+
		 * "from LOTE_VW nlote "+ "group by nlote.CiudadID";
		 */
		String consulta = "select nlote.Value as ciudad, SUM(nlote.agrinsagdb_DBO_LoteV_Area) as areasembrada "
				+ "from "
				+ "(SELECT * "
				+ "FROM agrinsagdb.dbo.LOTE_VW LEFT OUTER JOIN  "
				+ "(SELECT "
				+ "codedValue.value('Code[1]','nvarchar(max)') AS Code, "
				+ "codedValue.value('Name[1]', 'nvarchar(max)') AS Value "
				+ "FROM agrinsagdb.dbo.GDB_ITEMS AS items INNER JOIN agrinsagdb.dbo.GDB_ITEMTYPES AS itemtypes "
				+ "ON items.Type = itemtypes.UUID "
				+ "CROSS APPLY items.Definition.nodes "
				+ "('/GPCodedValueDomain2/CodedValues/CodedValue') AS CodedValues(codedValue) "
				+ "WHERE itemtypes.Name = 'Coded Value Domain' "
				+ "AND items.Name = 'DomMunicipio') AS CodedValues "
				+ "ON agrinsagdb.dbo.LOTE_VW.CiudadID = CodedValues.Code) as nlote "
				+ "group by nlote.Value ";
		Query query = sesion.createSQLQuery(consulta)
				.addScalar("ciudad", Hibernate.STRING)
				.addScalar("areasembrada", Hibernate.BIG_DECIMAL);

		List<Object> lista = query.list();
		Iterator iterator = lista.iterator();
		while (iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			Areaxciudad areaxciudad = new Areaxciudad();
			String ciudad = String.valueOf(row[0]);
			if (ciudad.equalsIgnoreCase("null")) {
				ciudad = "INDEFINIDA";
			}
			areaxciudad.setCiudad(ciudad);
			areaxciudad.setArea(((BigDecimal) row[1]).doubleValue());
			arealist.add(areaxciudad);
		}
		return arealist;
	}

	/**
	 * Retorna el area cultivada por vereda
	 */
	public List<Areaxvereda> getAreaxvereda() {
		List<Areaxvereda> arealist = new ArrayList<Areaxvereda>();
		Session sesion = this.hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		String consulta = "select nlote.Value as ciudad,nlote.Vereda as vereda, SUM(nlote.agrinsagdb_DBO_LoteV_Area) as areasembrada "
				+ "from "
				+ "(SELECT * "
				+ "FROM agrinsagdb.dbo.LOTE_VW LEFT OUTER JOIN  "
				+ "(SELECT "
				+ "codedValue.value('Code[1]','nvarchar(max)') AS Code, "
				+ "codedValue.value('Name[1]', 'nvarchar(max)') AS Value "
				+ "FROM agrinsagdb.dbo.GDB_ITEMS AS items INNER JOIN agrinsagdb.dbo.GDB_ITEMTYPES AS itemtypes "
				+ "ON items.Type = itemtypes.UUID "
				+ "CROSS APPLY items.Definition.nodes "
				+ "('/GPCodedValueDomain2/CodedValues/CodedValue') AS CodedValues(codedValue) "
				+ "WHERE itemtypes.Name = 'Coded Value Domain' "
				+ "AND items.Name = 'DomMunicipio') AS CodedValues "
				+ "ON agrinsagdb.dbo.LOTE_VW.CiudadID = CodedValues.Code) as nlote "
				+ "group by nlote.Vereda,nlote.Value ";

		// String
		// consulta="select nlote.Vereda as vereda, SUM(nlote.agrinsagdb_DBO_LoteV_Area) as areasembrada "+
		// "from LOTE_VW nlote "+
		// "group by nlote.Vereda";
		Query query = sesion.createSQLQuery(consulta)
				.addScalar("ciudad", Hibernate.STRING)
				.addScalar("vereda", Hibernate.STRING)
				.addScalar("areasembrada", Hibernate.BIG_DECIMAL);
		List<Object> lista = query.list();
		Iterator iterator = lista.iterator();
		while (iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			Areaxvereda areaxvereda = new Areaxvereda();
			String ciudad = String.valueOf(row[0]);
			if (ciudad.equalsIgnoreCase("null")) {
				ciudad = "INDEFINIDA";
			}
			String vereda = String.valueOf(row[1]);
			if (vereda.equalsIgnoreCase("null")) {
				vereda = "INDEFINIDA";
			}
			double area = 0;
			if (row[2] != null) {
				area = ((BigDecimal) row[2]).doubleValue();
			}
			areaxvereda.setCiudad(ciudad);
			areaxvereda.setVereda(vereda);
			areaxvereda.setArea(area);
			arealist.add(areaxvereda);
		}
		return arealist;
	}

	public List<Sembradoporcultivo> getSembradoporcultivo() {
		List<Sembradoporcultivo> arealist = new ArrayList<Sembradoporcultivo>();
		Session sesion = this.hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		String consulta = "select tipocultivo.Value as cultivo, SUM(nlote.agrinsagdb_DBO_LoteV_Area) as areasembrada "
				+ "from agrinsagdb.dbo.LOTE_VW nlote, "
				+ "(SELECT * "
				+ "FROM agrinsagdb.dbo.CULTIVOS_VW LEFT OUTER JOIN "
				+ "(SELECT "
				+ "codedValue.value('Code[1]','nvarchar(max)') AS Code, "
				+ "codedValue.value('Name[1]', 'nvarchar(max)') AS Value "
				+ "FROM agrinsagdb.dbo.GDB_ITEMS AS items INNER JOIN agrinsagdb.dbo.GDB_ITEMTYPES AS itemtypes "
				+ "ON items.Type = itemtypes.UUID "
				+ "CROSS APPLY items.Definition.nodes "
				+ "('/GPCodedValueDomain2/CodedValues/CodedValue') AS CodedValues(codedValue) "
				+ "WHERE itemtypes.Name = 'Coded Value Domain' "
				+ "AND items.Name = 'DomCultivo') AS CodedValues "
				+ "ON agrinsagdb.dbo.CULTIVOS_VW.Descripcion = CodedValues.Code) as tipocultivo "
				+ "where nlote.LoteID=tipocultivo.LoteID "
				+ "group by tipocultivo.Value ";
		Query query = sesion.createSQLQuery(consulta)
				.addScalar("cultivo", Hibernate.STRING)
				.addScalar("areasembrada", Hibernate.BIG_DECIMAL);
		List<Object> lista = query.list();
		Iterator iterator = lista.iterator();
		while (iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			Sembradoporcultivo areaxcultivo = new Sembradoporcultivo();
			areaxcultivo.setCultivo(String.valueOf(row[0]));
			areaxcultivo.setArea(((BigDecimal) row[1]).doubleValue());
			arealist.add(areaxcultivo);
		}
		return arealist;
	}

	public List<Sembradoporvariedad> getSembradoporvariedad() {
		List<Sembradoporvariedad> arealist = new ArrayList<Sembradoporvariedad>();
		Session sesion = this.hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		String consulta = "select cultivos.Value as cultivo, variedad.Value as variedad,SUM(lote.agrinsagdb_DBO_LoteV_Area) as areasembrada from "
				+ "agrinsagdb.dbo.LOTE_VW lote,"
				+ "(SELECT * "
				+ "FROM agrinsagdb.dbo.CULTIVOS_VW LEFT OUTER JOIN "
				+ "(SELECT "
				+ "codedValue.value('Code[1]','nvarchar(max)') AS Code,"
				+ "codedValue.value('Name[1]', 'nvarchar(max)') AS Value "
				+ "FROM agrinsagdb.dbo.GDB_ITEMS AS items INNER JOIN agrinsagdb.dbo.GDB_ITEMTYPES AS itemtypes "
				+ "ON items.Type = itemtypes.UUID "
				+ "CROSS APPLY items.Definition.nodes "
				+ "('/GPCodedValueDomain2/CodedValues/CodedValue') AS CodedValues(codedValue) "
				+ "WHERE itemtypes.Name = 'Coded Value Domain' "
				+ "AND items.Name = 'DomCultivo') AS CodedValues  "
				+ "ON agrinsagdb.dbo.CULTIVOS_VW.Descripcion = CodedValues.Code) as cultivos ,"
				+

				"(SELECT * "
				+ "FROM agrinsagdb.dbo.VARIEDAD_VW LEFT OUTER JOIN "
				+ "(SELECT "
				+ "codedValue.value('Code[1]','nvarchar(max)') AS Code,"
				+ "codedValue.value('Name[1]', 'nvarchar(max)') AS Value "
				+ "FROM agrinsagdb.dbo.GDB_ITEMS AS items INNER JOIN agrinsagdb.dbo.GDB_ITEMTYPES AS itemtypes "
				+ "ON items.Type = itemtypes.UUID "
				+ "CROSS APPLY items.Definition.nodes "
				+ "('/GPCodedValueDomain2/CodedValues/CodedValue') AS CodedValues(codedValue) "
				+ "WHERE itemtypes.Name = 'Coded Value Domain' "
				+ "AND items.Name = 'VariedadId') AS CodedValues "
				+ "ON agrinsagdb.dbo.VARIEDAD_VW.Nombre = CodedValues.Code) as variedad "
				+ "where lote.LoteID=cultivos.LoteID "
				+ "and cultivos.CultivoID=variedad.CultivoID "
				+ "group by cultivos.Value, variedad.Value ";
		Query query = sesion.createSQLQuery(consulta)
				.addScalar("cultivo", Hibernate.STRING)
				.addScalar("variedad", Hibernate.STRING)
				.addScalar("areasembrada", Hibernate.BIG_DECIMAL);
		List<Object> lista = query.list();
		Iterator iterator = lista.iterator();
		while (iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			Sembradoporvariedad areaxcultivovariedad = new Sembradoporvariedad();
			areaxcultivovariedad.setCultivo(String.valueOf(row[0]));
			areaxcultivovariedad.setVariedad(String.valueOf(row[1]));
			areaxcultivovariedad.setArea(((BigDecimal) row[2]).doubleValue());
			arealist.add(areaxcultivovariedad);
		}
		return arealist;
	}

	public List<Estadolotes> getEstadolotes() {
		List<Estadolotes> estadolist = new ArrayList<Estadolotes>();
		Session sesion = this.hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		String consulta = "select activos,inactivos from "
				+ "(select SUM(lotes.Estado) as activos "
				+ "from agrinsagdb.dbo.LOTE_VW lotes "
				+ "where lotes.Estado=1) as activos,"
				+ "(select SUM(lotes.Estado) as inactivos "
				+ "from agrinsagdb.dbo.LOTE_VW lotes "
				+ "where lotes.Estado=2) as inactivos";
		Query query = sesion.createSQLQuery(consulta)
				.addScalar("activos", Hibernate.INTEGER)
				.addScalar("inactivos", Hibernate.INTEGER);
		List<Object> lista = query.list();
		Iterator iterator = lista.iterator();
		while (iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			Estadolotes estado = new Estadolotes();
			int activos = 0;
			if (row[0] != null) {
				activos = (Integer) row[0];
			}
			int inactivos = 0;
			if (row[1] != null) {
				inactivos = (Integer) row[1];
			}

			estado.setActivos(activos);
			estado.setInactivos(inactivos);
			estadolist.add(estado);
		}
		return estadolist;
	}

	public List<Totallotesxareaxvendedor> getTotalLotesxAreaxVendedor() {
		List<Totallotesxareaxvendedor> arealist = new ArrayList<Totallotesxareaxvendedor>();
		Session sesion = this.hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		String consulta = "select lotes.Value as vendedor,COUNT(lotes.LoteID) as nlotes, SUM(lotes.agrinsagdb_DBO_LoteV_Area) as areasembrada "
				+ "from "
				+ "(SELECT * "
				+ "FROM agrinsagdb.dbo.LOTE_VW LEFT OUTER JOIN "
				+ "(SELECT "
				+ "codedValue.value('Code[1]','nvarchar(max)') AS Code, "
				+ "codedValue.value('Name[1]', 'nvarchar(max)') AS Value  "
				+ "FROM agrinsagdb.dbo.GDB_ITEMS AS items INNER JOIN agrinsagdb.dbo.GDB_ITEMTYPES AS itemtypes "
				+ "ON items.Type = itemtypes.UUID "
				+ "CROSS APPLY items.Definition.nodes "
				+ "('/GPCodedValueDomain2/CodedValues/CodedValue') AS CodedValues(codedValue) "
				+ "WHERE itemtypes.Name = 'Coded Value Domain' "
				+ "AND items.Name = 'DomVendedor') AS CodedValues "
				+ "ON agrinsagdb.dbo.LOTE_VW.Vendedor = CodedValues.Code) as lotes "
				+ "group by lotes.Value ";
		Query query = sesion.createSQLQuery(consulta)
				.addScalar("vendedor", Hibernate.STRING)
				.addScalar("nlotes", Hibernate.INTEGER)
				.addScalar("areasembrada", Hibernate.BIG_DECIMAL);
		List<Object> lista = query.list();
		Iterator iterator = lista.iterator();
		while (iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			Totallotesxareaxvendedor tlxaxv = new Totallotesxareaxvendedor();
			String vend = String.valueOf(row[0]);
			if (vend.equalsIgnoreCase("null")) {
				vend = "INDEFINIDO";
			}
			tlxaxv.setVendedor(vend);
			tlxaxv.setLotes((Integer) row[1]);
			tlxaxv.setArea(((BigDecimal) row[2]).doubleValue());
			arealist.add(tlxaxv);
		}
		return arealist;
	}

	public List<Totallotesxareaxvendedor> getTotalLotesEntidad() {
		List<Totallotesxareaxvendedor> arealist = new ArrayList<Totallotesxareaxvendedor>();
		Session sesion = this.hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		String consulta = "select lotes.Value as entidad,COUNT(lotes.LoteID) as nlotes, SUM(lotes.agrinsagdb_DBO_LoteV_Area) as areasembrada "
				+ "from "
				+ "(SELECT * "
				+ "FROM agrinsagdb.dbo.LOTE_VW LEFT OUTER JOIN "
				+ "(SELECT "
				+ "codedValue.value('Code[1]','nvarchar(max)') AS Code, "
				+ "codedValue.value('Name[1]', 'nvarchar(max)') AS Value  "
				+ "FROM agrinsagdb.dbo.GDB_ITEMS AS items INNER JOIN agrinsagdb.dbo.GDB_ITEMTYPES AS itemtypes "
				+ "ON items.Type = itemtypes.UUID "
				+ "CROSS APPLY items.Definition.nodes "
				+ "('/GPCodedValueDomain2/CodedValues/CodedValue') AS CodedValues(codedValue) "
				+ "WHERE itemtypes.Name = 'Coded Value Domain' "
				+ "AND items.Name = 'DomEntidad') AS CodedValues "
				+ "ON agrinsagdb.dbo.LOTE_VW.Entidad = CodedValues.Code) as lotes "
				+ "group by lotes.Value ";
		Query query = sesion.createSQLQuery(consulta)
				.addScalar("entidad", Hibernate.STRING)
				.addScalar("nlotes", Hibernate.INTEGER)
				.addScalar("areasembrada", Hibernate.BIG_DECIMAL);
		List<Object> lista = query.list();
		Iterator iterator = lista.iterator();
		while (iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			Totallotesxareaxvendedor tlxaxv = new Totallotesxareaxvendedor();
			String ent = String.valueOf(row[0]);
			if (ent.equalsIgnoreCase("null")) {
				ent = "INDEFINIDA";
			}
			tlxaxv.setVendedor(ent);
			tlxaxv.setLotes((Integer) row[1]);
			tlxaxv.setArea(((BigDecimal) row[2]).doubleValue());
			arealist.add(tlxaxv);
		}
		return arealist;
	}

	/**
	 * 
	 * @return
	 */
	public List<Lotes> getAlertasCorte() {
		List<Lotes> proximoscorte = new ArrayList<Lotes>();
		Session sesion = this.hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		String consulta = "select lotes.LoteID,lotes.NomLote,CONVERT(VARCHAR(10), lotes.FecCorteReal, 103) AS FecCorteReal "
				+ "from agrinsagdb.dbo.LOTE_VW lotes "
				+ "where lotes.FecCorteReal between GETDATE() and DATEADD(DAY, +15, GETDATE())";
		Query query = sesion.createSQLQuery(consulta)
				.addScalar("LoteID", Hibernate.INTEGER)
				.addScalar("NomLote", Hibernate.STRING)
				.addScalar("FecCorteReal", Hibernate.STRING);
		List<Object> lista = query.list();
		Iterator iterator = lista.iterator();
		while (iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			Lotes lote = new Lotes();
			lote.setLoteid((Integer) row[0]);
			lote.setNomlote((String) row[1]);

			lote.setFeccorte((String) row[2]);
			proximoscorte.add(lote);
		}
		return proximoscorte;
	}

	/**
	 * Total Hectareas por agricultor por etapa
	 * 
	 * @return
	 */
	public List<Totalhxaxe> getHxaxe() {
		List<Totalhxaxe> hxaxelist = new ArrayList<Totalhxaxe>();
		Session sesion = this.hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		String consulta = "select cultivos.EtapaCultivo as Etapa,lotes.Agricultor as Agricultor,"
				+ "SUM(lotes.agrinsagdb_DBO_LoteV_Area) as areasembrada from "
				+ "agrinsagdb.dbo.LOTE_VW lotes,agrinsagdb.dbo.CULTIVOS_VW cultivos "
				+ "where "
				+ "lotes.LoteID=cultivos.LoteID "
				+ "group by cultivos.EtapaCultivo,lotes.Agricultor";
		Query query = sesion.createSQLQuery(consulta)
				.addScalar("Etapa", Hibernate.STRING)
				.addScalar("Agricultor", Hibernate.STRING)
				.addScalar("areasembrada", Hibernate.BIG_DECIMAL);
		List<Object> lista = query.list();
		Iterator iterator = lista.iterator();
		while (iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			Totalhxaxe total = new Totalhxaxe();
			int etapa = 4;
			try {
				etapa = Integer.parseInt((String) row[0]);
			} catch (Exception e) {
			}
			String etapastr = "";
			switch (etapa) {
			case -1:
				etapastr = "0(-30-0)";
				break;
			case 0:
				etapastr = "1(1-30)";
				break;
			case 1:
				etapastr = "2(31-60)";
				break;
			case 2:
				etapastr = "3(61-90)";
				break;
			case 3:
				etapastr = "4(91+)";
				break;
			default:
				etapastr = "";
				break;
			}
			total.setEtapa(etapastr);
			total.setAgricultor((String) row[1]);
			total.setArea(((BigDecimal) row[2]).doubleValue());
			hxaxelist.add(total);
		}
		return hxaxelist;
	}

	/**
	 * Total Hectareas por etapa
	 * 
	 * @return
	 */
	public List<Totalhxaxe> getTotalxEtapa() {
		List<Totalhxaxe> totxetapalist = new ArrayList<Totalhxaxe>();
		Session sesion = this.hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		String consulta = "select cultivos.EtapaCultivo as Etapa,"
				+ "SUM(lotes.agrinsagdb_DBO_LoteV_Area) as areasembrada from "
				+ "agrinsagdb.dbo.LOTE_VW lotes,agrinsagdb.dbo.CULTIVOS_VW cultivos "
				+ "where " + "lotes.LoteID=cultivos.LoteID "
				+ "group by cultivos.EtapaCultivo";
		Query query = sesion.createSQLQuery(consulta)
				.addScalar("Etapa", Hibernate.STRING)
				.addScalar("areasembrada", Hibernate.BIG_DECIMAL);
		List<Object> lista = query.list();
		Iterator iterator = lista.iterator();
		while (iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			Totalhxaxe total = new Totalhxaxe();
			int etapa = 4;
			try {
				etapa = Integer.parseInt((String) row[0]);
			} catch (Exception e) {
			}
			String etapastr = "";
			switch (etapa) {
			case -1:
				etapastr = "0(-30-0)";
				break;
			case 0:
				etapastr = "1(1-30)";
				break;
			case 1:
				etapastr = "2(31-60)";
				break;
			case 2:
				etapastr = "3(61-90)";
				break;
			case 3:
				etapastr = "4(91+)";
				break;
			default:
				etapastr = "";
				break;
			}
			total.setEtapa(etapastr);
			total.setAgricultor("");
			total.setArea(((BigDecimal) row[1]).doubleValue());
			totxetapalist.add(total);
		}
		return totxetapalist;
	}

	/**
	 * 
	 * @return
	 */
	public List<Resumen> getResumen() {
		try {
			List<Resumen> resumen= new ArrayList<Resumen>();
			PreparedStatement query = null;
			ResultSet resultset=null;
			Connection conection = ((SessionFactoryImplementor) this.hibernateTemplate
					.getSessionFactory()).getConnectionProvider()
					.getConnection();
			query = conection.prepareStatement("{call getResumenAgrinsa()}");
			resultset=query.executeQuery();
			while(resultset.next()){
				Resumen ressimp=new Resumen(resultset.getString("UsuarioId"),resultset.getString("Cedula"),
						resultset.getString("Agricultor"),resultset.getString("Sucursal"),
						resultset.getString("Ciudad"),resultset.getString("Vereda"),resultset.getString("NomLote"),
						resultset.getString("TipoCultivo"),resultset.getString("Area"),
						resultset.getString("Variedad"),resultset.getString("FecSiembra"),resultset.getString("FecCorteReal"),
						resultset.getString("Edad"),resultset.getString("Propietario"),resultset.getString("Molino"),
						resultset.getString("valorsolicitud"),resultset.getString("Acta"),resultset.getString("valorvendedor"));														
				resumen.add(ressimp);
			}
			conection.close();
			return resumen;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
