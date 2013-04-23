/**
 * @name Toolbar
 * @author: Alfredo Alarcon 
 * @fileoverview
 * <p><code>agsjs</code></p>
 */

dojo.provide('dojoclass.dijit.ToolBarWindow');
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dojo.dnd.Mover");
dojo.require("dojo.dnd.Moveable");
dojo.require("dojo.dnd.move");;


(function() {
  var link = dojo.create("link", {
    type: "text/css",
    rel: "stylesheet",
    href: dojo.moduleUrl("dojoclass.dijit", "css/ToolBarWindow.css")
  });
  dojo.doc.getElementsByTagName("head")[0].appendChild(link);
}());

/**
 * _TOCNode is a node, with 3 possible types: layer(service)|layer|legend
 * @private
 */
dojo.ready(function(){
  

dojo.declare("dojoclass.dijit.ToolBarWindow", [dijit._Widget, dijit._Templated], {
  widgetsInTemplate: true,   
  templateString: "<div id=\"webmap-toolbar-right2\" class=\"ppsaToolbarWindow\">\r\n  "+  		  				  
                  "<div  class=\"iwContainer\" dojoattachpoint=\"_body\" style=\"width: ${width}px; height: ${height}px;\">\r\n    "+
                  	"<div id=\"navToolbar\" data-dojo-type=\"dijit.Toolbar\">"+ 
                  		"<div data-dojo-type=\"dijit.form.Button\" id=\"zoomin\"  data-dojo-props=\"iconClass:'zoominIcon', onClick:function(){navToolbar.activate(esri.toolbars.Navigation.ZOOM_IN);}\">"+
                  		"</div>\r\n  "+  
                  		"<div data-dojo-type=\"dijit.form.Button\" id=\"zoomout\" data-dojo-props=\"iconClass:'zoomoutIcon', onClick:function(){navToolbar.activate(esri.toolbars.Navigation.ZOOM_OUT);}\">"+
                  		"</div>\r\n  "+
                  		"<div data-dojo-type=\"dijit.form.Button\" id=\"zoomfullext\" data-dojo-props=\"iconClass:'zoomfullextIcon', onClick:function(){navToolbar.zoomToFullExtent();}\">"+
                  		"</div>\r\n  "+
                  		"<div data-dojo-type=\"dijit.form.Button\" id=\"pan\" data-dojo-props=\"iconClass:'panIcon', onClick:function(){navToolbar.activate(esri.toolbars.Navigation.PAN);}\">"+ 
                  		"</div>\r\n  "+
                  		"<div data-dojo-type=\"dijit.form.Button\" id=\"identificar\" data-dojo-props=\"iconClass:'identificarIcon', onClick:function(){showIdentify();}\">"+
                  		"</div>\r\n "+                                    
                  		"<div data-dojo-type=\"dijit.form.Button\" id=\"capas\" data-dojo-props=\"iconClass:'capasIcon', onClick:function(){showToc();}\">"+
                  		"</div>\r\n "+                                    
                  	"</div>\r\n  "+
                  	"<div id=\"${id}\" style=\"width: 100%; height: 100%;\">\r\n      "+                                    
                  		"<div class=\"iwHighlight\" dojoattachpoint=\"_focusDiv\" title=\"${NLS_drag}\" style=\"border: 1px solid ${color}; background-color: ${color};\">"+                  
                  	"</div>\r\n    "+
                  "</div>\r\n "+                  
                  "</div>\r\n  "+
                  "<div class=\"iwButton iwController\" title=\"Mostrar Barra de Herramientas\" dojoattachpoint=\"_controllerDiv\" dojoattachevent=\"onclick: _visibilityHandler\">\r\n  "+
                  "</div>\r\n  "+
                  "<div class=\"iwButton iwMaximizer\" title=\"Mostrar Barra de Herramientas\" dojoattachpoint=\"_maximizerDiv\" dojoattachevent=\"onclick: _maximizeHandler\">\r\n  "+
                  "</div>\r\n"+                  
                  "</div>\r\n",
  basePath: dojo.moduleUrl("dojoclass.dijit"),
  constructor: function (params, srcNodeRef) {
	  dojo.mixin(this, esri.bundle.widgets.overviewMap);
      params = params || {};
      var _6 = {};
      if (srcNodeRef) {
          this._detached = true;
          _6 = dojo.coords(srcNodeRef, true);
      }
      
      this.map = params.map;
      navToolbar = new esri.toolbars.Navigation(this.map);
      this.width = params.width || _6.w || this.map.width / 4;
      this.height = params.height || _6.h || this.map.height / 4;
      this.attachTo = params.attachTo || "bottom-left";
      this.color = params.color || "#000000";
      this.opacity = params.opacity || 0.5;
      this.maximizeButton = !! params.maximizeButton;
      this.visible = !! params.visible;
      
      if (this._detached) {
          this.visible = true;
      }
      this._maximized = false;
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
      if (this._detached) {
          dojo.style(this._body, "display", "block");
          dojo.style(this._controllerDiv, "display", "none");
          dojo.style(this._maximizerDiv, "display", "none");
      } else {
          if (this.attachTo.split("-")[0] === "bottom") {
              this.domNode.insertBefore(this._maximizerDiv, this._controllerDiv);
          }
          if (!this.maximizeButton) {
              dojo.addClass(this._maximizerDiv, "iwDisabledButton");
          }
          dojo.addClass(this.domNode, {
              "top-left": "iwTL",
              "top-right": "iwTR",
              "bottom-left": "iwBL",
              "bottom-right": "iwBR"
          }[this.attachTo]);
          dojo.addClass(this._controllerDiv, "iwShow");
          dojo.addClass(this._maximizerDiv, "iwMaximize");
          if (this.visible) {
              var _b = function () {
                  this.visible = false;
                  this.show();
              };
              _b.call(this);
          }
      }
      dojo.style(this._focusDiv, "opacity", this.opacity);
  },
  destroy: function () {
      this._deactivate();
      if (this.informationWindow) {
          this.informationWindow.destroy();
      }
      this.informationWindow;
      this.inherited(arguments);
  },
  resize: function (_c) {
      if (!_c || _c.w <= 0 || _c.h <= 0) {
          return;
      }
      this._resize(_c.w, _c.h);
  },
  show: function () {
      if (!this.visible) {
          var _d = this._controllerDiv;
          _d.title = "Ocultar Barra de Herramientas";
          dojo.removeClass(_d, "iwShow");
          dojo.addClass(_d, "iwHide");
          esri.show(this._body);
          esri.show(this._maximizerDiv);
          this._initialize();
          this.visible = true;
      }
  },
  hide: function () {
      if (this.visible) {
          var _e = this._controllerDiv;
          _e.title = "Mostrar Barra de Herramientas";
          dojo.removeClass(_e, "iwHide");
          dojo.addClass(_e, "iwShow");
          if (this._maximized) {
              this._maximizeHandler();
          }
          esri.hide(this._body);
          esri.hide(this._maximizerDiv);
          this._deactivate();
          this.visible = false;
      }
  },
  _visibilityHandler: function () {
      if (this.visible) {
          this.hide();
      } else {
          this.show();
      }
  },
  _maximizeHandler: function () {
      var _f = this._maximizerDiv;
      if (this._maximized) {
          _f.title = this.NLS_maximize;
          dojo.removeClass(_f, "iwRestore");
          dojo.addClass(_f, "iwMaximize");
          this._resize(this.width, this.height);
      } else {
          _f.title = this.NLS_restore;
          dojo.removeClass(_f, "iwMaximize");
          dojo.addClass(_f, "iwRestore");
          this._resize(this.map.width, this.map.height);
      }
      this._maximized = !this._maximized;
  },
  _resize: function (dijit0, dijit1) {
      dojo.style(this._body, {
          width: dijit0 + "px",
          height: dijit1 + "px"
      });
  },
  _initialize: function () {
      if (!this.informationWindow) {
      } else {
          this._activate();
      }
  },
  _deactivate: function () {
      if (this._moveableHandle) {
          this._moveableHandle.destroy();
      }
  }

});
});