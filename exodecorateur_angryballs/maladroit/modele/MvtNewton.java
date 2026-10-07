package exodecorateur_angryballs.maladroit.modele;

import java.util.Vector;

/**
 * Acceleration behaviour: universal attraction, the ball is pulled by every other ball through
 * Newton's gravitational force.
 */
public class MvtNewton extends DecorateurBille
{

public MvtNewton(Bille billedecoree)
{
super(billedecoree);
}

public void gestionAccélération(Vector<Bille> billes)
{
super.gestionAccélération(billes);                              // runs the chain down to the reset to zero
this.getAccélération().ajoute(OutilsBille.gestionAccélérationNewton(this, billes));     // pull of the other balls
}

@Override
public String toString()
{
return description("Mouvement Newton");
}

}
