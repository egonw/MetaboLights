goog.provide('specview.controller.plugins.Highlight');

goog.require('specview.controller.Plugin');
goog.require('goog.debug.Logger');
goog.require('specview.model.Atom');

/**
 * @constructor
 * @extends{specview.controller.Plugin}
 */
specview.controller.plugins.Highlight = function() {
	specview.controller.Plugin.call(this);
};
goog.inherits(specview.controller.plugins.Highlight,specview.controller.Plugin);
goog.exportSymbol('specview.controller.plugins.Highlight',specview.controller.plugins.Highlight);
specview.controller.plugins.Highlight.prototype.getTrogClassId = goog.functions.constant('highlight');
specview.controller.plugins.Highlight.prototype.HIGHLIGHT_COLOR = '#4E9600';
specview.controller.plugins.Highlight.prototype.logger = goog.debug.Logger.getLogger('specview.controller.plugins.Highlight');

/*
 * These lastXX variables keep track of the last object highlighted. 
 * This is done for performance: IE (8) is sluggish at updating
 * the graphics, so re-highlighting is prevented. 
 */
specview.controller.plugins.Highlight.prototype.lastAtomHighlighted=null;
specview.controller.plugins.Highlight.prototype.lastBondHighlighted=null;
specview.controller.plugins.Highlight.prototype.lastPeakHighlighted=null;

specview.controller.plugins.Highlight.prototype.lastArrayOfAtomHighlighted=null;

specview.controller.plugins.Highlight.prototype.lastT=null;


specview.controller.plugins.Highlight.prototype.handleMouseMove = function(e) {
	var newMoleculeToDisplay;

	if(this.editorObject.findTarget(e)!=undefined) {
		this.editorObject.clearSelected();
		//this.editorObject.getOriginalElement().style.cursor = 'default';
//		var target = this.editorObject.findTarget(e);//FORMER VERSION
		//SAMY
		var target=this.editorObject.findTarget(e);//SAMY. NOT SURE IF THIS IS THE BEST

		var peakTarget=null;
		if (target instanceof specview.model.Atom) {
			if(this.lastAtomHighlighted==null || target!=this.lastAtomHighlighted) {
				if (this.lastT!=null) {
					this.lastT.highlightGroup.clear();
					if(this.lastT.highlightPeak!=undefined){
						this.lastT.highlightPeak.clear();
					}
				}
				this.lastAtomHighlighted=target;
				this.lastT=e.currentTarget;
				this.editorObject.addSelected(target);
				e.currentTarget.highlightGroup = this.highlightAtom(target);
				//LOOKING FOR THE PEAK
				var currentMetaSpecObject=this.editorObject.getSpecObject();//The metaSpec object
				var currentAtomInnerIdentifier= target.innerIdentifier;//the atom Id
				var hypotheticalPeakIdToWhichTheCurrentAtomIsLinked=target.peakMap[currentAtomInnerIdentifier];//its peak Id
				var peakObjectCorrespondingToThePeakId=currentMetaSpecObject.ArrayOfPeaks[hypotheticalPeakIdToWhichTheCurrentAtomIsLinked];//Peak
				//HIGHLIGHT THE PEAK PROVIDED IT EXISTS
				if(peakObjectCorrespondingToThePeakId){
					this.lastPeakHighlighted=peakObjectCorrespondingToThePeakId;
					e.currentTarget.highlightPeak=this.highlightPeak(peakObjectCorrespondingToThePeakId);					
				}
			}
			return true;
		}
		//Peaks
		//CAREFUL: MIGHT BE K ATOMS FOR ONE PEAK
		else if (target instanceof specview.model.Peak){
			
			var currentMetaSpecObject=this.editorObject.getSpecObject();
			if(this.lastPeakHighlighted==null || target!=this.lastPeakHighlighted){
				if(this.lastT!=null){
					this.lastT.highlightPeak.clear();
					if(this.lastT.highlightGroup!=undefined){
						this.lastT.highlightGroup.clear();
					}
				}
				this.lastPeakHighlighted=target;
				this.lastT=e.currentTarget;
				this.editorObject.addSelected(target);
				e.currentTarget.highlightPeak=this.highlightPeak(target,currentMetaSpecObject.editor);
				//LOOKING FOR THE ATOM(S)
				var currentMetaSpecObject=this.editorObject.getSpecObject();
	//			alert(currentMetaSpecObject.experienceType)
				var currentPeakIdentifier=target.peakId;
			//	alert(currentMetaSpecObject.ArrayOfSecondaryMolecules[currentMetaSpecObject.ArrayOfPeaks[currentPeakIdentifier].arrayOfSecondaryMolecules]);
				if(currentMetaSpecObject.experienceType=="ms"){
					newMoleculeToDisplay=currentMetaSpecObject.ArrayOfSecondaryMolecules[currentMetaSpecObject.ArrayOfPeaks[currentPeakIdentifier].arrayOfSecondaryMolecules];
				}
	//			alert(currentMetaSpecObject.experienceType)			
				if(currentMetaSpecObject.experienceType=="ms" && newMoleculeToDisplay!=undefined){
					currentMetaSpecObject.molecule=newMoleculeToDisplay;
					this.drawNewMolecule(currentMetaSpecObject);
					this.editorObject.spectrumRenderer.renderAxis(currentMetaSpecObject,editor.spectrumRenderer.box,'black');
					this.editorObject.spectrumRenderer.renderGrid(editor.specObject.mainSpecBox,'black',spectrumData.spectrum);
				}else{
					var arrayOfAtomToWhichThePeakIsRelated=target.atomMap[target.peakId];//Array of atom identifier: ["a1","a4" ...]
					//NOW HIGHLIGHT THE CORRESPONDING ATOMS(CAREFUL THERE MIGHT BE MULTIPLE)
					var arrayOfAtomObjectToWhichThePeakIsRelated=new Array();
					for(var k=0;k<arrayOfAtomToWhichThePeakIsRelated.length;k++){
						var atomObject=currentMetaSpecObject.ArrayOfAtoms[arrayOfAtomToWhichThePeakIsRelated[k]];
						arrayOfAtomObjectToWhichThePeakIsRelated.push(atomObject);
					}
					var atomIdentifier=arrayOfAtomToWhichThePeakIsRelated[0];
					var atom=currentMetaSpecObject.ArrayOfAtoms[atomIdentifier];
					
					this.lastArrayOfAtomHighlighted=arrayOfAtomObjectToWhichThePeakIsRelated;
					e.currentTarget.highlightGroup=this.highlightSeriesOfAtom(arrayOfAtomObjectToWhichThePeakIsRelated);
				}
			}		
		}
		
		
		
		else 
		if (target instanceof specview.model.Bond) {
			if(this.lastBondHighlighted==null || target!=this.lastBondHighlighted) {
				if (this.lastT!=null) {
					this.lastT.highlightGroup.clear();
				}
				this.lastBondHighlighted=target;
				this.lastT=e.currentTarget;
				this.editorObject.addSelected(target);
				e.currentTarget.highlightGroup = this.highlightBond(target);
			}
			return true;

		}

		/*
		else if (target instanceof specview.model.Molecule) {
			//this.editorObject.getOriginalElement().style.cursor = 'move';
			this.editorObject.addSelected(target);
			e.currentTarget.highlightGroup = this.highlightMolecule(target);
			return true;
		}
		*/
	}
	/*
	 * When the mouse is off an atom/bond, then we withdraw the highlight.
	 */
	else { 
		if (this.lastT!=null) {
			if(this.lastT.highlightGroup!=undefined){
				this.lastT.highlightGroup.clear();
			}
			if(this.lastT.highlightPeak!=undefined){
				this.lastT.highlightPeak.clear();
			}

			this.lastAtomHighlighted=null;
			this.lastBondHighlighted=null;
		}
	}

	return false;
};


