package com.alma.splashbimboombidaboum.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.alma.splashbimboombidaboum.client.controller.WaitingRoomController;
import com.alma.splashbimboombidaboum.server.RoomInterface;
import com.alma.splashbimboombidaboum.server.RoomReservationInterface;
import com.alma.splashbimboombidaboum.utility.Address;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class Player extends UnicastRemoteObject implements PlayerInterface, Address {
	private String name;
	private boolean ready = false;
	private Color color = Color.BLACK;
	private String colorString = "";
	private CoordinatesInterface coord;
	private LocalPlayersInterface players;
	private RoomReservationInterface server;
	private RoomInterface room;
	private Scene scene;
	private WaitingRoomController waitingRoom;

	public Player(String name) throws RemoteException {
		this.name = name;
		this.coord = new Coordinates();
	}

	public void createLocalPlayers() throws RemoteException {
		this.players = new LocalPlayers();
	}
	
	public WaitingRoomController getWaitingRoom() throws RemoteException {
		return this.waitingRoom;
	}

	public void setWaitingRoom(WaitingRoomController waitingRoom) throws RemoteException {
		this.waitingRoom = waitingRoom;
	}

	public Scene getScene() throws RemoteException {
		return this.scene;
	}

	public void setScene(Scene scene) throws RemoteException {
		this.scene = scene;
	}

	public boolean getState() throws RemoteException {
		return this.ready;
	}

	public String getColor() throws RemoteException {
		return this.colorString;
	}

	public void setColor(String color) throws RemoteException {
		this.color = Color.web(color);
		this.colorString = color;
	}

	public CoordinatesInterface getCoordinates() throws RemoteException {
		return this.coord;
	}

	public void setState(boolean ready) throws RemoteException {
		this.ready = ready;
	}

	public LocalPlayersInterface getLocalPlayers() throws RemoteException {
		return this.players;
	}

	public RoomReservationInterface getServer() throws RemoteException {
		return this.server;
	}

	public RoomInterface getRoom() throws RemoteException {
		return this.room;
	}

	public String getName() throws RemoteException {
		return this.name;
	}

	public void setRoom(RoomInterface room) throws RemoteException {
		this.room = room;
	}

	public void setName(String name) throws RemoteException {
		this.name = name;
	}

	public RoomReservationInterface connection() throws MalformedURLException, RemoteException, NotBoundException {
		this.server = (RoomReservationInterface) Naming.lookup("//" + PREADRRESS + ":" + PORT + "/" + POSTADDRESS);

		return this.server;
	}

	public RoomInterface roomConnection() throws RemoteException {
		this.room = server.getRoom(this);

		return this.room;
	}

	public RoomInterface roomConnection(String id) throws RemoteException {
		this.room = server.getRoom(this, id);

		return this.room;
	}
}
