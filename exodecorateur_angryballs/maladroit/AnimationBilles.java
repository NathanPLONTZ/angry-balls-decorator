package exodecorateur_angryballs.maladroit;

import java.util.Vector;

import exodecorateur_angryballs.maladroit.modele.Bille;
import exodecorateur_angryballs.maladroit.vues.VueBillard;

/**
 * Drives the animation, that is, the motion of the list of balls.
 *
 * Runs on its own thread and endlessly updates the balls, then tells the view that the scene has
 * to be redrawn. deltaT, the delay between two updates, can be pictured as the delay between two
 * flashes of a stroboscope lighting the scene.
 *
 * Every step of the life of a ball happens here, in this order: move it, compute the acceleration
 * it undergoes, handle its collisions with the other balls, then handle its collision with the
 * border.
 */
public class AnimationBilles implements Runnable
{

Vector<Bille> billes;     // every ball currently in motion
VueBillard vueBillard;    // the view in charge of drawing the balls
private Thread thread;    // used to start and stop the balls

public AnimationBilles(Vector<Bille> billes, VueBillard vueBillard)
{
this.billes = billes;
this.vueBillard = vueBillard;
this.thread = null;
}

@Override
public void run()
{
try
    {
    double deltaT;  // delay between two updates of the list of balls
    Bille billeCourante;

    while (!Thread.interrupted())                           // motion
        {
        deltaT = 10;

        int i;
        for ( i = 0; i < billes.size(); ++i)    // update the list of balls
            {
            billeCourante = billes.get(i);
            billeCourante.déplacer(deltaT);                 // update position and velocity of this ball
            billeCourante.gestionAccélération(billes);      // compute the acceleration this ball undergoes
            billeCourante.gestionCollisionBilleBille(billes);
            billeCourante.collisionContour( 0, 0, vueBillard.largeurBillard(), vueBillard.hauteurBillard());
            }

        vueBillard.miseAJour();                             // tell the view to redraw the balls

        Thread.sleep((int)deltaT);
        }
    }

catch (InterruptedException e)
    {
    /* normal shutdown, nothing to do */
    }

}

public void lancerAnimation()
{
if (this.thread == null)
    {
    this.thread = new Thread(this);
    thread.start();
    }
}

public void arrêterAnimation()
{
if (thread != null)
    {
    this.thread.interrupt();
    this.thread = null;
    }
}

}
