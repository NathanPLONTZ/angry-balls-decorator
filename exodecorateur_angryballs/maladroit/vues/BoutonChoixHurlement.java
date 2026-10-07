package exodecorateur_angryballs.maladroit.vues;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.HeadlessException;

import musique.SonLong;

/**
 * One radio button offering one sound for the screaming ball.
 *
 * It carries the sound it stands for, so the listener reading the event can pull the sound straight
 * out of the button that fired it.
 */
public class BoutonChoixHurlement extends Checkbox
{

public SonLong sonLong;

/**
 * @param sonLong the sound this button stands for; its name becomes the label of the button
 */
public BoutonChoixHurlement( CheckboxGroup group, boolean state,
        SonLong sonLong) throws HeadlessException
{
super(sonLong.getNom(), group, state);
this.sonLong = sonLong;
}

}
