package exodecorateur_angryballs.maladroit.vues;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.Vector;

import exodecorateur_angryballs.maladroit.modele.Bille;

/**
 * The billiard table: the canvas the balls are drawn on.
 *
 * It holds the list of balls and asks each of them to draw itself through a visitor, so the view
 * knows how to paint with AWT while the model stays unaware that AWT exists at all.
 */
public class Billard extends Canvas implements VueBillard
{

Vector<Bille> billes;

public Billard(Vector<Bille> billes)
{
this.billes = billes;
}

/**
 * Hook for the subclasses that need to set a rendering strategy up once the canvas is on screen.
 * Nothing to do for a plain canvas painted through paint().
 */
public void init() {}

@Override
public void paint(Graphics graphics)
{
this.dessine(graphics);
}

@Override
public double largeurBillard()
{
return this.getWidth();
}

@Override
public double hauteurBillard()
{
return this.getHeight();
}

@Override
public void miseAJour()
{
this.repaint();
}

@Override
public void montrer()
{
this.setVisible(true);
}

/**
 * Draws every ball, by handing each of them the AWT drawing visitor.
 */
public  void dessine(Graphics graphics)
{
int i;
VisiteurDessinateurAWT visiteur = new VisiteurDessinateurAWT(graphics);
for ( i = 0; i < this.billes.size(); ++i) {
    this.billes.get(i).dessine(visiteur);
}
}

}
