/**
 * @name Identify
 * @author: Alfredo alarcon 
 * @fileoverview
 * <p><code>agsjs</code></p>
 */

dojo.provide('dojoclass.dijit.Identify');
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("esri.tasks.identify");
dojo.require("dijit.layout.ContentPane");
dojo.require("dijit.Toolbar");
dojo.require("dijit.form.Button");
dojo.require("esri.map");
dojo.require("dijit.form.ComboBox");
dojo.require("dojo.store.Memory");
dojo.require("esri.layers.FeatureLayer");
dojo.require("esri.layers.Domain");

(function() {
  var link = dojo.create("link", {
    type: "text/css",
    rel: "stylesheet",
    href: dojo.moduleUrl("dojoclass.dijit", "css/identify.css")
  });  
  dojo.doc.getElementsByTagName("head")[0].appendChild(link);
}());

 
dojo.declare("dojoclass.dijit.Identify", [dijit._Widget, dijit._Templated], {
  widgetsInTemplate: true,  
  templateString: dojo.cache("dojoclass.dijit", "templates/Identify.html"),
  symbol:null,
  camposexcluidos:null,
  climapa:null,
  featureLayer:null,
  html:"",
  divtabla:null,
  divcontiene:null,
  constructor: function (params, srcNodeRef) {
     this.map = params.map;
     this.camposexcluidos=params.camposexcluidos;

  },
  startup: function () {	  
      	this.inherited(arguments);     	 
      	 if (dojo.isIE) {
             if (!this.domNode.parentElement) {
                 this.map.container.appendChild(this.domNode);
             }
         } else {
             if (!this.domNode.parentNode) {
                 this.map.container.appendChild(this.domNode);
             }
        }      	      	
        map.infoWindow.resize(415, 200);        
        map.infoWindow.setTitle("Resultado de la identificacion");
        symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([255,0,0]), 2), new dojo.Color([255,255,0,0.25]));      	 
  },
  hide: function () {
  	  dojo.disconnect(this.clicmapa);
	  map.graphics.clear();
	  map.infoWindow.hide();	  
  },
  
  identificaToggleButton: function () {
	  dijit.byNode(this.identifica.domNode).setAttribute("checked", true);
	  dijit.byNode(this.limpia.domNode).setAttribute("checked", false);
	  this.clicmapa=dojo.connect(map, "onClick",dojo.hitch(this, function(evt){		 
		  var resultados=new Array();
		  map.graphics.clear();
		  esri.show(loading);	
		  var identificado=false;
		  for ( var j = 0; j <  map.layerIds.length; j++) {
			  var layer = map.getLayer(map.layerIds[j]);		
			  if(layer.id==idtematicos){				  
				  this.featureLayer=new esri.layers.FeatureLayer(layer.url+"/0");
				  identificado=true;
				  console.log("Identificar en Layer " + layer.id);								  
				  var geom = esri.geometry.webMercatorToGeographic(evt.mapPoint);
				  var geomextent = esri.geometry.webMercatorToGeographic(map.extent);						  
				  identifyTask = new esri.tasks.IdentifyTask(layer.url);
				  identifyParams = new esri.tasks.IdentifyParameters();
				  identifyParams.tolerance = 3;
				  identifyParams.returnGeometry = true;
				  identifyParams.layerOption = esri.tasks.IdentifyParameters.LAYER_OPTION_ALL;
				  identifyParams.layerIds = [0];
				  identifyParams.width  = map.width;
				  identifyParams.height = map.height;	       
				  identifyParams.geometry = geom;
				  identifyParams.mapExtent = geomextent;
				  //var spatialReference = new esri.SpatialReference({wkid:4326});
				  //identifyParams.spatialReference=spatialReference;
				  identifyTask.execute(identifyParams, dojo.hitch(this,function(idResults) {
					  var loteid;
					  for ( var i = 0; i < idResults.length; i++) {	    		  
					  	  resultados.push(idResults[i]);	
					  } 
					  var numres=resultados.length;
					  if(numres>0){
						  console.log("LLEGAN "+numres+ " RESULTADOS");
						  this.divcontiene=dojo.create("div");
						  this.divtabla=dojo.create("div");	
						  this.divcontiene.appendChild(this.divtabla);				    		      
						  this.html="<table width=\"100%\">";	    		  
						  for ( var k = 0; k < resultados.length; k++) {
							  			 console.log("k:"+k);
								    	 var result=resultados[k];
								    	 if(k % 2==0){
								  			clase="class=\"celdaImpar\"";
								  		 }else{
								  			 clase="class=\"celdaPar\"";
								  	     }
								    	 this.html+="<tr>";
								    	 this.html+="<td class=\"TitTabla\" colspan=\"2\" align=\"center\"> Lotes </td>";			    				  
								    	 this.html+="</tr>";				    			  				    				  
									     var atributos=result.feature.attributes;
									     for (var key in atributos) {
									    	  //console.log("---------->"+key);
									    	  if (atributos.hasOwnProperty(key)) {						    
									    			if(atributos[key]=="Null" || atributos[key]==undefined ){
									    				  atributos[key]="&nbsp";
									    			}					    					  
									    			//console.log(key + " -> " + atributos[key]);
									    			if(tidentify.camposexcluidos.indexOf(key.toUpperCase())==-1){
									    				this.html+="<tr>";
									    				if(key=="agrinsagdb.DBO.LoteV.Area"){
									    					this.html+="<td "+clase+"  align=\"center\">Area</td>";
									    				}else if(key=="Numero Lote"){
										    					loteid=atributos[key];
										    					this.html+="<td "+clase+"  align=\"center\">"+key+"</td>";
									    				}else{
									    					this.html+="<td "+clase+"  align=\"center\">"+key+"</td>";
									    				}	
									    				//var cval = this.getDomainValue(key,atributos[key]);
									    				this.html+="<td "+clase+"  align=\"center\">"+atributos[key]+"</td>";
									    				this.html+="</tr>";			    			  			    						  
									    			}
									    	  }
									     }								    			  
						   }
						   this.html+="<tr>";
						   //this.html+="<td class=\"TitTabla\" colspan=\"2\" align=\"center\"> <a href=\"#\" onclick=\"window.open('consultas/cultivoyvariedad.jsf','window')\">Ver Cultivo y Variedad</a>  </td>";
						   this.html+="<td class=\"TitTabla\" colspan=\"2\" align=\"center\">Datos de Cultivo y Variedad </td>";
					       this.html+="</tr>";	
					       //this.html+="</table>";
					       setLoteidCultivo(loteid);
						   //console.log(html);
					       map.infoWindow.show(evt.screenPoint, map.getInfoWindowAnchor(evt.screenPoint));
					   }else{
					     setMensaje("No hay se ha identificado ningun lote","INFO");
						  esri.hide(loading);
					   }
			}));				  
	   	 }    			  			  
	  }	
	  if(identificado==false){
		     setMensaje("No existe el layer de lotes","INFO");
			  esri.hide(loading);	   		 		  
	  }
	}));
  },
  
  hcsetLoteidCultivo: function (xhr, status, args) {
	  //console.log("hcsetLoteidCultivo");
	   var lista = args.listacyv;
	   var listado = dojo.fromJson(lista);
	   for(var i=0;i<listado.length;i++){		
			var edad=listado[i].edad;
			var etapa=listado[i].etapa;
			var descripcion=listado[i].descripcion;
			var variedad=listado[i].variedad;
			this.html+="<tr>";
			this.html+="<td class=\"celdaImpar\" align=\"center\">Edad</td>";
			this.html+="<td class=\"celdaImpar\" align=\"center\">"+edad+"</td>";			    				  
		    this.html+="</tr>";	
		    this.html+="<tr>";
			this.html+="<td class=\"celdaImpar\" align=\"center\">Etapa</td>";
			this.html+="<td class=\"celdaImpar\" align=\"center\">"+etapa+"</td>";			    				  
		    this.html+="</tr>";				
		    this.html+="<tr>";
			this.html+="<td class=\"celdaImpar\" align=\"center\">Descripcion</td>";
			this.html+="<td class=\"celdaImpar\" align=\"center\">"+descripcion+"</td>";			    				  
		    this.html+="</tr>";			    
		    this.html+="<tr>";
			this.html+="<td class=\"celdaImpar\" align=\"center\">Variedad</td>";
			this.html+="<td class=\"celdaImpar\" align=\"center\">"+variedad+"</td>";			    				  
		    this.html+="</tr>";			    
	   }
	   this.html+="</table>";
	   //console.log(this.html);
	   this.divtabla.innerHTML= this.html;				    		  			    		  				    		  
	   map.infoWindow.setContent(this.divcontiene);    		 	   
	   esri.hide(loading);								      	  
  },	  
  getDomainValue: function(fieldName,code){
	   console.log(fieldName+","+ code);
		var returnValue = "";
		var cDomain=new esri.layers.Domain();
		//console.log(this.featureLayer.fields.length);
		dojo.forEach(this.featureLayer.fields, function(fld){	
			//console.log(fld.name+","+fieldName);
			if(fld.name == fieldName){
				console.log(fld);
				cDomain = fld.domain;
				if (cDomain){					
					dojo.forEach(cDomain.codedValues, function(cVal){
						console.log("Hay dominio:"+cVal.code+","+code);
						if(cVal.code == code)
							returnValue = cVal.name;
					})
				}else{
					console.log("Dominio no valido");
				}
			}
		})
		if(returnValue==""){
			returnValue=code;
		}
		return returnValue;
	
  },  
  showFeature: function(feature){
	  map.graphics.clear();
      //feature.setSymbol(symbol);
      map.graphics.add(feature);	  
  },
  limpiaToggleButton: function () {	  
	  dijit.byNode(this.identifica.domNode).setAttribute("checked", false);
	  dijit.byNode(this.limpia.domNode).setAttribute("checked", false);
	  dojo.disconnect(this.clicmapa);
	  map.graphics.clear();
	  map.infoWindow.hide();
	  
  }
});
