define("dojoclass/dijit/Bookmarks", ["dijit", "dojo", "dojox", "dojo/require!esri/map,esri/geometry"], function (_1, _2, _3) {
    _2.provide("dojoclass.dijit.Bookmarks");
    _2.require("esri.map");
    _2.require("esri.geometry");
    _2.declare("dojoclass.dijit.BookmarkItem", null, {
        constructor: function (_4) {
            this.name = _4.name;
            this.extent = _4.extent;
            this.id= _4.id;
        },
        toJson: function () {
            var _5 = {};
            var _6 = this.extent.toJson();
            _5.extent = {
                spatialReference: _6.spatialReference,
                xmax: _6.xmax,
                xmin: _6.xmin,
                ymax: _6.ymax,
                ymin: _6.ymin
            };
            _5.name = this.name;
            _5.id=this.id;
            return _5;
        }
    });
    _2.declare("dojoclass.dijit.Bookmarks", null, {
    	initBookmarks:new Array(),
        constructor: function (_7, _8) {
            this.map = _7.map;
            this.editable = _7.editable;
            this.initBookmarks = _7.bookmarks;
            this._clickHandlers = this._mouseOverHandlers = this._mouseOutHandlers = this._removeHandlers = this._editHandlers = [];
            this.bookmarkDomNode = _2.create("div");
            _2.addClass(this.bookmarkDomNode, "esriBookmarks");
            this.bookmarkTable = _2.create("table");
            _2.addClass(this.bookmarkTable, "esriBookmarkTable");
            this.bookmarkDomNode.appendChild(this.bookmarkTable);
            _8 = _2.byId(_8);
            _8.appendChild(this.bookmarkDomNode);    
        	this._addInitialBookmarks();
        },
        onClick: function () {},
        onEdit: function () {},
        onRemove: function () {},
        addBookmark: function (_9) {
            var _a;
            if (_9.declaredClass == "dojoclass.dijit.BookmarkItem") {
                _a = _9;
                this.bookmarks.push(_a);
            } else {
                var _b = new esri.geometry.Extent(_9.extent);
                _a = new dojoclass.dijit.BookmarkItem({
                    name: _9.name,
                    extent: _b
                });
                this.bookmarks.push(_a);
            }
            var _c;
            if (this.editable) {
                var _d = esri.bundle.widgets.bookmarks;
                console.log(_d);
                var _e = _d.NLS_bookmark_edit;
                var _f = _d.NLS_bookmark_remove;
                _c = _2.create("div", {
                    innerHTML: "<div class='esriBookmarkLabel'>" + _9.name + "</div><div title='" + _f + "' class='esriBookmarkRemoveImage'><br/></div><div title='" + _e + "' class='esriBookmarkEditImage'><br/></div>"
                });
                var _10 = _2.query(".esriBookmarkEditImage", _c)[0];
                var _11 = _2.query(".esriBookmarkRemoveImage", _c)[0];
                this._removeHandlers.push(_2.connect(_11, "onclick", this, "_removeBookmark"));
                this._editHandlers.push(_2.connect(_10, "onclick", this, "_editBookmarkLabel"));
            } else {
                _c = _2.create("div", {
                    innerHTML: "<div class='esriBookmarkLabel' style='width: 210px;'>" + _9.name + "</div>"
                });
            }
            _2.addClass(_c, "esriBookmarkItem");
            var _12;
            if (_9.extent.declaredClass == "esri.geometry.Extent") {
                _12 = _9.extent;
            } else {
                _12 = new esri.geometry.Extent(_9.extent);
            }
            var _13 = _2.query(".esriBookmarkLabel", _c)[0];
            this._clickHandlers.push(_2.connect(_13, "onclick", _2.hitch(this, "_onClickHandler", _9)));
            this._mouseOverHandlers.push(_2.connect(_c, "onmouseover", function () {
                _2.addClass(this, "esriBookmarkHighlight");
            }));
            this._mouseOutHandlers.push(_2.connect(_c, "onmouseout", function () {
                _2.removeClass(this, "esriBookmarkHighlight");
            }));
            var _14 = this.bookmarkTable;
            var _15;
            if (this.editable) {
                _15 = _14.rows.length - 1;
            } else {
                _15 = _14.rows.length;
            }
            var _16 = _14.insertRow(_15);
            var _17 = _16.insertCell(0);
            _17.appendChild(_c);
        },
        removeBookmark: function (_18) {
            var _19 = _2.query(".esriBookmarkLabel", this.bookmarkDomNode);
            var _1a = _2.filter(_19, function (_1b) {
                return _1b.innerHTML == _18;
            });
            _2.forEach(_1a, function (_1c) {
                _1c.parentNode.parentNode.parentNode.parentNode.removeChild(_1c.parentNode.parentNode.parentNode);
            });
            for (var i = this.bookmarks.length - 1; i >= 0; i--) {
                if (this.bookmarks[i].name == _18) {
                    this.bookmarks.splice(i, 1);
                }
            }
            this.onRemove();
        },
        hide: function () {
            esri.hide(this.bookmarkDomNode);
        },
        show: function () {
            esri.show(this.bookmarkDomNode);
        },
        destroy: function () {
            this.map = null;
            _2.forEach(this._clickHandlers, function (_1d, idx) {
                _2.disconnect(_1d);
            });
            _2.forEach(this._mouseOverHandlers, function (_1e, idx) {
                _2.disconnect(_1e);
            });
            _2.forEach(this._mouseOutHandlers, function (_1f, idx) {
                _2.disconnect(_1f);
            });
            _2.forEach(this._removeHandlers, function (_20, idx) {
                _2.disconnect(_20);
            });
            _2.forEach(this._editHandlers, function (_21, idx) {
                _2.disconnect(_21);
            });
            _2.destroy(this.bookmarkDomNode);
        },
        toJson: function () {
            var _22 = [];
            _2.forEach(this.bookmarks, function (_23, idx) {
                _22.push(_23.toJson());
            });
            return _22;
        },
        _addInitialBookmarks: function () {
            if (this.editable) {
                var _24 = esri.bundle.widgets.bookmarks;
                var _25 = _24.NLS_add_bookmark;
                var _26 = _2.create("div", {
                    innerHTML: "<div>" + _25 + "</div>"
                });
                _2.addClass(_26, "esriBookmarkItem");
                _2.addClass(_26, "esriAddBookmark");
                this._clickHandlers.push(_2.connect(_26, "onclick", this, this._newBookmark));
                this._mouseOverHandlers.push(_2.connect(_26, "onmouseover", function () {
                    _2.addClass(this, "esriBookmarkHighlight");
                }));
                this._mouseOutHandlers.push(_2.connect(_26, "onmouseout", function () {
                    _2.removeClass(this, "esriBookmarkHighlight");
                }));
                var _27 = this.bookmarkTable;
                var _28 = _27.insertRow(0);
                var _29 = _28.insertCell(0);
                _29.appendChild(_26);
                var salvar = _2.create("div", {
                    //innerHTML: "<div>Salvar</div>"
                    innerHTML: "<div><a href=\"#\">Salvar Bookmarks</a></div>"
                });
                //_2.addClass(salvar, "esriBookmarkItem");
                //_2.addClass(salvar, "esriAddBookmark");
                this._clickHandlers.push(_2.connect(salvar, "onclick", this, this._salvar));
                //this._mouseOverHandlers.push(_2.connect(salvar, "onmouseover", function () {
                //    _2.addClass(this, "esriBookmarkHighlight");
                //}));
                //this._mouseOutHandlers.push(_2.connect(salvar, "onmouseout", function () {
                //    _2.removeClass(this, "esriBookmarkHighlight");
                //}));
                
                _29.appendChild(salvar);

            }
            this.bookmarks = [];
            _2.forEach(this.initBookmarks, function (_2a, idx) {
                this.addBookmark(_2a);
            }, this);
        },
        _removeBookmark: function (e) {
            this.bookmarks.splice(e.target.parentNode.parentNode.parentNode.rowIndex, 1);
            e.target.parentNode.parentNode.parentNode.parentNode.removeChild(e.target.parentNode.parentNode.parentNode);
            this.onRemove();
        },
        _editBookmarkLabel: function (e) {
            var _2b = e.target.parentNode;
            var _2c = _2.position(_2b, true);
            var y = _2c.y;
            var _2d = _2.create("div", {
                innerHTML: "<input type='text' class='esriBookmarkEditBox' style='left:" + _2c.x + "px; top:" + y + "px;'/>"
            });
            this._inputBox = _2.query("input", _2d)[0];
            this._label = _2.query(".esriBookmarkLabel", _2b)[0];
            var _2e = esri.bundle.widgets.bookmarks;
            var _2f = _2e.NLS_new_bookmark;
            if (this._label.innerHTML == _2f) {
                this._inputBox.value = "";
            } else {
                this._inputBox.value = this._label.innerHTML;
            }
            _2.connect(this._inputBox, "onkeyup", this, function (key) {
                switch (key.keyCode) {
                case _2.keys.ENTER:
                    this._finishEdit();
                    break;
                default:
                    break;
                }
            });
            _2.connect(this._inputBox, "onblur", this, "_finishEdit");
            _2b.appendChild(_2d);
            this._inputBox.focus();
        },
        _finishEdit: function () {
            this._inputBox.parentNode.parentNode.removeChild(this._inputBox.parentNode);
            var _30 = esri.bundle.widgets.bookmarks;
            var _31 = _30.NLS_new_bookmark;
            if (this._inputBox.value == "") {
                this._label.innerHTML = _31;
            } else {
                this._label.innerHTML = this._inputBox.value;
            }
            var _32 = _2.query(".esriBookmarkLabel", this.bookmarkDomNode);
            _2.forEach(this.bookmarks, function (_33, idx) {
                _33.name = _32[idx].innerHTML;
            });
            this.onEdit();
        },
        _newBookmark: function () {
            var map = this.map,
                _34 = esri.bundle.widgets.bookmarks,
                _35 = _34.NLS_new_bookmark,
                _36 = map.extent,
                _37;
            if (map.spatialReference._isWrappable()) {
                var _38 = esri.geometry.Extent.prototype._normalizeX(_36.xmin, map.spatialReference._getInfo()).x;
                var _39 = esri.geometry.Extent.prototype._normalizeX(_36.xmax, map.spatialReference._getInfo()).x;
                if (_38 > _39) {
                    var _3a = map.spatialReference.isWebMercator(),
                        _3b = _3a ? 20037508.342788905 : 180,
                        _3c = _3a ? -20037508.342788905 : -180,
                        _3d, _3e;
                    if (Math.abs(_38 - _3b) > Math.abs(_39 - _3c)) {
                        _3d = _38;
                        _3e = _3b;
                    } else {
                        _3d = _3c;
                        _3e = _39;
                    }
                    _37 = new esri.geometry.Extent(_3d, _36.ymin, _3e, _36.ymax, map.spatialReference);
                } else {
                    _37 = new esri.geometry.Extent(_38, _36.ymin, _39, _36.ymax, map.spatialReference);
                }
            } else {
                _37 = _36;
            }
            var _3f = new dojoclass.dijit.BookmarkItem({
                "name": _35,
                "extent": _37
            });
            this.addBookmark(_3f);
            var _40 = _2.query(".esriBookmarkItem", this.bookmarkDomNode);
            var _41 = _40[_40.length - 2];
            var e = {
                "target": {
                    "parentNode": null
                }
            };
            e.target.parentNode = _41;
            this._editBookmarkLabel(e);
           
        },
        _salvar: function () {  
        	var data=new Array();
            _2.forEach(this.bookmarks, function (bookmark, idx) {
            	/*var esta=false;
                _2.forEach(this.initBookmarks, function (bookmarkinicial, idx) {             
                	if(bookmark.name==bookmarkinicial.name){
                		esta=true;                		
                	}                    
                }, this);
                if(esta==false){*/
	               	 //LLEVARLOS A LA DB   
                   var objeto=new Object();
                   var bookmarkagr=bookmark.extent.xmin+";"+bookmark.extent.ymin+";"+bookmark.extent.xmax+";"+bookmark.extent.ymax+";"+bookmark.name;
                   objeto.marcador_id=bookmark.id;
                   objeto.marcador=bookmarkagr;
                   data.push(objeto);
	               this.initBookmarks.push(bookmark);
               //}                
            }, this);
            var datajson = dojo.toJson(data);	
            addMarcador(datajson);
        },
        
        _onClickHandler: function (_42) {
            var _43 = _42.extent;
            if (!_42.extent.declaredClass) {
                _43 = new esri.geometry.Extent(_42.extent);
            }            
            _43 = esri.geometry.webMercatorToGeographic(_43);
            /*
            s = "XMin: "+ _43.xmin
	  	      +" YMin: " + _43.ymin
	  	      +" XMax: " + _43.xmax
	  	      +" YMax: " + _43.ymax;
	  		console.log(s);
	  		console.log(_43.spatialReference.wkid);
	  		*/
            this.map.setExtent(_43);
            this.onClick();
        }
    });
});