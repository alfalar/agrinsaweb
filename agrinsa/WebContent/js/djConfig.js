// host path regular expression
var pathRegex = new RegExp(/\/[^\/]+$/);
var locationPath = location.pathname.replace(pathRegex, '');


var dojoConfig = {
      parseOnLoad: true,
      packages: [{
        name: "esriTemplate",
        location: locationPath
      }, {
        name: "utilities",
        location: locationPath + '/js/application'
      }, 
      {
          name: "dojoclass",
          location: locationPath + '/dojoclass'
        }]
    };

//Variables Globales
var loading;
var map;
var clickHandler, clickListener;
var editLayers = [], editorWidget;
var webmapExtent;
var configOptions;
var measure;
var i18n;
var proveedorConstantes;
var mapa_base;
var menu_admon=undefined;
var menu_consultas=undefined;
var menu_marcadores=undefined;
var tidentify;
var tabladecontenido;
var serviciosgeograficos=new Array();
var bookmarks;
var serviciogeometria;
var servicioimpresion;
var idtematicos;