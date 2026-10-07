package exodecorateur_angryballs.maladroit.vues;

import java.awt.CheckboxGroup;
import java.awt.GridLayout;
import java.awt.Panel;


import musique.SonLong;

/**
 * The row of radio buttons picking the sound of the screaming ball, at the bottom of the frame.
 *
 * The row is filled from the list of sounds that were actually loaded, so it always matches what is
 * available rather than a hard coded list.
 */
public class PanneauChoixHurlement extends Panel
{

BoutonChoixHurlement boutons[];
CheckboxGroup checkboxGroup;

/**
 * @param hurlements            every sound available for the screaming ball
 * @param choixHurlementInitial sound selected when the application starts, as a valid index into
 *                              hurlements
 */
public PanneauChoixHurlement(SonLong [] hurlements, int choixHurlementInitial)
{
this.boutons = new BoutonChoixHurlement [hurlements.length];
this.checkboxGroup = new CheckboxGroup();
this.setLayout(new GridLayout(1, this.boutons.length));
int i;
for ( i = 0; i < this.boutons.length; ++i)
    {
    this.boutons[i] = new BoutonChoixHurlement(checkboxGroup, false, hurlements[i]);
    this.add(this.boutons[i]);
    }

this.boutons[choixHurlementInitial].setState(true);
}

}
