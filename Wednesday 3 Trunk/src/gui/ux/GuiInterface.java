package gui.ux;

/**
 * Empty interface that only serves as a marker and designates certain classes as a type of
 * GuiInterface. Only classes that implement this interface may alter data in the UserData
 * class. 
 * 
 * @author David Tan
 */
public interface GuiInterface {
	
	/*
	 * Document last edited 27 November 2011.
	 * 
	 * Each GuiInterface type class is given a unique ID that is simply its name encoded with
	 * a 28-bit one-time pad key. The keys are listed below.
	 * 
	 * ConsolePanel:	g-{@$y-zl^}e3x}]io$|cr~c*!p!s^^)#.
	 * ControlPanel:	mmqd/myg.%w6wg:7rdh!fegj.u0bd<ju>h
	 * FactoryPanel:	mvzu.a|%m;y17-<v,cl)c)3q!dni.~;lt<
	 * KitSelection:	ce}7+[z1m#sju^~rd(x?u:vu|81x]=b.gq
	 * MainInterface:	,]/!^+rfu}y@~/_[~pt@mpnm]qrne:c(dn
	 * NonNormPanel:	d!yfi?shd[j:{cy.^eq|h4x)e5qf#q<%2w
	 * PowerPanel:		[vqy|w^dj)eb55:ywug*4e-[<o%l-}z79#
	 */
	
	/** Returns the unique ID of a GUI Interface class type. */
	public String returnPanelID();
}
