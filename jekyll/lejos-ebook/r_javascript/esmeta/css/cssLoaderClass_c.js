// Compressed with JSCruncher.
var cssLoaderClass=new Object();cssLoaderClass.cssRel='stylesheet';cssLoaderClass.cssType='text/css';cssLoaderClass.cssPath='';cssLoaderClass.error=false;cssLoaderClass.version="1.1";cssLoaderClass.name="cssLoaderClass";cssLoaderClass.setPath=_setPath;cssLoaderClass.getPath=_getPath;cssLoaderClass.setCss=_setCss;cssLoaderClass.loadCss=_setCss;function _setPath(_path){if(this.error==false){if((typeof _path=='string')&&(_path!=null)&&(_path!=' ')&&(_path!='')){this.cssPath=_path}else{this.esmError(0,'setPath')}}};function _getPath(){return this.cssPath};function _setCss(file){if(this.error==false){if(this.cssPath!=''){var startTag='<link ';var rel='rel="stylesheet" ';var type='type="text/css" ';var charset='charset="iso-8859-1" ';var url_1='href="';var url_2='" ';var title=' title="Esmeta Component '+this.name+this.version+'"';var endTag=' />';var tag=startTag+rel+type+charset+url_1+this.cssPath+file+url_2+title+endTag;document.write(tag)}else{}}};function _esmError(id,obj){var errorArray=[];var header='EsmError Report: \n\n    ';var footer='\n\nEsmeta Systems.';errorArray[0]='No ha establecido correctamente el metodo: '+obj+' .';errorArray[1]='Algun parametro de esto no esta soportado por el componente.';errorArray[2]='Se han definido los parametros fuera del rango.';errorArray[3]='No se ha definido el path de los ficheros CSS.';errorMessage=errorArray[id];if(errorArray.length>=id){alert(header+errorArray[id]+footer)}this.error=true};