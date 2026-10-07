package exodecorateur_angryballs.maladroit.modele;

import mesmaths.cinematique.Collisions;

/**
 * Border behaviour: the ball goes through the sides and reappears on the opposite one.
 */
public class PasseMuraille extends DecorateurBille
{

public PasseMuraille(Bille billedecoree)
{
super(billedecoree);
}

@Override
public void collisionContour(double abscisseCoinHautGauche, double ordonnéeCoinHautGauche, double largeur,
        double hauteur)
{
Collisions.collisionBilleContourPasseMuraille(this.billedecoree.getPosition(), abscisseCoinHautGauche, ordonnéeCoinHautGauche, largeur, hauteur);
}

@Override
public String toString()
{
return description("Passe muraille");
}

}
