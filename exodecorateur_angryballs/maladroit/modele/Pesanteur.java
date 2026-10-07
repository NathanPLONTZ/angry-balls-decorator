package exodecorateur_angryballs.maladroit.modele;

import java.util.Vector;

import mesmaths.geometrie.base.Vecteur;

/**
 * Acceleration behaviour: a constant pull, normally downwards.
 *
 * The pull is given as a vector at construction time, so the same decorator also covers an
 * unusual gravity -- sideways, upwards, or weaker than on Earth.
 */
public class Pesanteur extends DecorateurBille
{

Vecteur pesanteur;

public Pesanteur(Bille billedecoree, Vecteur pesanteur)
{
super(billedecoree);
this.pesanteur = pesanteur;
}

public void gestionAccélération(Vector<Bille> billes)
{
super.gestionAccélération(billes);                              // runs the chain down to the reset to zero
this.getAccélération().ajoute(this.pesanteur);
}

@Override
public String toString()
{
return description("Pesanteur");
}

}
