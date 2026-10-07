package exodecorateur_angryballs.maladroit;

import java.awt.event.MouseEvent;

import exodecorateur_angryballs.maladroit.modele.Pilotee;
import exodecorateur_angryballs.maladroit.modele.PiloteeSouris;
import mesmaths.geometrie.base.Vecteur;

/**
 * State of the State pattern that drives a ball the user can grab and throw.
 *
 * A piloted ball answers the same three mouse events differently depending on whether it is
 * waiting to be grabbed or currently held. Each state implements its own answer and names the
 * state to hand over to, so PiloteeSouris never has to test a flag: it just forwards every event
 * to its current state.
 *
 * The two concrete states form a cycle: ControleurEtat1 (waiting) hands over to ControleurEtat2
 * (held) on a press, which hands back to ControleurEtat1 on a release.
 */
public abstract class ControleurEtat
{

private ControleurEtat suivant;

public ControleurEtat(ControleurEtat suivant)
{
super();
this.suivant = suivant;
}

public abstract void mouseDragged(MouseEvent arg0, PiloteeSouris bille);

public abstract void mousePressed(MouseEvent arg0, PiloteeSouris bille);

public abstract void mouseReleased(MouseEvent arg0, PiloteeSouris bille);

/**
 * @return the state to switch to once this one is done
 */
public ControleurEtat getSuivant()
{
return suivant;
}

public void setSuivant(ControleurEtat suivant)
{
this.suivant = suivant;
}

/**
 * Reads the pointer position out of a mouse event, in the coordinates of the billiard table.
 *
 * Shared by the states so that they all read the pointer the same way.
 */
public static Vecteur sourisToVecteur(MouseEvent arg0)
{
return new Vecteur(arg0.getX(), arg0.getY());
}

/**
 * Tells whether the pointer is inside the ball, which is what decides if a press grabs it.
 *
 * Compares squared distances to avoid a square root.
 */
public static boolean sourisSurBille(MouseEvent arg0, Pilotee bille)
{
Vecteur souris = sourisToVecteur(arg0);
Vecteur sourisEtBilleDifference = souris.difference(bille.getPosition());
double distance = sourisEtBilleDifference.normeCarrée();
return distance <= bille.getRayon()*bille.getRayon();
}

}
