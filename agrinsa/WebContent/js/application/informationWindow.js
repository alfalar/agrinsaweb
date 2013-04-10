function addToolbarWindow(isVisible) {
	  //attachTo:bottom-right,bottom-left,top-right,top-left
	  //opacity: opacity of the extent rectangle - values between 0 and 1. 
	  //color: fill color of the extnet rectangle
	  //maximizeButton: When true the maximize button is displayed
	  //expand factor: The ratio between the size of the ov map and the extent rectangle.
	  //visible: specify the initial visibility of the ovmap.
	  var twDijit = new dojoclass.dijit.ToolBarWindow({
		map: map,
		attachTo: "top-right",
	    color: "#000000",
	    maximizeButton: false,
	    visible: isVisible,
	    id: 'toolbarInformationWindow',
	    width: 400,
	    height: 55
	  });
	  twDijit.startup();
}