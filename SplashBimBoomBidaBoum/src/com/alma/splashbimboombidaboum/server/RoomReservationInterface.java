package com.alma.splashbimboombidaboum.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.alma.splashbimboombidaboum.client.PlayerInterface;

/**
 * <b>Interface représentant la réservation des lobbys</b>
 * <p>
 * Elle peut être utilisée comme objet distant.
 * </p>
 * <p>
 * La réservation des lobbys est caractérisée par l'information suivante :
 * <ul>
 * <li>Une liste de lobby</li>
 * </ul>
 * </p>
 * 
 * @author Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme
 * @version 1.0
 */
public interface RoomReservationInterface extends Remote {

	/**
	 * Retourne un lobby qui où le joueur passé en paramètre a été inscrit dedans.
	 * 
	 * @param player Le joueur à inscrire.
	 * @return Un lobby disponible.
	 * @throws RemoteException
	 * @see PlayerInterface
	 * @see RoomInterface
	 */
	public RoomInterface getRoom(PlayerInterface player) throws RemoteException;

	/**
	 * Retourne le lobby dont le joueur à renseigné l'identifiant de celui-ci. S'il
	 * a assez de place, incrit le joueur en paramètre dans le lobby.
	 * 
	 * @param player Le joueur à inscrire.
	 * @param roomId L'identifiant du lobby.
	 * @return Le lobby si le joueur à été incrit dedans, null sinon.
	 * @throws RemoteException
	 * @see PlayerInterface
	 * @see RoomInterface
	 */
	public RoomInterface getRoom(PlayerInterface player, String roomId) throws RemoteException;

	/**
	 * Supprime le lobby de la liste de lobby.
	 * 
	 * @param room Le lobby à supprimer.
	 * @throws RemoteException
	 * @see RoomInterface
	 */
	public void removeRoom(RoomInterface room) throws RemoteException;
}
