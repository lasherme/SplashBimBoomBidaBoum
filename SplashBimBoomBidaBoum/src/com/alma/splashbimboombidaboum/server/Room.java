package com.alma.splashbimboombidaboum.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.alma.splashbimboombidaboum.client.PlayerInterface;

public class Room extends UnicastRemoteObject implements RoomInterface {
	private String id;
	private int maxPlayer;
	private int queue = 0;
	private ArrayList<PlayerInterface> players = new ArrayList<PlayerInterface>();

	public Room(String id, int maxPlayer) throws RemoteException{
		this.id = id;
		this.maxPlayer = maxPlayer;
	}
	
	public String getId() throws RemoteException {
		return this.id;
	}
	
	public int getMaxPlayer() throws RemoteException{
		return this.maxPlayer;
	}

	public int getQueue() throws RemoteException {
		return this.queue;
	}

	public int getSize() throws RemoteException {
		return this.players.size();
	}

	public RoomInterface roomConnection(PlayerInterface player) throws RemoteException {
		RoomInterface currentRoom = null;

		queue++;
		currentRoom = roomConnectionBis(player);
		queue--;

		return currentRoom;
	}

	private synchronized RoomInterface roomConnectionBis(PlayerInterface player) throws RemoteException {
		RoomInterface currentRoom = null;

		if (this.players.size() < maxPlayer) {
			this.players.add(player);
			currentRoom = this;
		}

		return currentRoom;
	}
}
