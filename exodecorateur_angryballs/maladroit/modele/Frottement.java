package exodecorateur_angryballs.maladroit.modele;

import java.util.Vector;

import mesmaths.mecanique.MecaniquePoint;

/**
 * Acceleration behaviour: viscous friction with the air, which slows the ball down.
 *
 * The braking opposes the velocity and grows with it, so a fast ball loses speed faster than a
 * slow one.
 */
public class Frottement extends DecorateurBille
{

public Frottement(Bille billedecoree)
{
super(billedecoree);
}

public void gestionAccélération(Vector<Bille> billes)
{
super.gestionAccélération(billes);                              // runs the chain down to the reset to zero
this.getAccélération().ajoute(MecaniquePoint.freinageFrottement(this.masse(), this.getVitesse()));
}

@Override
public String toString()
{
return description("Frottement");
}

}
