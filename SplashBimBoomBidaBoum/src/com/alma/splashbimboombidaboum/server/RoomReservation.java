package com.alma.splashbimboombidaboum.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import com.alma.splashbimboombidaboum.client.PlayerInterface;
import com.alma.splashbimboombidaboum.utility.Address;
import com.alma.splashbimboombidaboum.utility.MathVectorInterface;

/**
 * <b>Classe représentant la réservation des lobbys.</b>
 * <p>
 * Elle peut être aussi utilisée comme objet distant.
 * </p>
 * <p>
 * Voir {@link RoomReservationInterface} pour plus de renseignements.
 * </p>
 * 
 * @see Address
 * @author Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme
 * @version 1.0
 */
public class RoomReservation extends UnicastRemoteObject implements RoomReservationInterface, Address {
	/**
	 * Liste des lobbys ouvert. L'attribut "rooms" peut être modifié.
	 * 
	 * @see RoomReservation#createRoom(int)
	 * @see RoomReservation#removeRoom(RoomInterface)
	 * @see RoomInterface
	 */
	private Map<String, RoomInterface> rooms = new Hashtable<String, RoomInterface>();

	/**
	 * Constructeur : RoomReservation
	 * 
	 * <p>
	 * Il permet d'initialiser la connexion "remote" de l'objet, afin que les
	 * clients puissent avoir accès à distance à cette objet.
	 * </p>
	 * 
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws AlreadyBoundException
	 * @see Address
	 */
	public RoomReservation() throws RemoteException, MalformedURLException, AlreadyBoundException {
		LocateRegistry.createRegistry(PORT);
		Naming.bind("//" + PREADRRESS + ":" + PORT + "/" + POSTADDRESS, this);
	}

	/**
	 * {@inheritDoc}
	 */
	public RoomInterface getRoom(PlayerInterface player) throws RemoteException {
		RoomInterface currentRoom = null;
		RoomInterface iterateRoom = null;

		// On cherche s'il y a de la place dans un lobby déjà existant.
		for (String roomId : this.rooms.keySet()) {
			iterateRoom = this.rooms.get(roomId);
			if (iterateRoom.getPlayers().size() + iterateRoom.getQueue() < iterateRoom.getMaxPlayer()
					&& !iterateRoom.getInGame()) {
				currentRoom = iterateRoom.roomConnection(player);
				break;
			}
		}
		// On créé un nouveau lobby si aucun n'est disponible.
		if (currentRoom == null) {
			currentRoom = createRoom(4);
			currentRoom = currentRoom.roomConnection(player);
		}

		return currentRoom;
	}

	/**
	 * {@inheritDoc}
	 */
	public RoomInterface getRoom(PlayerInterface player, String roomId) throws RemoteException {
		RoomInterface currentRoom = null;

		// On regarde si l'identifiant du lobby passé en paramètre existe bien.
		if ((currentRoom = this.rooms.get(roomId)) != null) {
			// On regarde s'il y a encore de la place dans le lobby avant d'incrire le
			// joueur dedans.
			if (currentRoom.getPlayers().size() + currentRoom.getQueue() < currentRoom.getMaxPlayer()
					&& !currentRoom.getInGame()) {
				currentRoom.roomConnection(player);
			} else {
				currentRoom = null;
			}
		}

		return currentRoom;
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeRoom(RoomInterface room) throws RemoteException {
		rooms.remove(room.getId());
	}

	/**
	 * Créer un nouveau lobby avec un nouvel identifiant unique et le retourne.
	 * 
	 * @param maxPlayer Le valeur maximale du nombre de joueur dans le lobby.
	 * @return Le nouveau lobby.
	 * @throws RemoteException
	 * @see RoomInterface
	 */
	private RoomInterface createRoom(int maxPlayer) throws RemoteException {
		RoomInterface currentRoom = null;

		currentRoom = new Room(UUID.randomUUID().toString(), maxPlayer, this);
		this.rooms.put(currentRoom.getId(), currentRoom);

		return currentRoom;
	}
}
