package exodecorateur_angryballs.maladroit;

import java.awt.event.ItemListener;
import java.io.File;
import java.util.Vector;

import mesmaths.geometrie.base.Vecteur;
import musique.SonLong;
import exodecorateur_angryballs.maladroit.modele.Arret;
import exodecorateur_angryballs.maladroit.modele.Bille;
import exodecorateur_angryballs.maladroit.modele.BilleNue;
import exodecorateur_angryballs.maladroit.modele.Couleur;
import exodecorateur_angryballs.maladroit.modele.Frottement;
import exodecorateur_angryballs.maladroit.modele.Hurlement;
import exodecorateur_angryballs.maladroit.modele.MvtNewton;
import exodecorateur_angryballs.maladroit.modele.MvtRU;
import exodecorateur_angryballs.maladroit.modele.PasseMuraille;
import exodecorateur_angryballs.maladroit.modele.Pesanteur;
import exodecorateur_angryballs.maladroit.modele.PiloteeSouris;
import exodecorateur_angryballs.maladroit.modele.Rebond;
import exodecorateur_angryballs.maladroit.modele.torche.Flamme;
import exodecorateur_angryballs.maladroit.vues.Billard;
import exodecorateur_angryballs.maladroit.vues.BillardAR;
import exodecorateur_angryballs.maladroit.vues.CadreAngryBalls;


/**
 * Entry point: builds six balls that all behave differently, then starts the animation.
 *
 * This is where the Decorator pattern pays off. Each ball starts as a BilleNue, which carries state
 * but no behaviour, and is then wrapped in the decorators that give it the behaviours wanted. The
 * six balls of the original application are rebuilt here by composition alone, without a single
 * class per combination of behaviours -- which is what the naive design would have required, and
 * which would have meant writing ninety more classes to cover every combination.
 *
 * Adding a behaviour to a ball is one more line. Inventing a new behaviour is one more decorator,
 * and nothing else in the application has to change.
 */
