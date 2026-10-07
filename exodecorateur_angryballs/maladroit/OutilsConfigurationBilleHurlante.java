package exodecorateur_angryballs.maladroit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import musique.SonLong;
import musique.SonLongFantome;
import musique.javax.SonLongJavax;

/**
 * Builds every sound offered for the screaming ball, out of the audio files sitting next to the
 * configuration file.
 *
 * Nothing here is fatal: whenever a sound cannot be built, the application keeps running with a
 * silent stand-in rather than failing to start.
 */
public class OutilsConfigurationBilleHurlante
{

/**
 * Opens the configuration file of the screaming ball and hands it to chargeSons1.
 *
 * @return the sounds described by the file, or a single silent SonLongFantome when the file cannot
 *         be read at all
 */
public static Vector<SonLong>  chargeSons(File répertoireBruits, String nomFichierConfigAudio)
{
Vector<SonLong> résultat;
try
    {
    File f = new File(répertoireBruits,nomFichierConfigAudio);
    FileInputStream f1 = new FileInputStream(f);
    BufferedReader fichierConfigBilleHurlante = new BufferedReader(new InputStreamReader(f1));

    résultat = OutilsConfigurationBilleHurlante.chargeSons1(répertoireBruits, fichierConfigBilleHurlante);
    fichierConfigBilleHurlante.close();
    }

catch (IOException e)
    {
    résultat = new Vector<SonLong>();
    résultat.add(new SonLongFantome());
    System.err.println("sons indisponibles pour les hurlements");
    }
return résultat;
}

/**
 * Builds a list of SonLong out of the configuration file.
 *
 * The first eight lines of the file describe the format of the file itself and are skipped. Every
 * line after that builds one SonLong and holds four fields separated by spaces: the name of the
 * audio file without its .wav extension, the start of the excerpt in hundredths of a second, its
 * end, and the number of chunks the excerpt is cut into. For instance:
 *
 *     spitfire 1100 1700 30
 *
 * Two constraints must hold on each line:
 *     effectif^2 >= (finExtrait - débutExtrait) / Hurlement.DELAI_MIN
 *     (finExtrait - débutExtrait) / effectif >= SonJavax.TAILLE_BUFFER_LIGNE
 *
 * Any line holding an error is skipped and the next one is read. When not a single valid SonLong
 * could be built, the method returns a Vector holding one silent SonLongFantome, so that the
 * application still runs.
 *
 * @param répertoireBruits           folder holding both the configuration file and the audio files
 * @param fichierConfigBilleHurlante the configuration file, already open
 */
public static Vector<SonLong>  chargeSons1(File répertoireBruits, BufferedReader fichierConfigBilleHurlante)
{
Vector<SonLong> sons = new Vector<SonLong>();

int i;
String ligne = null;

try
    {
    for (i = 0; i < 8; ++i) ligne = fichierConfigBilleHurlante.readLine(); /* skip the eight header lines holding the summary */
    }
catch (IOException e1)  /* the header of the file is broken */
    {
    sons.add(new SonLongFantome());         /* stand-in sound, so that the application still runs without any playback */
    return sons;
    }

/* the eight header lines were read successfully, so the file now points at the ninth line */

for ( /* nothing to do here */; ligne != null; ++i)
    {
    try
      {
      ligne = fichierConfigBilleHurlante.readLine();        /* expected to look like "spitfire 1100 1700 30" */

      if (ligne != null) sons.add(SonLongJavax.crée(répertoireBruits, ligne) );
      }
    catch (Exception e)
      {
      /* skip the offending line and move on to the next sound */
      System.err.println("Dans OutilsConfigurationBilleHurlante.chargeSons1() : ligne n° " + i + " ignorée car contenant une erreur : " + e);
      }
    }       // for

if (sons.isEmpty()) sons.add(new SonLongFantome());
return sons;
}

}
