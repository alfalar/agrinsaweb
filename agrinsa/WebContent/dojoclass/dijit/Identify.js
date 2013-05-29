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
	  this.clicmapa=dojo.connect(map, "onClick", function(evt){		 
		  var resultados=new Array();
		  map.graphics.clear();
		  var numeroresultados=0;
		  esri.show(loading);
		  var lysidentificados=false;		  
		  for ( var j = 0; j <  map.layerIds.length; j++) {
			  var layer = map.getLayer(map.layerIds[j]);				  
			  console.log("Identificar en Layer " + layer.id);
				  //console.log("El layer es del tipo " + layer.tileInfo);
				  if(layer.url!=null && typeof layer.tileInfo==="undefined"){
					  console.log("Entra a identicar "+layer.url);
					  lysidentificados=true;
					  var geom = esri.geometry.webMercatorToGeographic(evt.mapPoint);
					  ///console.log("x:"+geom.x);
					  //console.log("y:"+geom.y);
					  var geomextent = esri.geometry.webMercatorToGeographic(map.extent);
					  //console.log("xmin:"+geomextent.xmin);
					  //console.log("ymin:"+geomextent.ymin);
					  //console.log("xmax:"+geomextent.xmax);
					  //console.log("ymax:"+geomextent.ymax);
					  
				      identifyTask = new esri.tasks.IdentifyTask(layer.url);
				      identifyParams = new esri.tasks.IdentifyParameters();
				      identifyParams.tolerance = 3;
				      identifyParams.returnGeometry = true;
				      identifyParams.layerOption = esri.tasks.IdentifyParameters.LAYER_OPTION_ALL;
				      //identifyParams.layerIds = [0];
				      identifyParams.width  = map.width;
				      identifyParams.height = map.height;	       
				      identifyParams.geometry = geom;
				      identifyParams.mapExtent = geomextent;
				      //var spatialReference = new esri.SpatialReference({wkid:4326});
				      //identifyParams.spatialReference=spatialReference;
				      identifyTask.execute(identifyParams, function(idResults) {			    	  
				    	  numeroresultados++;
				    	  for ( var i = 0; i < idResults.length; i++) {	    		  
				    		  resultados.push(idResults[i]);	
				    	  } 
				    	  console.log("LLEGAN "+resultados.length+ " RESULTADOS");
				    	  var divcontiene=dojo.create("div");
				    	  var divtabla=dojo.create("div");	
				    	  divcontiene.appendChild(divtabla);				    		      
				    	  var html="<table width=\"100%\">";
					      for ( var k = 0; k < resultados.length; k++) {
					    	 var result=resultados[k];
					    	 if(k % 2==0){
					  			clase="class=\"celdaImpar\"";
					  		 }else{
					  			 clase="class=\"celdaPar\"";
					  	     }
						     html+="<tr>";
						     html+="<td class=\"TitTabla\" colspan=\"2\" align=\"center\">"+result.layerName+"</td>";			    				  
						     html+="</tr>";				    			  				    				  
						     var atributos=result.feature.attributes;
						     for (var key in atributos) {
						    	  //console.log("---------->"+key);
						    	  if (atributos.hasOwnProperty(key)) {						    
						    			if(atributos[key]=="Null" || atributos[key]==undefined ){
						    				  atributos[key]="&nbsp";
						    			}					    					  
						    			//console.log(key + " -> " + atributos[key]);
						    			if(tidentify.camposexcluidos.indexOf(key.toUpperCase())==-1){
									    	html+="<tr>";
						    				if(key=="lotes.agrinsagdb_DBO_LoteV_Area"){
						    					html+="<td "+clase+"  align=\"center\">Area</td>";
						    				}else{
						    					html+="<td "+clase+"  align=\"center\">"+key+"</td>";
						    				}									    										    			  
									    	html+="<td "+clase+"  align=\"center\">"+atributos[key]+"</td>";
									    	html+="</tr>";			    			  			    						  
						    			}
						    	  }
						     }
					    			  
			    		  }
					      html+="</table>";
					      divtabla.innerHTML= html;				    		  			    		  				    		  
				    	  map.infoWindow.setContent(divcontiene);    		 
				    	  map.infoWindow.show(evt.screenPoint, map.getInfoWindowAnchor(evt.screenPoint));
				    	  esri.hide(loading);				    	  
				      });		  	  		  				  					  
				  }			  			  
		  }	
		  if(lysidentificados==false){
			  setMensaje("No hay capas para identificar","INFO");
			  esri.hide(loading);
		  }
	  });
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
