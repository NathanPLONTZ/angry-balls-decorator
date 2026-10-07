package exodecorateur_angryballs.maladroit.modele.torche;

import mesmaths.geometrie.base.Vecteur;

/**
 * Local stand-in used when the C++ flame server could not be reached, so that the application still
 * runs in a minimalist version instead of failing.
 *
 * It draws no real flame: the cloud of sparks is simply the vertices of the spine, in a single
 * colour, which shows the backbone of the tail without any of the C++ computation.
 */
public class CreateurFlammesMock implements CreateurFlammes
{

/**
 * Assumes echine.length() <= etincelles.length.
 *
 * The cloud of sparks is the set of vertices of the spine, all painted in the colour defined by
 * Echine.
 */
@Override
public boolean creeFlamme( final Vecteur position, final double rayon, final Echine echine,
        Vecteur[] etincelles,
        int[] couleursEtincelles)
{

int i;
for ( i = 0 ; i < echine.length(); ++i) { etincelles[i] = echine.getSommets()[i]; couleursEtincelles[i] = Echine.COULEUR_ECHINE.couleurHexa; }

return true;
}

}
