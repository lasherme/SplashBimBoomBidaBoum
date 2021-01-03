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
	private Color color;
	private String colorString = "";
	private Scene scene;
	private WaitingRoomController waitingRoom;
	private RoomReservationInterface server;
	private RoomInterface room;
	private PlayerEntityInterface playerEntity;
	private ObservableDataInterface observablePlayers;

	public Player(String name) throws RemoteException {
		this.name = name;
		this.playerEntity = new PlayerEntity();
	}

	public String getName() throws RemoteException {
		return this.name;
	}

	public boolean getState() throws RemoteException {
		return this.ready;
	}

	public String getColor() throws RemoteException {
		return this.colorString;
	}

	public Scene getScene() throws RemoteException {
		return this.scene;
	}

	public WaitingRoomController getWaitingRoom() throws RemoteException {
		return this.waitingRoom;
	}

	public RoomReservationInterface getServer() throws RemoteException {
		return this.server;
	}

	public RoomInterface getRoom() throws RemoteException {
		return this.room;
	}

	public PlayerEntityInterface getPlayerEntity() throws RemoteException {
		return this.playerEntity;
	}

	public ObservableDataInterface getObservablePlayers() throws RemoteException {
		return this.observablePlayers;
	}

	public void setName(String name) throws RemoteException {
		this.name = name;
	}

	public void setState(boolean ready) throws RemoteException {
		this.ready = ready;
	}

	public void setColor(String color) throws RemoteException {
		this.color = Color.web(color);
		this.colorString = color;
	}

	public void setScene(Scene scene) throws RemoteException {
		this.scene = scene;
	}

	public void setWaitingRoom(WaitingRoomController waitingRoom) throws RemoteException {
		this.waitingRoom = waitingRoom;
	}

	public RoomReservationInterface connection() throws MalformedURLException, RemoteException, NotBoundException {
		this.server = (RoomReservationInterface) Naming.lookup("//" + PREADRRESS + ":" + PORT + "/" + POSTADDRESS);

		return this.server;
	}

	public void setRoom(RoomInterface room) throws RemoteException {
		this.room = room;
	}

	public void createObservablePlayers() throws RemoteException {
		this.observablePlayers = new ObservableData();
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
