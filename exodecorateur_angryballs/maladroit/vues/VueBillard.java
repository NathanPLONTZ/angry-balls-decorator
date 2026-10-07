package exodecorateur_angryballs.maladroit.vues;


/**
 * Contract honoured by any view able to draw the list of balls.
 *
 * AnimationBilles only ever talks to this interface, so the AWT components can be replaced without
 * touching the rest of the application.
 */
public interface VueBillard
{

public double largeurBillard();

public double hauteurBillard();

public void miseAJour();

public void montrer();

}
