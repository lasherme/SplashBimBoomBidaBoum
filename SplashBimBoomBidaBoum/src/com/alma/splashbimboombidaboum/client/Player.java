package com.alma.splashbimboombidaboum.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.alma.splashbimboombidaboum.server.RoomInterface;
import com.alma.splashbimboombidaboum.server.RoomReservationInterface;
import com.alma.splashbimboombidaboum.utility.Address;

public class Player extends UnicastRemoteObject implements PlayerInterface, Address {
	private String name;
	private boolean ready = false;
	private LocalPlayersInterface players;
	private RoomReservationInterface server;
	private RoomInterface room;

	public Player(String name) throws RemoteException {
		this.name = name;
		this.players = new LocalPlayers();
	}
	
	public boolean getState() throws RemoteException{
		return this.ready;
	}
	
	public void setState(boolean ready) throws RemoteException{
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
