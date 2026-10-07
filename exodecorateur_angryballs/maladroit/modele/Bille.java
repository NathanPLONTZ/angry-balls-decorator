package exodecorateur_angryballs.maladroit.modele;


import java.util.Vector;

import mesmaths.geometrie.base.Vecteur;


/**
 * Component of the Decorator pattern: the general case of a billiard ball.
 *
 * A ball is described by its position, its radius, its velocity, its acceleration, a unique
 * key and a colour. Its motion is entirely defined by the triple (position, velocity,
 * acceleration).
 *
 * No class of this package references java.awt or any other Java graphics library: the model
 * must not depend on the view. Drawing is delegated to a VisiteurDessinateur, so that swapping
 * the graphics toolkit -- or porting the application to Android -- leaves the model untouched.
 */
public abstract class Bille
{

/**
 * @return the position of the centre of the ball
 */
public abstract Vecteur getPosition();

/**
 * @return the radius, strictly positive
 */
public abstract double getRayon();

/**
 * @return the velocity vector
 */
public abstract Vecteur getVitesse();

/**
 * @return the acceleration vector
 */
public abstract Vecteur getAccélération();

/**
 * @return the unique identifier of this ball. OutilsBille.gestionCollisionBilleBille and
 *         OutilsBille.gestionAccélérationNewton use it to tell this ball from the others.
 */
public abstract int getClef();

/**
 * @return the colour of the ball, in a representation independent of any graphics library
 */
public abstract Couleur getCouleur();

/**
 * @return the mass of the ball, derived from its radius
 */
public abstract double masse();

/**
 * Updates position and velocity at t+deltaT from position and velocity at instant t.
 *
 * Modifies the position and the velocity vectors, and leaves the acceleration vector intact.
 * By default the ball undergoes a uniformly accelerated motion.
 */
public abstract void déplacer(double deltaT);

/**
 * Computes, that is updates, the acceleration vector.
 *
 * The acceleration is the sum of the contributions of every kind of acceleration the ball
 * undergoes. Each decorator delegates to the ball it wraps first -- which eventually resets
 * the vector to zero in BilleNue -- then adds its own contribution on top.
 *
 * @param billes every ball currently in motion. Needed when this ball is attracted by the
 *               others through gravity.
 */
public abstract void gestionAccélération(Vector<Bille> billes);

/**
 * Handles the possible collision between this ball and the other ones.
 *
 * The default behaviour is a perfectly elastic shock, that is, a rebound without damping.
 *
 * @param billes every ball currently in motion
 * @return true when a collision happened, in which case the positions and the velocity
 *         vectors of the two balls involved have been updated. false when there is no
 *         collision and the balls were left intact.
 */
public boolean gestionCollisionBilleBille(Vector<Bille> billes)
{
return OutilsBille.gestionCollisionBilleBille(this, billes);
}

/**
 * Handles the possible collision between this ball and the rectangular border of the screen
 * defined by (abscisseCoinHautGauche, ordonnéeCoinHautGauche, largeur, hauteur).
 *
 * Detects whether there is a collision and, where applicable, updates position and velocity.
 * The response -- bouncing, stopping, or wrapping around to the opposite side -- is defined
 * by the decorators.
 */
public abstract void collisionContour(double abscisseCoinHautGauche, double ordonnéeCoinHautGauche, double largeur, double hauteur);

/**
 * Accepts a drawing visitor. Keeping the drawing out of the model is what allows the model to
 * stay free of any graphics library.
 */
public void dessine(VisiteurDessinateur v)
{
v.visite(this);
}

@Override
public abstract String toString();

}