specview.controller.plugins.Highlight.prototype.highlightPeak=function(peak,editor){
	return this.editorObject.spectrumRenderer.highlightOn(peak,editor);
};

specview.controller.plugins.Highlight.prototype.highlightAtom = function(atom) {
	return this.editorObject.moleculeRenderer.atomRenderer.highlightOn(atom,this.HIGHLIGHT_COLOR);
};

specview.controller.plugins.Highlight.prototype.highlightSeriesOfAtom=function(arrayOfAtom){
	return this.editorObject.moleculeRenderer.atomRenderer.highlightOnSeriesOfAtom(arrayOfAtom,this.HIGHLIGHT_COLOR);
};

specview.controller.plugins.Highlight.prototype.highlightBond = function(bond) {

	return this.editorObject.moleculeRenderer.bondRendererFactory.get(bond)
			.highlightOn(bond,this.HIGHLIGHT_COLOR);
 
};

specview.controller.plugins.Highlight.prototype.drawNewMolecule = function(currentMetaSpecObject) {
	//	alert(molecule);
//	alert(trans)
	return 					this.editorObject.setModels([currentMetaSpecObject]);
//	return this.editorObject.moleculeRenderer.render(molecule,trans);
}


/*
specview.controller.plugins.Highlight.prototype.highlightMolecule = function(
		molecule) {
	 return this.editorObject.moleculeRenderer.highlightOn(
	 molecule, this.HIGHLIGHT_COLOR);
}

specview.controller.plugins.Highlight.prototype.handleMouseDown = function(e) {
	if (e.currentTarget.highlightGroup) {
		e.currentTarget.highlightGroup.clear();
	}
	e.currentTarget.highlightGroup = undefined;
	var target = this.editorObject.findTarget(e);
	return false;

};
*/