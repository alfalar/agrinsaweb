dojo.provide("dojoclass.dojo.ProveedorConstantes");
dojo.declare("dojoclass.dojo.ProveedorConstantes", null, {
	setValores : function(objeto) {
		// activa evento
		//console.log(objeto);
		this.onCustomEvent(objeto);
	},
	onCustomEvent : function(arg) {
	},
	
	getConstantesGenerales : function() {
		try {
			getConstantes();
			var handle = dojo.connect(proveedorConstantes,'onCustomEvent',function(argument) {
								dojo.disconnect(handle);	
								for ( var j = 0; j < argument.length; j++) {
									var llave = argument[j].opcion;
									var valor = argument[j].nombreRecurso;
									var tipo=argument[j].tipo;
									var descripcion=argument[j].descripcion;
									console.log(llave+":"+valor+":"+tipo);
									if(llave=="MAPA_BASE"){
										mapa_base=valor;
									}else if(llave=="MENU_ADMINISTRACION"){
										menu_admon=valor;
									}else if(llave=="MENU_CONSULTAS"){
										menu_consultas=valor;	
									}else if(llave=="SERVICIO_GEOMETRIA"){
										serviciogeometria=valor;	
									}else if(llave=="SERVICIO_IMPRESION"){
										servicioimpresion=valor;											
									}else if(tipo=="SERVICIO_GEOGRAFICO"){										
										var objeto=new Object();
										objeto.tipo=llave;
										objeto.servicio=valor;
										objeto.descripcion=descripcion;
										serviciosgeograficos.push(objeto);
									}
								}
								
							   
								getMarcadores();
							});
		} catch (err) {
			alert("Error Recuperando lista de constantes generales:"
					+ err.message);
		}
	},
});