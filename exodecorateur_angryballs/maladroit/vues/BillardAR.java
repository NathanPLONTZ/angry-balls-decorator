package exodecorateur_angryballs.maladroit.vues;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Vector;

import exodecorateur_angryballs.maladroit.modele.Bille;

/**
 * A billiard table drawn by active rendering, that is, without going through paint().
 *
 * The animation thread draws straight into a back buffer and flips it, instead of asking AWT to
 * repaint and waiting for it. Double buffering is what keeps the balls from flickering at a hundred
 * frames per second.
 */
public class BillardAR extends Billard
{

BufferStrategy stratégie;

public BillardAR(Vector<Bille> billes)
{
super(billes);
}

/**
 * Sets the double buffering up. Can only run once the canvas is actually displayable, which is why
 * CadreAngryBalls calls it from setVisible.
 */
public void init()
{
this.setIgnoreRepaint(true);
this.createBufferStrategy(2);

this.stratégie = this.getBufferStrategy();
}

@Override
public void miseAJour()
{
Graphics g = this.stratégie.getDrawGraphics();
g.clearRect(0, 0, this.getWidth(), this.getHeight());
this.dessine(g);
this.stratégie.show();
}

public void repaint()
{
this.miseAJour();
}

}
