package exodecorateur_angryballs.maladroit.vues;

import java.awt.*;
import java.awt.event.ItemListener;
import java.util.Vector;

import exodecorateur_angryballs.maladroit.modele.Bille;

import musique.SonLong;
import outilsvues.EcouteurTerminaison;

import outilsvues.Outils;

/**
 * Main window: the billiard table, the two buttons that start and stop the balls, and the row of
 * radio buttons that pick the sound of the screaming ball.
 */
public class CadreAngryBalls extends Frame implements VueBillard
{

TextField présentation;
public Billard billard;
public Button lancerBilles, arrêterBilles;
Panel haut, centre, bas, ligneBoutonsLancerArrêt;
PanneauChoixHurlement ligneBoutonsChoixHurlement;

EcouteurTerminaison ecouteurTerminaison;

public CadreAngryBalls(String titre, String message, Billard billard, Vector<Bille> billes, SonLong [] hurlements, int choixHurlementInitial) throws HeadlessException
{
super(titre);
Outils.place(this, 0.33, 0.33, 0.5, 0.5);
this.ecouteurTerminaison = new EcouteurTerminaison(this);


this.haut = new Panel(); this.haut.setBackground(Color.LIGHT_GRAY);
this.add(this.haut,BorderLayout.NORTH);

this.centre = new Panel();
this.add(this.centre,BorderLayout.CENTER);

this.bas = new Panel(); this.bas.setBackground(Color.LIGHT_GRAY);
this.add(this.bas,BorderLayout.SOUTH);

this.présentation = new TextField(message, 100); this.présentation.setEditable(false);
this.haut.add(this.présentation);

this.billard = billard;
this.add(this.billard);

//------------------- laying the bottom of the frame out -------------------------------

int nombreLignes = 2, nombreColonnes = 1;

this.bas.setLayout(new GridLayout(nombreLignes, nombreColonnes));

//---------------- the start and stop buttons ------------------------------------

this.ligneBoutonsLancerArrêt = new Panel(); this.bas.add(this.ligneBoutonsLancerArrêt);


this.lancerBilles = new Button("lancer les billes"); this.ligneBoutonsLancerArrêt.add(this.lancerBilles);
this.arrêterBilles = new Button("arrêter les billes"); this.ligneBoutonsLancerArrêt.add(this.arrêterBilles);

//---------------- the row of radio buttons picking the scream ------

this.ligneBoutonsChoixHurlement = new PanneauChoixHurlement(hurlements, choixHurlementInitial); this.bas.add(this.ligneBoutonsChoixHurlement);

}

public double largeurBillard()
{
return this.billard.getWidth();
}

public double hauteurBillard()
{
return this.billard.getHeight();
}

@Override
public void miseAJour()
{
this.billard.repaint();
}

@Override
public void montrer()
{
this.setVisible(true);
}

/**
 * Registers a listener on every radio button of the scream row, so the screaming ball hears about
 * the user picking another sound while the animation runs.
 */
public void addChoixHurlementListener(ItemListener écouteurChoixHurlant)
{
int i;

for ( i = 0; i < this.ligneBoutonsChoixHurlement.boutons.length; ++i) this.ligneBoutonsChoixHurlement.boutons[i].addItemListener(écouteurChoixHurlant);

}

/**
 * Also initialises the table, which can only set its rendering strategy up once it is displayable.
 */
@Override
public void setVisible(boolean ok)
{
super.setVisible(ok);
this.billard.init();
}

}
