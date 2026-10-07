package exodecorateur_angryballs.maladroit.modele.torche;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import mesmaths.geometrie.base.Vecteur;

/**
 * Proxy standing in for the C++ flame server: it looks like a local flame creator, but every call
 * is forwarded to another process over TCP/IP.
 *
 * Computing the positions and the colours of several hundred sparks a hundred times a second is out
 * of reach for Java, so the work is handed to a server written in C++. Messages travel in binary
 * rather than text, again to keep the round trip short.
 *
 * The parameters of the flame -- colours, how fast the sparks flow, the outline of the flame, how
 * much the sparks scatter -- are all defined on the C++ side.
 */
public class CreateurBellesFlammes implements CreateurFlammes
{

DataInputStream fluxEntrant;    /* stream receiving data from the socket */
DataOutputStream fluxSortant;   /* stream sending data to the socket */

/**
 * Connects to the flame server and tells it the size of the cloud of sparks wanted, which is
 * (mRangees+1)*(mEtincelles+1) sparks.
 *
 * mRangees drives the density of sparks across the width of the flame, mEtincelles the density
 * along the spine. The server caps both: mRangees+1 must stay under L_LIGNES and mEtincelles+1
 * under L_COLONNES, two limits fixed when the C++ server was compiled.
 *
 * Note that the address and the port are hard coded here. Choosing them in main() would mean
 * introducing an abstract factory of CreateurFlammes.
 *
 * @throws IOException when the connection fails, which is what makes Flamme fall back on the mock
 */
public CreateurBellesFlammes(int mRangees, int mEtincelles) throws IOException
{
InetAddress adresseServeur = InetAddress.getByName("127.0.0.1");
int portServeur = 3718;

//---------------- open the socket towards the server ----------------------

Socket socket = new Socket(adresseServeur, portServeur);

//------------- open the binary streams used to talk to the server -----------------

this.fluxEntrant = new DataInputStream(socket.getInputStream());
this.fluxSortant = new DataOutputStream(socket.getOutputStream());

//---------------------- send the parameters of the flame to the server --------------------

this.fluxSortant.writeInt(mRangees);
this.fluxSortant.writeInt(mEtincelles);
}

/**
 * Sends one request to the flame server and reads its answer back.
 *
 * The request holds the position of the centre of the ball, its radius, and the spine. The answer
 * is the cloud of sparks: first every position, then every colour.
 *
 * See the CreateurFlammes interface for the contract.
 */
@Override
public boolean creeFlamme( final Vecteur positionCentreBille, final double rayonBille, final Echine echine,
        Vecteur[] etincelles,
        int[] couleursEtincelles)
{
try
    {
    VecteurStream.writeVecteur(fluxSortant, positionCentreBille);
    fluxSortant.writeDouble(rayonBille);
    int mEchine = echine.length()-1;
    fluxSortant.writeInt(mEchine);
    VecteurStream.writeVecteurs(fluxSortant, echine.sommets);

    //------------------- read the answer of the server ------------------------------

    VecteurStream.readVecteurs(fluxEntrant, etincelles);

    int i;
    for ( i = 0; i < etincelles.length; ++i) couleursEtincelles[i] = fluxEntrant.readInt();

    return true;
    }
catch (IOException e)
    {
    return false;
    }

}

}
