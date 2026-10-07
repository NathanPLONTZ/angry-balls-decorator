package exodecorateur_angryballs.maladroit;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import exodecorateur_angryballs.maladroit.modele.Bille;
import exodecorateur_angryballs.maladroit.modele.PiloteeSouris;
import mesmaths.geometrie.base.Vecteur;

/**
 * State "held by the user".
 *
 * Every drag turns the movement of the pointer into an acceleration applied to the ball, so the
 * hand behaves like one more force rather than teleporting the ball around: the ball keeps
 * colliding with the others and keeps obeying its own behaviours while it is held.
 *
 * The push is divided by the mass of the ball, which is what makes a big ball harder to throw than
 * a small one: the hand has to move faster to get the same result.
 *
 * Only the last two pointer positions are kept, since the push is derived from the latest movement.
 */
public class ControleurEtat2 extends ControleurEtat
{

public final static double SENSIBILITE = 2;

private ArrayList<Vecteur> historiquePosition = new ArrayList<>();

public ControleurEtat2(Bille billepilotee, ControleurEtat suivant)
{
super(suivant);
historiquePosition.add(new Vecteur(billepilotee.getPosition()));
historiquePosition.add(new Vecteur(billepilotee.getPosition()));
}

@Override
public void mouseDragged(MouseEvent arg0, PiloteeSouris bille)
{
historiquePosition.remove(0);
historiquePosition.add(sourisToVecteur(arg0));
Vecteur s1s2 = new Vecteur(historiquePosition.get(1).difference(historiquePosition.get(0)));
s1s2.multiplie(SENSIBILITE/bille.masse());
bille.getAccélération().ajoute(s1s2);
}

@Override
public void mouseReleased(MouseEvent arg0, PiloteeSouris bille)
{
bille.setControleurCourant(bille.getControleurCourant().getSuivant());
}

@Override
public void mousePressed(MouseEvent arg0, PiloteeSouris bille)
{
}

}
