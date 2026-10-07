package exodecorateur_angryballs.maladroit.modele;

import mesmaths.cinematique.Collisions;

/**
 * Border behaviour: the ball is stopped dead by the sides instead of bouncing off them.
 */
public class Arret extends DecorateurBille
{

public Arret(Bille billedecoree)
{
super(billedecoree);
}

@Override
public void collisionContour(double abscisseCoinHautGauche, double ordonnéeCoinHautGauche, double largeur,
        double hauteur)
{
Collisions.collisionBilleContourAvecArretHorizontal(this.getPosition(), this.getRayon(), this.getVitesse(), abscisseCoinHautGauche, largeur);
Collisions.collisionBilleContourAvecArretVertical(this.getPosition(), this.getRayon(), this.getVitesse(), ordonnéeCoinHautGauche, hauteur);
}

@Override
public String toString()
{
return description("Arret");
}

}
