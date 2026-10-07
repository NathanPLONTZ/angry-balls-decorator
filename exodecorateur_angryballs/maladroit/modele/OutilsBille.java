package exodecorateur_angryballs.maladroit.modele;

import java.util.Vector;

import mesmaths.cinematique.Collisions;
import mesmaths.geometrie.base.Vecteur;
import mesmaths.mecanique.MecaniquePoint;

/**
 * Operations that need to look at a ball against all the others: collisions between balls and
 * gravitational attraction.
 *
 * They live here rather than in Bille because they are about the whole set of balls, and because
 * both the bare ball and the decorators need them.
 */
public class OutilsBille
{

/**
 * @param cetteBille one of the balls in motion
 * @param billes     every ball currently in motion
 * @return every ball except cetteBille
 */
public static Vector<Bille> autresBilles(Bille cetteBille, Vector<Bille> billes)
{
Vector<Bille> autresBilles = new Vector<Bille>();

Bille billeCourante;

int i;

for( i = 0; i < billes.size(); ++i)
   {
   billeCourante = billes.get(i);
   if ( billeCourante.getClef() != cetteBille.getClef())
     autresBilles.add(billeCourante);
   }

return autresBilles;
}

/**
 * Handles the possible collision between one ball and the other ones. The default behaviour is a
 * perfectly elastic shock, that is, a rebound without damping.
 *
 * Assumes that no collision ever involves more than two balls at a time, so the search stops at
 * the first ball found in contact.
 *
 * @param cetteBille a particular ball
 * @param billes     a list of balls, which may contain cetteBille
 * @return true when a collision happened, in which case the positions and the velocity vectors of
 *         the two balls involved have been updated. false when the balls were left intact.
 */
public static  boolean gestionCollisionBilleBille(Bille cetteBille, Vector<Bille> billes)
{
Vector<Bille> autresBilles = OutilsBille.autresBilles(cetteBille, billes);

Bille billeCourante;

int i;

for ( i = 0 ; i < autresBilles.size(); ++i)
    {
    billeCourante = autresBilles.get(i);
    if (Collisions.CollisionBilleBille(    cetteBille.getPosition(),    cetteBille.getRayon(),    cetteBille.getVitesse(),    cetteBille.masse(),
                                        billeCourante.getPosition(), billeCourante.getRayon(), billeCourante.getVitesse(), billeCourante.masse()))
       return true;
    }
return false;
}

/**
 * Computes the acceleration one ball undergoes from the gravitational pull of all the others.
 *
 * @param cetteBille a particular ball
 * @param billes     a list of balls, which may contain cetteBille
 * @return the resulting acceleration vector
 */
public static Vecteur gestionAccélérationNewton(Bille cetteBille, Vector<Bille> billes)
{
Vector<Bille> autresBilles = OutilsBille.autresBilles(cetteBille, billes);

//-------------- masses and positions of the other balls ------------------
int i;
Bille billeCourante;

int d = autresBilles.size();

double masses [] = new double[d];   // masses of the other balls
Vecteur C [] = new Vecteur[d];      // positions of the other balls

for ( i = 0; i < d; ++i)
    {
    billeCourante = autresBilles.get(i);
    masses[i] = billeCourante.masse();
    C[i] = billeCourante.getPosition();
    }

//------------------ gravity field the other balls exert on this one ------------------

return  MecaniquePoint.champGravitéGlobal( cetteBille.getPosition(),  masses, C);
}

}
