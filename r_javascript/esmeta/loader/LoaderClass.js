// LoaderClass
var LoaderClass = new Object();

// LoaderClass >>> Class Variables.
LoaderClass.librarypath = '';
LoaderClass.packages = [];

// LoaderClass >>> Static Public Methods.
LoaderClass.setLibraryPath = _setLibraryPath;
LoaderClass.addPackage = _addPackage;
LoaderClass.addLibrary = _addLibrary;
LoaderClass.include = _include;

function _setLibraryPath(path) {
	if (path.substring(path.length-1)!='/') path+='/';
	this.librarypath=path;
};

function _addPackage(pckg) {
	if (this.packages[pckg]) return;
	this.packages[pckg] = { libs: [] };// Nuevo concepto de programacion para la creacion de arrays bidimensionales.
};

function _addLibrary(path,files){
	var pckg = path.substring(0,path.indexOf('.'));
	if (!pckg){
		alert("LoaderClass Error: Incorrect LoaderClass.addLibrary usage");
		return;
	}
	var name = path.substring(path.indexOf('.')+1);
	if (!this.packages[pckg]) this.addPackage(pckg);
	if (this.packages[pckg].libs[name]) {// Uso de arrays bidimensionales.
		alert("LoaderClass Error: Library "+name+" already exists");
		return;
	}
	this.packages[pckg].libs[name] = files;
};

function _include(src,pth){
	src=src.split('.');
	if (src[src.length-1] == 'js') src.length -= 1;
	var path=pth||this.librarypath||'';
	if (path.substr(path.length-1) != "/") path += "/";
	var pckg=src[0];
	var grp=src[1];
	var file=src[2];
	if (file=='*'){
		if (this.packages[pckg]) group=this.packages[pckg].libs[grp];
		if(group) for (var i=0;i<group.length;i++) document.write('<script language="Javascript1.2" src="'+path+pckg+'/'+grp+'/'+group[i]+'.js"><\/script>');
		else alert('include()\n\nThe following package could not be loaded:\n'+src+'\n\nmake sure you specified the correct path.');
	}else{
		document.write('<script language="Javascript1.2" src="'+path+src.join('/')+'.js"><\/script>');

		// A�adido para darle m�s gracia a la carga. 02/03/2003
		//window.status = src;
	}
};