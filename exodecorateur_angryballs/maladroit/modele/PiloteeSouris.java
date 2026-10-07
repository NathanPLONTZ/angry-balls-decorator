package exodecorateur_angryballs.maladroit.modele;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import exodecorateur_angryballs.maladroit.ControleurEtat;
import exodecorateur_angryballs.maladroit.ControleurEtat1;
import exodecorateur_angryballs.maladroit.ControleurEtat2;
import exodecorateur_angryballs.maladroit.vues.CadreAngryBalls;

/**
 * Binds the Pilotee behaviour to the mouse, and holds the State pattern that drives it.
 *
 * The ball is either waiting to be grabbed or currently held, and these two situations answer the
 * very same mouse events in different ways. Rather than testing a flag on every event, this class
 * forwards each event to the current state object and lets the states hand over to one another:
 * ControleurEtat1 grabs the ball on a press, ControleurEtat2 drags it and releases it.
 */
public class PiloteeSouris extends Pilotee implements MouseListener, MouseMotionListener
{

public CadreAngryBalls cadre;
public ControleurEtat controleurCourant;

public PiloteeSouris(Bille billedecoree, CadreAngryBalls cadre)
{
super(billedecoree);
this.cadre=cadre;
cadre.billard.addMouseListener(this);
cadre.billard.addMouseMotionListener(this);
initialisationControleur();
}

/**
 * Builds the two states and wires them into a cycle: waiting hands over to held on a press, held
 * hands back to waiting on a release.
 */
public void initialisationControleur()
{
ControleurEtat1 etat1 = new ControleurEtat1(null);
ControleurEtat2 etat2 = new ControleurEtat2(billedecoree, etat1);
etat1.setSuivant(etat2);
controleurCourant=etat1;
}

public ControleurEtat getControleurCourant()
{
return controleurCourant;
}

public void setControleurCourant(ControleurEtat controleurCourant)
{
this.controleurCourant = controleurCourant;
}

@Override
public void mousePressed(MouseEvent e)
{
controleurCourant.mousePressed(e, this);
}

@Override
public void mouseReleased(MouseEvent e)
{
controleurCourant.mouseReleased(e, this);
}

@Override
public void mouseDragged(MouseEvent e)
{
controleurCourant.mouseDragged(e, this);
}

@Override
public String toString()
{
return description("Pilotée");
}

@Override
public void mouseMoved(MouseEvent e)
{
}

@Override
public void mouseClicked(MouseEvent e)
{
}

@Override
public void mouseEntered(MouseEvent e)
{
}

@Override
public void mouseExited(MouseEvent e)
{
}

}
