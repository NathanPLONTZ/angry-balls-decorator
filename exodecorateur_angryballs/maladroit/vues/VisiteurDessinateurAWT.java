package exodecorateur_angryballs.maladroit.vues;

import java.awt.Color;
import java.awt.Graphics;

import exodecorateur_angryballs.maladroit.modele.Bille;
import exodecorateur_angryballs.maladroit.modele.Couleur;
import exodecorateur_angryballs.maladroit.modele.VisiteurDessinateur;
import exodecorateur_angryballs.maladroit.modele.torche.Echine;
import exodecorateur_angryballs.maladroit.modele.torche.Flamme;
import mesmaths.geometrie.base.Vecteur;

/**
 * AWT implementation of the drawing visitor: the one place in the application that knows both what
 * the model holds and how to paint it with java.awt.
 *
 * Every java.awt dependency of the drawing is concentrated here, which is what keeps the model free
 * of any graphics library. Supporting another toolkit means writing another implementation of
 * VisiteurDessinateur, and changing nothing in the model.
 */
public class VisiteurDessinateurAWT implements VisiteurDessinateur
{

public Graphics graphique;

public VisiteurDessinateurAWT(Graphics g)
{
super();
graphique=g;
}

/**
 * Converts a colour of the model into the AWT colour the toolkit expects.
 */
public Color CouleurToAWT(Couleur c)
{
return new Color(c.getCouleurHexa());
}

/**
 * Draws a filled disc with an outline, centred on position.
 */
public static void dessineDisque(Graphics g, final Vecteur position, final double rayon, final Color couleurIntérieur, final Color couleurBord)
{
int width, height;
int xMin, yMin;

xMin = (int)Math.round(position.x-rayon);
yMin = (int)Math.round(position.y-rayon);

width = height = 2*(int)Math.round(rayon);

g.setColor(couleurIntérieur);
g.fillOval( xMin, yMin, width, height);
g.setColor(couleurBord);
g.drawOval(xMin, yMin, width, height);

}


/**
 * Draws a segment between p1 and p2, and restores the colour the caller was using.
 */
public static void dessineSegment( Graphics g, Vecteur p1, Vecteur p2, Color couleur)
{
int x1 = (int)Math.round(p1.x);
int y1 = (int)Math.round(p1.y);

int x2 = (int)Math.round(p2.x);
int y2 = (int)Math.round(p2.y);

Color ancienneCouleur = g.getColor();
g.setColor(couleur);
g.drawLine(x1, y1, x2, y2);
g.setColor(ancienneCouleur);
}

@Override
public void visite(Bille bille)
{
dessineDisque(this.graphique,bille.getPosition(),bille.getRayon(),CouleurToAWT(bille.getCouleur()),Color.CYAN);
}

/**
 * Draws the spine as the broken line joining its vertices.
 */
@Override
public void visite(Echine echine)
{
int i;
Vecteur p1,p2;
for ( i = 1, p1 = echine.getSommets()[0]; i <echine.getSommets().length; ++i, p1 = p2)
    {
    p2 = echine.getSommets()[i];
    dessineSegment(this.graphique, p1, p2, CouleurToAWT(Echine.COULEUR_ECHINE));
    }
}


/**
 * Draws the flame as its cloud of sparks.
 *
 * The positions and the colours of the sparks are asked for at draw time, which is also where the
 * request to the C++ flame server happens. When that request fails, the spine alone is drawn, so
 * something sensible still shows up on screen.
 */
@Override
public void visite(Flamme flamme)
{
/* fill the arrays of spark positions and spark colours first */

boolean ok = flamme.createurFlammes.creeFlamme(flamme.getPosition(), flamme.getRayon(),
                                             flamme.echine, flamme.etincelles,flamme.couleursEtincelles);


if (ok)
    {
    int i;
    for (i = 0; i < flamme.etincelles.length; ++i) /* draw the sparks one by one */
        {
        int couleur = flamme.couleursEtincelles[i];
        Color co = new Color(couleur);
        dessineDisque(this.graphique, flamme.etincelles[i], flamme.rayonEtincelle, co, co);
        }
    }
else            /* the arrays could not be filled, so draw the bare spine instead of the flame */
    {
    dessineSegment(this.graphique, flamme.getPosition(), flamme.echine.sommets[0],CouleurToAWT(Echine.COULEUR_ECHINE));
    flamme.echine.dessine(this);
    }
}


}
