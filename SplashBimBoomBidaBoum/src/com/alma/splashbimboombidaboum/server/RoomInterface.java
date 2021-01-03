package com.alma.splashbimboombidaboum.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.alma.splashbimboombidaboum.client.PlayerInterface;
import com.alma.splashbimboombidaboum.utility.PlayerColor;

/**
 * <b>Interface représentant un lobby</b>
 * <p>
 * Elle peut être utilisée comme objet distant.
 * </p>
 * <p>
 * Le lobby est caractérisé par les informations suivantes :
 * <ul>
 * <li>Un identifiant unique</li>
 * <li>Un nombre maximum de joueur</li>
 * <li>Une queue</li>
 * <li>Un boolean qui indique si le lobby est en jeu</li>
 * <li>Une référence vers l'objet de réservation des lobbys</li>
 * <li>Une liste de joueurs présents dans le lobby</li>
 * <li>Une liste de joueurs morts dans la partie</li>
 * <li>Une liste d'obstacles</li>
 * <li>Une liste de couleurs restantes</li>
 * <li>Une liste de couleurs prisent</li>
 * </ul>
 * </p>
 * 
 * @see ObstacleEntityInterface
 * @see PlayerColor
 * @see PlayerInterface
 * @see RoomReservationInterface
 * @author Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme
 * @version 1.0
 */
public interface RoomInterface extends Remote {
	/**
	 * Retourne l'identifiant du lobby.
	 * 
	 * @return L'identifiant du lobby.
	 * @throws RemoteException
	 */
	public String getId() throws RemoteException;

	/**
	 * Retourne le nombre maximal de joueur du lobby.
	 * 
	 * @return Le nombre maximal de joueur du lobby.
	 * @throws RemoteException
	 */
	public int getMaxPlayer() throws RemoteException;

	/**
	 * Retourne le nombre de clients en attentent de connexion au lobby.
	 * 
	 * @return Le nombre de clients dans la queue.
	 * @throws RemoteException
	 */
	public int getQueue() throws RemoteException;

	/**
	 * Retourne un boolean qui indique si le lobby à lancée une partie.
	 * 
	 * @return true si le lobby est en partie, false sinon.
	 * @throws RemoteException
	 */
	public boolean getInGame() throws RemoteException;

	/**
	 * Retourne la liste des joueurs dans le lobby.
	 * 
	 * @return La liste des joueurs du lobby.
	 * @throws RemoteException
	 * @see PlayerInterface
	 */
	public ArrayList<PlayerInterface> getPlayers() throws RemoteException;

	/**
	 * Permet de mettre à jour le nombre de joueur max du lobby.
	 * 
	 * @param maxPlayer La mise à jour du nombre de joueur du lobby. (maxPlayer > 1)
	 * @throws RemoteException
	 */
	public void setMaxPlayer(int maxPlayer) throws RemoteException;

	/**
	 * Supprime un joueur de la liste des joueurs du lobby.
	 * 
	 * @param player Le joueur à supprimer.
	 * @throws RemoteException
	 */
	public void removePlayer(PlayerInterface player) throws RemoteException;

	/**
	 * Change l'état d'un joueur. Entre autre, il permet de passer de l'état prêt à
	 * l'état pas prêt. Si tous les joueurs sont prêt et qu'ils sont au moins deux,
	 * lance la partie.
	 * 
	 * @param player Le joueur qui change d'état.
	 * @param ready  L'état du joueur.
	 * @throws RemoteException
	 * @see PlayerInterface
	 */
	public void changeState(PlayerInterface player, boolean ready) throws RemoteException;

	/**
	 * Retourne ce lobby s'il y a encore de la place pour le joueur passé en
	 * paramètre. Cette méthode sert de tampon. Elle crée une file implicite.
	 * 
	 * @param player Le joueur qui veut se connecter dans le lobby.
	 * @return Ce lobby s'il y a de la place, null sinon.
	 * @throws RemoteException
	 * @see PlayerInterface
	 */
	public RoomInterface roomConnection(PlayerInterface player) throws RemoteException;
}
