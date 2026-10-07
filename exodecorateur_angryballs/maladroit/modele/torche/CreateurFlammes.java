package exodecorateur_angryballs.maladroit.modele.torche;

import mesmaths.geometrie.base.Vecteur;

/**
 * Builds a flame out of a spine.
 *
 * This is the seam that lets the real creator -- a proxy towards the C++ server -- and the local
 * stand-in be swapped for one another without Flamme ever knowing which one it holds.
 */
public interface CreateurFlammes
{

/**
 * Builds a flame out of the spine. The flame is a cloud of sparks: spark number i has its position
 * in etincelles[i] and its colour, in 24 bit RGB, in couleursEtincelles[i].
 *
 * To keep the rendering loop free of allocations, this method never allocates: both arrays, and
 * every element of etincelles, are assumed to exist already, and both arrays are assumed to hold
 * the same number of elements.
 *
 * @param positionCentreBille position of the centre of the ball
 * @param rayonBille          radius of the ball
 * @param echine              spine of the tail, and therefore of the flame
 * @param etincelles          result: positions of the sparks
 * @param couleursEtincelles  result: colours of the sparks
 * @return true on success, false on failure
 */
public boolean creeFlamme(final Vecteur positionCentreBille, final double rayonBille, final Echine echine, Vecteur[] etincelles, int[] couleursEtincelles);

}
