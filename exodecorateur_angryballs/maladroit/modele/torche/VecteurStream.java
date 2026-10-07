package exodecorateur_angryballs.maladroit.modele.torche;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mesmaths.geometrie.base.Vecteur;

/**
 * Reads and writes two dimensional vectors on a stream. A vector is a pair of double coordinates.
 *
 * Everything travels in binary rather than text, to keep the exchange short: this class is used to
 * send and receive messages over the socket towards the C++ flame server.
 *
 * The reading methods never allocate, again for speed: the vectors and the arrays of vectors they
 * fill are assumed to exist already. The streams are assumed to be open.
 */
public class VecteurStream
{

/**
 * Writes the vector v(x,y) on the stream, in binary.
 *
 * @param flux written to by this call
 * @param v    vector to write
 */
public static void writeVecteur(DataOutputStream flux, final Vecteur v) throws IOException
{
flux.writeDouble(v.x);
flux.writeDouble(v.y);
}


/**
 * Writes the array of vectors t on the stream, in binary.
 *
 * @param flux written to by this call
 * @param t    array to write
 */
public static void writeVecteurs(DataOutputStream flux, final Vecteur t[]) throws IOException
{
for (Vecteur v : t) writeVecteur(flux, v);
}

/**
 * Reads the vector v(x,y) from the stream, in binary.
 *
 * @param flux read from by this call
 * @param v    result, assumed to exist already
 */
public static void readVecteur(DataInputStream flux, Vecteur v) throws IOException
{
v.x = flux.readDouble();
v.y = flux.readDouble();
}

/**
 * Reads the array of vectors t from the stream, in binary. Reads as many vectors as the array holds
 * elements.
 *
 * @param flux read from by this call
 * @param t    result, whose elements are all assumed to exist already
 */
public static void readVecteurs(DataInputStream flux, Vecteur t []) throws IOException
{
for (Vecteur v : t) readVecteur(flux,v);
}

}
