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
			var handle = dojo
					.connect(
							proveedorConstantes,
							'onCustomEvent',
							function(argument) {
								dojo.disconnect(handle);	
								for ( var j = 0; j < argument.length; j++) {
									var llave = argument[j].opcion;
									var valor = argument[j].nombreRecurso;
									console.log(llave+":"+valor);
									if(llave=="MAPA_BASE"){
										mapa_base=valor;
									}
								}
								
							    createApp();
							});
		} catch (err) {
			alert("Error Recuperando lista de constantes generales:"
					+ err.message);
		}
	},
});