package com.alma.splashbimboombidaboum.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.alma.splashbimboombidaboum.client.controller.WaitingRoomController;
import com.alma.splashbimboombidaboum.server.RoomInterface;
import com.alma.splashbimboombidaboum.server.RoomReservationInterface;

import javafx.scene.Scene;

/**
 * <b>Interface représentant un joueur.</b>
 * <p>
 * Elle peut être utilisée comme objet distant.
 * </p>
 * <p>
 * Un joueur est caractérisé par les informations suivantes :
 * <ul>
 * <li>Un nom</li>
 * <li>Un boolean qui indique si le joueur est prêt</li>
 * <li>Une couleur</li>
 * <li>Le code hexadécimal de la couleur</li>
 * <li>Une scène de JavaFX</li>
 * <li>Un controleur de la salle d'attente</li>
 * <li>La référence vers le serveur</li>
 * <li>La référence vers le lobby</li>
 * <li>Une entité joueur</li>
 * <li>Un objet qui encapsule les observeurs de JavaFX</li>
 * </ul>
 * </p>
 * 
 * @see ObservableDataInterface
 * @see PlayerEntityInterface
 * @see RoomInterface
 * @see RoomReservationInterface
 * @see WaitingRoomController
 * @author Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme
 * @version 1.0
 */
public interface PlayerInterface extends Remote {
	/**
	 * Retourne le nom du joueur.
	 * 
	 * @return Le nom du joueur.
	 * @throws RemoteException
	 */
	public String getName() throws RemoteException;

	/**
	 * Retourne l'état du joueur (prêt ou non).
	 * 
	 * @return L'état du joueur
	 * @throws RemoteException
	 */
	public boolean getState() throws RemoteException;

	/**
	 * Retourne le code hexadécimal de la couleur du joueur.
	 * 
	 * @return Le code hexadécimal de la couleur.
	 * @throws RemoteException
	 */
	public String getColor() throws RemoteException;

	/**
	 * Retourne la scène du joueur (scène de JavaFX).
	 * 
	 * @return La scène du joueur.
	 * @throws RemoteException
	 */
	public Scene getScene() throws RemoteException;

	/**
	 * Retourne le controleur de la salle d'attente du joueur.
	 * 
	 * @return La salle d'attente du joueur.
	 * @throws RemoteException
	 * @see WaitingRoomController
	 */
	public WaitingRoomController getWaitingRoom() throws RemoteException;

	/**
	 * Retourne le serveur auquel est connecté le joueur.
	 * 
	 * @return Le serveur du joueur.
	 * @throws RemoteException
	 * @see RoomReservationInterface
	 */
	public RoomReservationInterface getServer() throws RemoteException;

	/**
	 * Retourne le lobby auquel est rattaché le joueur.
	 * 
	 * @return Le lobby du joueur.
	 * @throws RemoteException
	 * @see RoomInterface
	 */
	public RoomInterface getRoom() throws RemoteException;

	/**
	 * Retourne l'entité joueur du joueur.
	 * 
	 * @return L'entité joueur du joueur.
	 * @throws RemoteException
	 * @see PlayerEntityInterface
	 */
	public PlayerEntityInterface getPlayerEntity() throws RemoteException;

	/**
	 * Retourne les observeurs du joueur (observeurs de JavaFX).
	 * 
	 * @return Les observeurs du joueur.
	 * @throws RemoteException
	 * @see ObservableDataInterface
	 */
	public ObservableDataInterface getObservablePlayers() throws RemoteException;

	/**
	 * Met à jour le nom du joueur.
	 * 
	 * @param name Le nouveau nom du joueur.
	 * @throws RemoteException
	 */
	public void setName(String name) throws RemoteException;

	/**
	 * Met à jour l'état du joueur.
	 * 
	 * @param ready Le nouvel état du joueur.
	 * @throws RemoteException
	 */
	public void setState(boolean ready) throws RemoteException;

	/**
	 * Met à jour la couleur et son code hexadécimal du joueur.
	 * 
	 * @param color Le nouveau code hexadécimal de la couleur du joueur.
	 * @throws RemoteException
	 */
	public void setColor(String color) throws RemoteException;

	/**
	 * Met à jour la scène du joueur.
	 * 
	 * @param scene La nouvel scène du joueur.
	 * @throws RemoteException
	 */
	public void setScene(Scene scene) throws RemoteException;

	/**
	 * Met à jour la salle d'attente du joueur.
	 * 
	 * @param waitingRoom La nouvelle salle d'attente du joueur.
	 * @throws RemoteException
	 * @see WaitingRoomController
	 */
	public void setWaitingRoom(WaitingRoomController waitingRoom) throws RemoteException;

	/**
	 * Met à jour le lobby du joueur.
	 * 
	 * @param room Le nouveau lobby du joueur.
	 * @throws RemoteException
	 * @see RoomInterface
	 */
	public void setRoom(RoomInterface room) throws RemoteException;

	/**
	 * Créé un objet encapsulant les observeurs
	 * 
	 * @throws RemoteException
	 */
	public void createObservablePlayers() throws RemoteException;

	/**
	 * Permet de connecter le joueur au serveur.
	 * 
	 * @return Le serveur auquel le joueur est connecté
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public RoomReservationInterface connection() throws MalformedURLException, RemoteException, NotBoundException;

	/**
	 * Permet de se connecter à un lobby aléatoire.
	 * 
	 * @return Le lobby du joueur.
	 * @throws RemoteException
	 * @see RoomInterface
	 */
	public RoomInterface roomConnection() throws RemoteException;

	/**
	 * Permet de se connecter à un lobby dont l'adresse est passé en paramètre.
	 * 
	 * @param id L'adresse du lobby.
	 * @return Le lobby du joueur.
	 * @throws RemoteException
	 * @see RoomInterface
	 */
	public RoomInterface roomConnection(String id) throws RemoteException;
}