public class TestAngryBalls
{

public static void main(String[] args) throws InterruptedException
{
//---------------------- sound: path of the folder holding the audio files --------------------------

File file = new File(""); // where the JVM was started: root of the project

File répertoireSon = new File(file.getAbsoluteFile(),
    "exodecorateur_angryballs"+File.separatorChar+
    "maladroit"+File.separatorChar+"bruits");
System.out.println(répertoireSon.getPath());

//-------------------- loading the sounds of the screaming ball --------------------------------------

Vector<SonLong> sonsLongs = OutilsConfigurationBilleHurlante.chargeSons(répertoireSon, "config_audio_bille_hurlante.txt");

SonLong hurlements[] = SonLong.toTableau(sonsLongs);                // gives an array of SonLong

//------------------- the list of balls, empty for now -----------------------

Vector<Bille> billes = new Vector<Bille>();

//---------------- the view in charge of drawing the balls -------------------------

Billard billard = new BillardAR(billes);

Thread.sleep(500);

//---------------- the frame holding the table and the control buttons -------------------------

int choixHurlementInitial = 0;
CadreAngryBalls cadre = new CadreAngryBalls("Angry balls",
                                        "Animation de billes ayant des comportements différents. Situation idéale pour mettre en place le DP Decorator",
                                        billard, billes,hurlements, choixHurlementInitial);



cadre.montrer(); // make the view visible

//------------- filling the list with six balls -------------------------------



double xMax, yMax;
double vMax = 0.1;
xMax = cadre.largeurBillard();      // largest abscissa
yMax = cadre.hauteurBillard();      // largest ordinate

double rayon = 0.05*Math.min(xMax, yMax); // here every ball has the same radius, but nothing requires it

Vecteur p0, p1, p2, p3, p4, p5,  v0, v1, v2, v3, v4, v5;    // centres and velocity vectors at start-up,
                                                    // all picked at random

//------------------- position vectors of the balls ---------------------------------

p0 = Vecteur.créationAléatoire(0, 0, xMax, yMax);
p1 = Vecteur.créationAléatoire(0, 0, xMax, yMax);
p2 = Vecteur.créationAléatoire(0, 0, xMax, yMax);
p3 = Vecteur.créationAléatoire(0, 0, xMax, yMax);
p4 = Vecteur.créationAléatoire(0, 0, xMax, yMax);
p5 = Vecteur.créationAléatoire(0, 0, xMax, yMax);

//------------------- velocity vectors of the balls ---------------------------------

v0 = Vecteur.créationAléatoire(-vMax, -vMax, vMax, vMax);
v1 = Vecteur.créationAléatoire(-vMax, -vMax, vMax, 0);
v2 = Vecteur.créationAléatoire(-vMax, -vMax, vMax, vMax);
v3 = Vecteur.créationAléatoire(-vMax, -vMax, vMax, vMax);
v4 = Vecteur.créationAléatoire(-vMax, -vMax, vMax, vMax);
v5 = Vecteur.créationAléatoire(-vMax, -vMax, vMax, vMax);

//--------------- assembling the six balls from elementary behaviours ---------------------------------

// red: straight line at constant speed, bounces off the sides
Bille bille1 = new BilleNue(p0,  rayon, v0, Couleur.ROUGE);
bille1=new MvtRU(bille1);
bille1=new Rebond(bille1);

// yellow: falls, slowed down by the air, bounces off the sides
Bille bille2 = new BilleNue(p1,  rayon, v1, Couleur.JAUNE);
bille2=new Pesanteur(bille2, new Vecteur(0,0.001));
bille2 = new Frottement(bille2);
bille2 = new Rebond(bille2);

// green: attracted by the other balls, slowed down by the air, bounces off the sides
Bille bille3 = new BilleNue(p2,  rayon, v2, Couleur.VERT);
bille3=new MvtNewton(bille3);
bille3=new Frottement(bille3);
bille3 = new Rebond(bille3);

// cyan: straight line at constant speed, goes through the sides and comes back on the opposite one
Bille bille4 = new BilleNue(p3,  rayon, v3, Couleur.CYAN);
bille4=new MvtRU(bille4);
bille4=new PasseMuraille(bille4);

// black: attracted by the other balls, stopped by the sides, and screams
Bille bille5 = new BilleNue(p4,  rayon, v4, Couleur.NOIR);
bille5=new MvtNewton(bille5);
bille5=new Arret(bille5);
bille5=new Hurlement(bille5,hurlements[choixHurlementInitial], cadre);
cadre.addChoixHurlementListener((ItemListener) bille5);

// grey: slowed down by the air, bounces off the sides, can be grabbed and thrown, and trails a flame.
// Stacking Pilotee on top of the other behaviours is the point: the hand is one more influence, and
// the ball keeps colliding with the others while it is held.
Bille bille6 = new BilleNue(p5,  rayon, v5, Couleur.GRIS);
bille6=new Frottement(bille6);
bille6=new Rebond(bille6);
bille6=new PiloteeSouris(bille6,cadre);
bille6=new Flamme(bille6);


billes.add(bille1);
billes.add(bille2);
billes.add(bille3);
billes.add(bille4);
billes.add(bille5);
billes.add(bille6);

System.out.println("billes = " + billes);


//-------------------- the object driving the animation, on a separate thread -----------------------

AnimationBilles animationBilles = new AnimationBilles(billes, cadre);

//----------------------- listeners of the control buttons -----------------

EcouteurBoutonLancer écouteurBoutonLancer = new EcouteurBoutonLancer(animationBilles);
EcouteurBoutonArreter écouteurBoutonArrêter = new EcouteurBoutonArreter(animationBilles);

//------------------------- from here on the application runs on its own ------------------------------


cadre.lancerBilles.addActionListener(écouteurBoutonLancer);             // could be replaced by Observable - Observer
cadre.arrêterBilles.addActionListener(écouteurBoutonArrêter);           // could be replaced by Observable - Observer

}

}
