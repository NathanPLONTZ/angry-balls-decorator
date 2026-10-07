package exodecorateur_angryballs.maladroit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Starts the animation when the user presses the "lancer les billes" button.
 */
public class EcouteurBoutonLancer implements ActionListener
{

AnimationBilles animationBilles;

public EcouteurBoutonLancer(AnimationBilles animationBilles)
{
this.animationBilles = animationBilles;
}

@Override
public void actionPerformed(ActionEvent arg0)
{
this.animationBilles.lancerAnimation();
}

}
