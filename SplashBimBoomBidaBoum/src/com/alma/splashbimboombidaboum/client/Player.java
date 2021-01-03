package com.alma.splashbimboombidaboum.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.alma.splashbimboombidaboum.client.controller.WaitingRoomController;
import com.alma.splashbimboombidaboum.server.EntityInterface;
import com.alma.splashbimboombidaboum.server.RoomInterface;
import com.alma.splashbimboombidaboum.server.RoomReservationInterface;
import com.alma.splashbimboombidaboum.utility.Address;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * <b>Classe représentant un joueur.</b>
 * <p>
 * Elle peut être aussi utilisée comme objet distant.
 * </p>
 * <p>
 * Voir {@link PlayerInterface} pour plus de renseignements.
 * </p>
 * 
 * @see Address
 * @author Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme
 * @version 1.0
 */
public class Player extends UnicastRemoteObject implements PlayerInterface, Address {
	private String name;
	private boolean ready = false;
	private Color color;
	private String colorString = "";
	private Scene scene;
	private WaitingRoomController waitingRoom;
	private RoomReservationInterface server;
	private RoomInterface room;
	private PlayerEntityInterface playerEntity;
	private ObservableDataInterface observablePlayers;

	/**
	 * Constructeur renseignant le nom du joueur.
	 * 
	 * <p>
	 * Initialise aussi l'entité du joueur.
	 * </p>
	 * 
	 * @param name Le nom du joueur.
	 * @throws RemoteException
	 */
	public Player(String name) throws RemoteException {
		this.name = name;
		this.playerEntity = new PlayerEntity();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() throws RemoteException {
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean getState() throws RemoteException {
		return this.ready;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getColor() throws RemoteException {
		return this.colorString;
	}

	/**
	 * {@inheritDoc}
	 */
	public Scene getScene() throws RemoteException {
		return this.scene;
	}

	/**
	 * {@inheritDoc}
	 */
	public WaitingRoomController getWaitingRoom() throws RemoteException {
		return this.waitingRoom;
	}

	/**
	 * {@inheritDoc}
	 */
	public RoomReservationInterface getServer() throws RemoteException {
		return this.server;
	}

	/**
	 * {@inheritDoc}
	 */
	public RoomInterface getRoom() throws RemoteException {
		return this.room;
	}

	/**
	 * {@inheritDoc}
	 */
	public PlayerEntityInterface getPlayerEntity() throws RemoteException {
		return this.playerEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	public ObservableDataInterface getObservablePlayers() throws RemoteException {
		return this.observablePlayers;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setName(String name) throws RemoteException {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setState(boolean ready) throws RemoteException {
		this.ready = ready;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setColor(String color) throws RemoteException {
		this.color = Color.web(color);
		this.colorString = color;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setScene(Scene scene) throws RemoteException {
		this.scene = scene;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setWaitingRoom(WaitingRoomController waitingRoom) throws RemoteException {
		this.waitingRoom = waitingRoom;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRoom(RoomInterface room) throws RemoteException {
		this.room = room;
	}

	/**
	 * {@inheritDoc}
	 */
	public void createObservablePlayers() throws RemoteException {
		this.observablePlayers = new ObservableData();
	}

	/**
	 * {@inheritDoc}
	 */
	public RoomReservationInterface connection() throws MalformedURLException, RemoteException, NotBoundException {
		this.server = (RoomReservationInterface) Naming.lookup("//" + PREADRRESS + ":" + PORT + "/" + POSTADDRESS);

		return this.server;
	}

	/**
	 * {@inheritDoc}
	 */
	public RoomInterface roomConnection() throws RemoteException {
		this.room = server.getRoom(this);

		return this.room;
	}

	/**
	 * {@inheritDoc}
	 */
	public RoomInterface roomConnection(String id) throws RemoteException {
		this.room = server.getRoom(this, id);

		return this.room;
	}
}
