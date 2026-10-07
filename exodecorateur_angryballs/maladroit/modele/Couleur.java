package exodecorateur_angryballs.maladroit.modele;

/**
 * A colour in 24 bit RGB, held by the model.
 *
 * The model deliberately does not use java.awt.Color: depending on a graphics library here
 * would force every model class to change the day the application switches toolkit. The view
 * converts a Couleur into whatever its own toolkit expects -- see VisiteurDessinateurAWT.
 */
public class Couleur
{

public int couleurHexa;

public static final Couleur GRIS  = new Couleur(0xD3D3D3);
public static final Couleur ROUGE = new Couleur(0xFF0000);
public static final Couleur VERT  = new Couleur(0x00FF00);
public static final Couleur BLEU  = new Couleur(0x0000FF);
public static final Couleur NOIR  = new Couleur(0x000000);
public static final Couleur JAUNE = new Couleur(0xFFFF00);
public static final Couleur CYAN  = new Couleur(0x00FFFF);

public Couleur(int couleurHexa)
{
super();
this.couleurHexa = couleurHexa;
}

public int getCouleurHexa()
{
return couleurHexa;
}

public void setCouleurHexa(int couleurHexa)
{
this.couleurHexa = couleurHexa;
}

}
