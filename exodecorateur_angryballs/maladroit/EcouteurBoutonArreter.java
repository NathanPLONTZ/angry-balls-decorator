package exodecorateur_angryballs.maladroit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Stops the animation when the user presses the "arrêter les billes" button.
 */
public class EcouteurBoutonArreter implements ActionListener
{

AnimationBilles animationBilles;

public EcouteurBoutonArreter(AnimationBilles animationBilles)
    {
    this.animationBilles = animationBilles;
    }

@Override
public void actionPerformed(ActionEvent e)
{
this.animationBilles.arrêterAnimation();
}

}
