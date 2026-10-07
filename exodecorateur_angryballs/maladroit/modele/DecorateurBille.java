package exodecorateur_angryballs.maladroit.modele;


import java.util.Vector;

import mesmaths.geometrie.base.Vecteur;

/**
 * Abstract decorator of the Decorator pattern.
 *
 * Holds the ball it decorates and forwards every operation to it. A concrete decorator only
 * overrides the operations it actually alters, and usually calls super first so that the whole
 * chain down to the BilleNue runs before it adds its own contribution.
 *
 * This is what lets the developer assemble a ball from elementary behaviours at run time,
 * instead of writing one class per combination of behaviours.
 */
public abstract class DecorateurBille extends Bille
{

protected Bille billedecoree;

public DecorateurBille(Bille billedecoree)
{
super();
this.billedecoree = billedecoree;
}

public Vecteur getPosition()
{
return billedecoree.getPosition();
}

public double getRayon()
{
return billedecoree.getRayon();
}

public Vecteur getVitesse()
{
return billedecoree.getVitesse();
}

public Vecteur getAccélération()
{
return billedecoree.getAccélération();
}

public Couleur getCouleur()
{
return billedecoree.getCouleur();
}

public int getClef()
{
return billedecoree.getClef();
}

public double masse()
{
return billedecoree.masse();
}

public void déplacer(double deltaT)
{
billedecoree.déplacer(deltaT);
}

public boolean gestionCollisionBilleBille(Vector<Bille> billes)
{
return billedecoree.gestionCollisionBilleBille(billes);
}

public void dessine(VisiteurDessinateur v)
{
billedecoree.dessine(v);
}

@Override
public void gestionAccélération(Vector<Bille> billes)
{
billedecoree.gestionAccélération(billes);
}

@Override
public void collisionContour(double abscisseCoinHautGauche, double ordonnéeCoinHautGauche, double largeur,
        double hauteur)
{
billedecoree.collisionContour(abscisseCoinHautGauche, ordonnéeCoinHautGauche, largeur, hauteur);
}

/**
 * Builds the textual description of a decorated ball: the name of the behaviour this decorator
 * adds, followed by the description of everything it wraps. Reading a stack from left to right
 * therefore lists its behaviours from the outermost decorator down to the bare ball.
 *
 * Shared here so that every concrete decorator describes itself the same way.
 *
 * @param comportement the name of the behaviour added by the calling decorator
 */
protected String description(String comportement)
{
return comportement + ", " + this.billedecoree.toString();
}

@Override
public abstract String toString();

}
