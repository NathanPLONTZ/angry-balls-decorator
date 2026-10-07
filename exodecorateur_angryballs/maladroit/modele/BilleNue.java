package exodecorateur_angryballs.maladroit.modele;


import java.util.Vector;

import mesmaths.cinematique.Cinematique;
import mesmaths.geometrie.base.Geop;
import mesmaths.geometrie.base.Vecteur;

/**
 * Concrete component of the Decorator pattern: a bare ball, carrying state but no behaviour.
 *
 * It holds the position, radius, velocity, acceleration, key and colour, it integrates the
 * uniformly accelerated motion, and it resets the acceleration to zero at the start of every
 * acceleration pass. Everything else -- gravity, friction, bouncing, sound, flame -- is added
 * by decorators stacked on top of it.
 */
public class BilleNue extends Bille
{

public Vecteur position;        // centre of the ball
public double rayon;            // radius, strictly positive
public Vecteur vitesse;
public Vecteur accélération;
public int clef;                // unique identifier of this ball

private Couleur couleur;

private static int prochaineClef = 0;

public static double ro = 1;    // volumetric mass density

/**
 * @param centre       position of the centre of the ball
 * @param rayon        radius, strictly positive
 * @param vitesse      initial velocity vector
 * @param accélération initial acceleration vector
 * @param couleur      colour, independent of any graphics library
 */
protected BilleNue(Vecteur centre, double rayon, Vecteur vitesse,
        Vecteur accélération, Couleur couleur)
{
this.position = centre;
this.rayon = rayon;
this.vitesse = vitesse;
this.accélération = accélération;
this.couleur = couleur;
this.clef = BilleNue.prochaineClef ++;
}

/**
 * Builds a ball whose acceleration starts at the null vector.
 */
public BilleNue(Vecteur position, double rayon, Vecteur vitesse, Couleur couleur)
{
this(position, rayon, vitesse, new Vecteur(), couleur);
}

public Vecteur getPosition()
{
return this.position;
}

public double getRayon()
{
return this.rayon;
}

public Vecteur getVitesse()
{
return this.vitesse;
}

public Vecteur getAccélération()
{
return this.accélération;
}

public int getClef()
{
return this.clef;
}

public Couleur getCouleur()
{
return this.couleur;
}

/**
 * Mass derived from the radius, so that a bigger ball is heavier and therefore harder to
 * deflect, whether by a collision, by gravity, or by the hand that throws it.
 */
public double masse()
{
return ro*Geop.volumeSphère(rayon);
}

/**
 * Updates position and velocity at t+deltaT from position and velocity at instant t.
 *
 * Modifies the position and the velocity vectors, and leaves the acceleration vector intact.
 */
public void déplacer(double deltaT)
{
Cinematique.mouvementUniformémentAccéléré(this.getPosition(), this.getVitesse(), this.getAccélération(), deltaT);
}

/**
 * Resets the acceleration vector to zero, that is, no acceleration at all.
 *
 * This is the bottom of the acceleration chain: every decorator delegates here first, then adds
 * its own contribution, which is how the sum a = 0 + a_friction + a_newton + a_gravity is built.
 */
public void gestionAccélération(Vector<Bille> billes)
{
this.getAccélération().set(Vecteur.VECTEURNUL);
}

/**
 * A bare ball ignores the border: it simply leaves the screen. Reacting to the border is the
 * job of the Rebond, Arret and PasseMuraille decorators.
 */
public void collisionContour(double abscisseCoinHautGauche, double ordonnéeCoinHautGauche, double largeur, double hauteur)
{
}

public String toString()
    {
    return "centre = " + position + " rayon = "+rayon +  " vitesse = " + vitesse + " accélération = " + accélération + " couleur = " + couleur + "clef = " + clef;
    }

}
