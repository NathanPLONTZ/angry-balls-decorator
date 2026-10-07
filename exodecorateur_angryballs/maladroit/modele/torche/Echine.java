package exodecorateur_angryballs.maladroit.modele.torche;


import java.util.Arrays;

import exodecorateur_angryballs.maladroit.modele.Couleur;
import exodecorateur_angryballs.maladroit.modele.VisiteurDessinateur;
import mesmaths.geometrie.base.Vecteur;

/**
 * The spine of the tail of the torch ball: the backbone the flame hangs on.
 *
 * The centre of the ball is not one of the vertices. sommets[0] is the vertex closest to the centre
 * of the ball, sommets[length-1] the furthest away.
 *
 * The distances between consecutive vertices never change over time, so they are stored once in
 * longueursVertebres rather than recomputed -- a distance costs a square root. Writing the vertices
 * {S0, S1, ..., Sm} and the lengths {V0, V1, ..., Vm}, then Vi = |Si - Si-1| for i = 0 ... m, with
 * V0 the distance from the centre of the ball to S0.
 */
public class Echine
{

/**
 * The spine is drawn in black.
 */
public static final Couleur COULEUR_ECHINE = Couleur.NOIR;

public Vecteur sommets[];
double longueursVertebres[];

public int length() { return sommets.length; }

public Vecteur[] getSommets()
{
return this.sommets;
}

/**
 * Builds a straight spine.
 *
 * @param positionCentreBille position of the centre of the ball
 * @param d0                  distance between positionCentreBille and S0
 * @param u                   unit vector giving the direction from one vertex to the next
 * @param coeffRaison         common ratio of the geometric progression of the vertebra lengths
 * @param nombreSommets       number of vertices of the spine, centre of the ball excluded
 */
public Echine( final Vecteur positionCentreBille, final double d0, final Vecteur u,  final double coeffRaison, final int nombreSommets)
{
this.longueursVertebres = new double[nombreSommets];
this.sommets = new Vecteur[nombreSommets];

Vecteur position;
double d;
int i;

for ( i = 0, d = d0, position = positionCentreBille.somme(u.produit(d0)); i < sommets.length; ++i, position.ajoute(u.produit(d*=coeffRaison)))
    {
    this.longueursVertebres[i] = d;
    this.sommets[i] = position.copie();
    }

}


/**
 * Copy constructor, from an array of vertices and an array of lengths.
 *
 * The information is redundant on purpose: recomputing the distances would cost a square root each.
 */
public Echine(final Vecteur[] sommets, final double[] longueursVertebres)
{
this.sommets = new Vecteur[sommets.length];
this.longueursVertebres = new double[sommets.length];

int i;
for ( i = 0; i < sommets.length; ++i)
    {
    this.sommets[i] = sommets[i].copie();
    this.longueursVertebres[i] = longueursVertebres[i];
    }
}

/**
 * Moves and bends the whole spine after the ball has moved; must be called right after the ball
 * changed position.
 *
 * Each vertex follows the one before it, which propagates the movement from the ball down to the
 * tip of the tail.
 *
 * @param positionCentreBilleAvant  previous position of the centre of the ball
 * @param positionCentreBilleAprès  current position of the centre of the ball
 */
void miseAJour(final Vecteur positionCentreBilleAvant, final Vecteur positionCentreBilleAprès)
{
int i;
Vecteur A0, A1;
for ( i = 0, A0 = positionCentreBilleAvant, A1 =  positionCentreBilleAprès; i< sommets.length ; ++i)
    {
    Vecteur B0 = sommets[i].copie();
    Vecteur B1 = sommets[i] = miseAJour(B0, A0, A1,this.longueursVertebres[i]);
    A0 = B0;
    A1 = B1;
    }
}

/**
 * Updates vertex number i given how vertex number i-1 moved.
 *
 * The move keeps |B0 - A0| = |B1 - A1|: the spine bends, but it never stretches nor shrinks.
 *
 * @param B0 previous position of vertex number i
 * @param A0 previous position of vertex number i-1
 * @param A1 new position of vertex number i-1
 * @param d  distance |B0 - A0|
 * @return B1, the new position of vertex number i
 */
public static  Vecteur miseAJour(final Vecteur B0, final Vecteur A0, final Vecteur A1, final double d)
{
Vecteur A1A0 = A0.difference(A1);
Vecteur A1B0 = B0.difference(A1);
Vecteur s = A1A0.somme(A1B0);

double n = s.norme();
s.multiplie(d/n);
return  A1.somme(s);
}


@Override
public String toString()
{
return "Echine [sommets=" + Arrays.toString(this.sommets) + "]";
}

public Echine copie()
{
return new Echine(this.sommets,this.longueursVertebres);
}

public void dessine (VisiteurDessinateur v)
{
v.visite(this);
}

}
