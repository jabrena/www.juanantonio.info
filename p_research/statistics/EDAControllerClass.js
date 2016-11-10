/*
Class: ServicesControllerClass
Description: Clase que controla la interaccion de la pagina web: default.htm
Author: JAB
Info: bren@juanantonio.info
Date of Creation: 2006.04.23
*/
function EDAControllerClass(){
	this.error = false;
	
	//Encapsule HELPER DATA
	this.title;
	this.X 
};
// Heredamos de WebPageControllerClass;
EDAControllerClass.prototype = new WebPageControllerClass();

/***********************************
***** PROGRAMACION DE LOGO JAB *****
************************************/

EDAControllerClass.prototype.logo_onClick = function(){
	if(!this.error){
		URL ="../../p_home/home.htm";
		this.redirect(URL);
	}
};

/*********************************************
***** PROGRAMACION DE EVENTOS DE VENTANA *****
*********************************************/

EDAControllerClass.prototype.window_onLoad = function(){
	var helperObject = new HelperClass();
	helperObject.setTitle();

	var cbmObject = new ComboManagerClass("frmNavigation","cbNavigation");
	cbmObject.sort();
	cbmObject.selectByName('Research');
};

EDAControllerClass.prototype.window_onError = function(msg,url,ln){
	alert(msg,url,ln);
	return true;
};

EDAControllerClass.prototype.writeTitle = function(){
	//alert(this.title)
	document.write(WEB_TITLE)
	return true;
};