package exodecorateur_angryballs.maladroit;

import java.awt.event.MouseEvent;

import exodecorateur_angryballs.maladroit.modele.PiloteeSouris;

/**
 * State "waiting to be grabbed".
 *
 * The ball moves under its own behaviours and ignores the mouse, until the user presses the button
 * while the pointer is over it. Dragging and releasing mean nothing here.
 */
public class ControleurEtat1 extends ControleurEtat
{

public ControleurEtat1(ControleurEtat suivant)
{
super(suivant);
}

@Override
public void mousePressed(MouseEvent arg0, PiloteeSouris bille)
{
if (sourisSurBille(arg0, bille))
    {
    bille.setControleurCourant(bille.getControleurCourant().getSuivant());
    }
}

@Override
public void mouseReleased(MouseEvent arg0, PiloteeSouris bille)
{
}

@Override
public void mouseDragged(MouseEvent arg0, PiloteeSouris bille)
{
}

}
