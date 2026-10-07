package exodecorateur_angryballs.maladroit.modele;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import exodecorateur_angryballs.maladroit.vues.BoutonChoixHurlement;
import exodecorateur_angryballs.maladroit.vues.VueBillard;
import mesmaths.geometrie.base.Vecteur;
import musique.SonLong;

/**
 * Sound behaviour: the ball screams, and the scream follows what the ball is doing.
 *
 * Both the volume and the playback rate grow with the speed of the ball, and the stereo balance
 * follows its abscissa, so the sound travels from the left speaker to the right one as the ball
 * crosses the table.
 *
 * The decorator also listens to the row of radio buttons at the bottom of the frame, so that the
 * sound can be swapped while the animation runs.
 */
public class Hurlement extends DecorateurBille implements ItemListener
{

private static final int DELAI_MIN = 10;    /* shortest delay between two sound refreshes, in milliseconds */
public static final int DELAI_MAX = 150;    /* longest delay between two sound refreshes, in milliseconds */

public SonLong sonLong;                     /* soundtrack to play */
int i;                                      /* index of the chunk of sonLong to play, must stay >= 0.
                                               sonLong applies the modulo itself to get a valid index
                                               and therefore to loop over the array it holds */
long dernierInstant;                        /* last instant at which the sound was played */
VueBillard vueBillard;

private static final double COEFF_VOLUME = 6;

public Hurlement(Bille billedecoree, SonLong sonLong, VueBillard vueBillard)
{
super(billedecoree);
this.sonLong = sonLong;
i = 0;
dernierInstant = System.currentTimeMillis();
this.vueBillard = vueBillard;
}

@Override
public void déplacer(double deltaT)
{
super.déplacer(deltaT);
Vecteur p = this.getPosition();
Vecteur v = this.getVitesse();
double xMax;

xMax = vueBillard.largeurBillard();

double n = v.norme();
double y = Math.exp(-COEFF_VOLUME*n);                // y = e^(-COEFF*n), hence 0 < y <= 1
double volume = 1-y;                                 // hence 0 <= volume < 1, zero when the ball is still and close to 1 when it is fast
double x1 = p.x/xMax;                                // hence 0 <= x1 <= 1
double balance = 2*x1 - 1;                           // hence -1 <= balance <= 1

int délai = (int)(DELAI_MIN*volume + DELAI_MAX*y);   /* the delay between two plays shrinks as the speed grows */
long instant = System.currentTimeMillis();
if (instant - this.dernierInstant >=délai)           /* so the playback rate grows with the speed of the ball */
    {
    double coeffPitch = 1;
    this.sonLong.joue(i++, volume, balance, coeffPitch);            /* the sound is played on a separate thread */
    this.dernierInstant= instant;
    }
}

/**
 * Called when the user picks another sound in the row of radio buttons at the bottom of the frame.
 */
@Override
public void itemStateChanged(ItemEvent e)
{
if (e.getSource() instanceof BoutonChoixHurlement)
    {
    BoutonChoixHurlement boutonChoixHurlement = (BoutonChoixHurlement)(e.getSource());
    this.sonLong = boutonChoixHurlement.sonLong;
    }
}

@Override
public String toString()
{
return description("Hurlement");
}

}
