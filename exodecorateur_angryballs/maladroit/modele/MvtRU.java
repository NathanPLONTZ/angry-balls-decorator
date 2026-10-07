package exodecorateur_angryballs.maladroit.modele;

import mesmaths.cinematique.Cinematique;

/**
 * Motion behaviour: rectilinear uniform motion, that is, a straight line at constant speed.
 *
 * This is the first entry of the inventory of accelerations: the absence of acceleration. A ball
 * carrying only this behaviour never has its velocity changed by anything other than the border
 * and the other balls.
 */
public class MvtRU extends DecorateurBille
{

public MvtRU(Bille billedecoree)
{
super(billedecoree);
}

public void déplacer(double deltaT)
{
super.déplacer(deltaT);
Cinematique.mouvementUniformémentAccéléré(this.getPosition(), this.getVitesse(), this.getAccélération(), deltaT);
}

@Override
public String toString()
{
return description("Mouvement rectiligne uniforme");
}

}
