dojo.ready(function(){		
        i18n = dojo.i18n.getLocalization("esriTemplate","template");
        var  defaults = {
        //The ID for the map from ArcGIS.com     
        webmap: "dbd1c6d52f4e447f8c01d14a691a70fe",
        //The id for the web mapping applciation item that contains configuration info - in most
        //cases this will be null. 
        appid: "",
        //set to true to display the title
        displaytitle: false,
        //Enter a title, if no title is specified, the webmap's title is used.
        title: "Agroindustriales del Tolima S.A",
        //Enter a description for the application. This description will appear in the left pane
        //if no description is entered the webmap description will be used.
        description: "",
        //specify an owner for the app - used by the print option. The default value will be the web map's owner
        owner: '',
        //Specify a color theme for the app. Valid options are gray,blue,purple,green and orange
        theme: 'blue',
        //Optional tools - set to false to hide the tool
        //set to false to hide the zoom slider on the map 
        displayslider: true,
        displaymeasure: true,
        displaybasemaps: true,
        displayoverviewmap: true,
        displayeditor: false,
        ////When editing you need to specify a proxyurl (see below) if the service is on a different domain
        //Specify a proxy url if you will be editing, using the elevation profile or have secure services or web maps that are not shared with everyone.
        proxyurl: "",
        displaylegend: true,
        displaysearch: false,
        displaylayerlist: true,
        displaybookmarks: true,
        displaydetails: false,
        displaytimeslider: true,
        displayprint: true,
        //BEGIN:AGR
        displayInformationWindow:true,
        //END:AGR
        //Print options 
        printtask: "http://utility.arcgisonline.com/arcgis/rest/services/Utilities/PrintingTools/GPServer/Export%20Web%20Map%20Task",
        //Set the label in the nls file for your browsers language
        printlayouts: [{
          layout: 'Letter ANSI A Landscape',
          label: i18n.tools.print.layouts.label1,
          format: 'PDF'
        }, {
          layout: 'Letter ANSI A Portrait',
          label: i18n.tools.print.layouts.label2,
          format: 'PDF'
        }, {
          layout: 'Letter ANSI A Landscape',
          label: i18n.tools.print.layouts.label3,
          format: 'PNG32'
        }, {
          layout: 'Letter ANSI A Portrait',
          label: i18n.tools.print.layouts.label4,
          format: 'PNG32'
        }],
        //i18n.viewer.main.scaleBarUnits,
        //The elevation tool uses the  measurement tool to draw the lines. So if this is set
        //to true then displaymeasure needs to be true too. 
        displayelevation: false,
        //This option is used when the elevation chart is displayed to control what is displayed when users mouse over or touch the chart. When true, elevation gain/loss will be shown from the first location to the location under the cursor/finger. 
        showelevationdifference: false,
        displayscalebar: true,
        displayshare: false,
        //if enabled enter bitly key and login below.
        //The application allows users to share the map with social networking sites like twitter
        //and facebook. The url for the application can be quite long so shorten it using bit.ly. 
        //You will need to provide your own bitly key and login.
        bitly: {
          key: 'R_b8a169f3a8b978b9697f64613bf1db6d',
          login: 'arcgis'
        },
        //Set to true to display the left panel on startup. The left panel can contain the legend, details and editor. Set to true to 
        //hide left panel on initial startup. 2
        leftPanelVisibility: true,
        //If the webmap uses Bing Maps data, you will need to provide your Bing Maps Key
        bingmapskey: "AveykoqqYwXEdK7uu1opSWLfQ-xYOar4gaS8sM5HdODZCR78OCGVynrlxu16bdg6",
        //Modify this to point to your sharing service URL if you are using the portal
        sharingurl: "",
        //specify a group in ArcGIS.com that contains the basemaps to display in the basemap gallery
        //example: title:'ArcGIS Online Basemaps' , owner:esri
        basemapgroup: {
          title: '',
          owner: ''
        },
        //Enter the URL to a Geometry Service 
        geometryserviceurl: "http://utility.arcgisonline.com/ArcGIS/rest/services/Geometry/GeometryServer",
        //Specify the geocoder options. By default uses the geocoder widget with the default locators. If you specify a url value then that locator will be used. 
        placefinder: {
          "url": "",
          "countryCode":"",
          "currentExtent":false,
          "placeholder": ""
        },
        //Set link text and url parameters if you want to display clickable links in the upper right-corner
        //of the application. 
        //ArcGIS.com. Enter link values for the link1 and link2 and text to add links. For example
        //url:'http://www.esri.com',text:'Esri'
        link1: {
          url: '',
          text: ''
        },
        link2: {
          url: '',
          text: ''
        },
        //specify the width of the panel that holds the editor, legend, details
        leftpanewidth: 228,
        //Restrict the map's extent to the initial extent of the web map. When true users
        //will not be able to pan/zoom outside the initial extent.
        constrainmapextent: false,
        //Provide an image and url for a logo that will be displayed as a clickable image 
        //in the lower right corner of the map. If nothing is specified then the esri logo will appear.
        customlogo: {
          image: '/agrinsa/images/logo_agrinsa_peq.jpg',
          link: 'http://www.agrinsa.com'
        },
        //embed = true means the margins will be collapsed to just include the map no title or links
        embed: true
      };

        var app = new utilities.App(defaults, defaults.geometryserviceurl, defaults.proxyurl );
        app.init().then(function(options){
    		//CREACION DEL PROVEEDOR DE CONSTANTES
        	proveedorConstantes=new dojoclass.dojo.ProveedorConstantes();
        	proveedorConstantes.getConstantesGenerales();   
        	initMap(options);
        });
      });