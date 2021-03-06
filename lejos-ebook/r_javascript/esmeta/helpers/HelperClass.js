function HelperClass(){
	this.site = null;
	this.area = null;
	this.title = null;
	this.blParent = false;
	this.parent = null;
	this.element = " >> ";

	this.error = false;

	this.HelperClass_Constructor();
};

HelperClass.prototype.getTitle = function(){
	//alert(this.title)
	return(this.title)
}

HelperClass.prototype.writeTitle = function(){
	document.write(this.title)
	return(this.title)
}

HelperClass.prototype.HelperClass_Constructor = function(){
	if(document.forms["frmNavigationHelper"]){
		with(document.forms["frmNavigationHelper"]){
			if(wpSite && wpTitle && wpArea){
				this.site = wpSite.value;
				this.area = wpArea.value;
				this.title = wpTitle.value;
			}else{
				this.error = true;
			}
		}
	}
};

HelperClass.prototype.setTitle = function(){
	if(!this.error){
		with(document.forms["frmNavigationHelper"]){
			if(document.getElementById){
				if(document.getElementById('wpBlParent')){
					if(document.getElementById('wpParent')){
						this.blParent = wpBlParent.value;
						this.parent = wpParent.value;
					}else{
						this.error = true;
						return false;
					}
				}else{
					this.blParent = 0;
				}
			}else{
				this.blParent = 0;
			}
		}

		if(this.blParent == 1){
			document.title = this.site + this.element + this.parent + this.element  + this.area + this.element  + this.title;
		}else{
			document.title = this.site + this.element  + this.area + this.element  + this.title;
		}
	}
};
