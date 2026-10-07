package exodecorateur_angryballs.maladroit.modele;

import mesmaths.cinematique.Collisions;

/**
 * Border behaviour: the ball bounces off the sides.
 */
public class Rebond extends DecorateurBille
{

public Rebond(Bille billedecoree)
{
super(billedecoree);
}

@Override
public void collisionContour(double abscisseCoinHautGauche, double ordonnéeCoinHautGauche, double largeur,
        double hauteur)
{
Collisions.collisionBilleContourAvecRebond(this.getPosition(), this.getRayon(), this.getVitesse(), abscisseCoinHautGauche, ordonnéeCoinHautGauche, largeur, hauteur);
}

@Override
public String toString()
{
return description("Rebond");
}

}
