package exodecorateur_angryballs.maladroit.modele;

import exodecorateur_angryballs.maladroit.modele.torche.Echine;
import exodecorateur_angryballs.maladroit.modele.torche.Flamme;

/**
 * Visitor that knows how to draw every drawable part of the model.
 *
 * This is the seam that keeps the model free of any graphics library: the model exposes what it
 * is, and an implementation living in the view package -- VisiteurDessinateurAWT -- decides how to
 * paint it with a given toolkit. Supporting another toolkit means writing one more implementation,
 * and changing nothing in the model.
 */
public interface VisiteurDessinateur
{

public void visite(Bille bille);

public void visite(Echine echine);

public void visite(Flamme flamme);

}
