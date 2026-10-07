package exodecorateur_angryballs.maladroit.modele;

/**
 * Behaviour of a ball the user can grab and throw.
 *
 * This abstract decorator names the behaviour without committing to an input device. The concrete
 * subclass binds it to an actual device -- see PiloteeSouris for the mouse -- which leaves room
 * for a touch screen or a gamepad without touching anything else.
 *
 * A piloted ball keeps every behaviour it already had: the hand is just one more influence, which
 * is the very idea of the Decorator pattern. It also still collides with the other balls exactly
 * as it did before being grabbed.
 */
public abstract class Pilotee extends DecorateurBille
{

public Pilotee(Bille billedecoree)
{
super(billedecoree);
}

}
