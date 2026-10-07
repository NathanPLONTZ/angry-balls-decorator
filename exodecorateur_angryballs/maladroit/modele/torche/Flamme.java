package exodecorateur_angryballs.maladroit.modele.torche;

import exodecorateur_angryballs.maladroit.modele.Bille;
import exodecorateur_angryballs.maladroit.modele.DecorateurBille;
import exodecorateur_angryballs.maladroit.modele.VisiteurDessinateur;
import mesmaths.geometrie.base.Vecteur;

/**
 * Visual behaviour: the ball trails a flame, the way a comet or a torch would.
 *
 * The flame hangs on a spine -- see Echine -- whose vertebrae keep a constant length, so the spine
 * bends to follow the ball without ever stretching, like the wake of a boat. The flame itself is a
 * cloud of sparks hanging on that spine.
 *
 * The shape of the spine blends two spines at every step: one that trails behind the ball, and one
 * that stands straight up. The faster the ball goes, the less the vertical one weighs, so a still
 * ball carries an upright flame and a fast one a trailing flame. The blend also grows towards the
 * tip, which is why the tip straightens up before the base does.
 *
 * The positions and colours of the sparks are not computed here: they are delegated to a
 * CreateurFlammes, which is a Proxy towards a C++ computation server. When that server cannot be
 * reached, a minimalist local stand-in takes over -- see CreateurFlammesMock.
 */
public class Flamme extends DecorateurBille
{

public final static double K = 5.0e2;
public Echine echine;
Echine echineVerticale;
public Vecteur etincelles[];
public int couleursEtincelles[];
int mEtincelles, mRangees, mNuage;
public double rayonEtincelle;
public CreateurFlammes createurFlammes;

public Flamme(Bille billedecoree)
{
this( billedecoree, 20, 0.18, 1.12, 25, 20, 1.5);
}

/**
 * @param nombreSommets  number of vertices of the spine
 * @param coeffR0        distance from the ball to the first vertex, as a fraction of its radius
 * @param coeffRaison    common ratio of the geometric progression of the vertebra lengths
 * @param mEtincelles    mEtincelles + 1 sparks along the length of the flame
 * @param mRangees       mRangees + 1 sparks across the width of the flame
 * @param rayonEtincelle radius of one spark
 */
public Flamme(Bille billedecoree ,final int nombreSommets,
        final double coeffR0,
        final double coeffRaison,
        final int mEtincelles,
        final int mRangees,
        final double rayonEtincelle)
{
super(billedecoree);
Vecteur u = new Vecteur(0,-1);  // vertical unit vector pointing up, in screen coordinates

/* the spine starts straight, pointing upwards */
this.echine = new Echine(getPosition().somme(u.produit(getRayon())), coeffR0*this.getRayon(), u, coeffRaison, nombreSommets);
this.echineVerticale = this.echine.copie();

/* try to start the client towards the C++ flame server */
try
    {
    this.mEtincelles = mEtincelles;
    this.mRangees = mRangees;
    this.createurFlammes = new CreateurBellesFlammes(this.mRangees,this.mEtincelles);

    this.rayonEtincelle = rayonEtincelle;
    System.out.println("CréateurBellesFlammes créé");
    }
catch (Exception e)         /* the flame server is unreachable, fall back on the minimalist creator */
    {
    this.createurFlammes = new CreateurFlammesMock();
    this.mEtincelles = this.echine.length()-1;
    this.mRangees = 0;
    this.rayonEtincelle = 4;
    }


/* the cloud of sparks is allocated once and reused, to keep the rendering loop free of allocations */

this.mNuage = (this.mEtincelles+1)*(this.mRangees+1) - 1;
this.etincelles = new Vecteur[this.mNuage+1];
int i; for ( i = 0 ; i < this.etincelles.length; ++i) this.etincelles[i] = new Vecteur();
this.couleursEtincelles = new int[this.etincelles.length];
}

@Override
public void déplacer(double deltaT)
{
Vecteur p = this.getPosition().copie();         // remember where the centre of the ball was
super.déplacer(deltaT);                         // update position and velocity of the ball

miseAJour2Echines(p);
}

/**
 * Moves and bends both spines after the ball has moved.
 *
 * @param p the position of the centre of the ball before it moved
 */
public void miseAJour2Echines(Vecteur p)
{
this.echine.miseAJour(p,this.getPosition());    // bend the trailing spine behind the ball

Vecteur trans = this.getPosition().difference(p);           /* translation to apply to the vertical spine */

for (Vecteur v : echineVerticale.sommets) v.ajoute(trans);          // move the vertical spine along

double alfa0 = Math.exp(-K*this.getVitesse().normeCarrée());        // weight of the vertical spine: the faster the
                                                                    // ball, the closer to zero. Always 0 < alfa0 <= 1


//-------- the new spine is a blend of the trailing spine and the vertical one ----
//--------- a convex combination, weighted so that the closer to the tip of the flame, the more the vertical spine weighs

double d = echine.sommets.length;

alfa0/=d;
int i,j;
for ( i = 0, j = 1; i < echine.sommets.length; i = j++)
    {
    double alfa = alfa0*j;          /* makes the vertices near the tip of the flame more attracted by the vertical
                                         spine than the vertices near the ball */
    double alfabar = 1-alfa;
    echine.sommets[i].combinaisonLineaire(alfabar, alfa, this.echineVerticale.sommets[i]);
    }
}

/**
 * Draws the flame, then the ball it decorates, so that the ball sits on top of its own flame.
 */
public void dessine (VisiteurDessinateur v)
{
v.visite(this);
v.visite(billedecoree);
}

@Override
public String toString()
{
return description("Flamme");
}

}
