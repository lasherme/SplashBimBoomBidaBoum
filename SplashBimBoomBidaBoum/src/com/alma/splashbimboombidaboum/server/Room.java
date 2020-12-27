package com.alma.splashbimboombidaboum.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.alma.splashbimboombidaboum.client.PlayerInterface;
import com.alma.splashbimboombidaboum.utility.Address;

public class Room extends UnicastRemoteObject implements RoomInterface, Address {
	private String id;
	private int maxPlayer;
	private int queue = 0;
	private ArrayList<PlayerInterface> players = new ArrayList<PlayerInterface>();
	/** true when players can connect to this Room, false otherwise */
	private boolean isOpen = false;

	public Room(String id, int maxPlayer) throws RemoteException {
		this.id = id;
		this.maxPlayer = maxPlayer;
		this.isOpen = true;

		System.out.println("Room ID : " + this.id);
	}

	public String getId() throws RemoteException {
		return this.id;
	}

	public int getMaxPlayer() throws RemoteException {
		return this.maxPlayer;
	}

	public int getQueue() throws RemoteException {
		return this.queue;
	}

	public int getSize() throws RemoteException {
		return this.players.size();
	}

	public boolean getIsOpen() throws RemoteException {
		return this.isOpen;
	}

	public void setMaxPlayer(int maxPlayer) {

		if (maxPlayer >= 4) {
			this.maxPlayer = maxPlayer;
		}
	}

	public void removePlayer(PlayerInterface player) throws RemoteException {

		if (this.players.contains(player)) {
			this.players.remove(player);
		}

		for (PlayerInterface currentPlayer : players) {
			currentPlayer.getLocalPlayers().removePlayer(player);
		}
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
		int i;
		String name = player.getName();

		if (this.players.size() < maxPlayer) {
			i = 1;
			// Create unique name in the room
			while (!checkName(player.getName())) {
				player.setName(name + i++);
			}
			// Update players' waiting room with new player
			for (PlayerInterface currentPlayer : players) {
				currentPlayer.getLocalPlayers().addPlayer(player);
				player.getLocalPlayers().addPlayer(currentPlayer);
			}
			this.players.add(player);
			currentRoom = this;
		}

		return currentRoom;
	}

	private boolean checkName(String name) throws RemoteException {
		for (PlayerInterface player : players) {
			if (player.getName().equals(name)) {
				return false;
			}
		}

		return true;
	}

	public void changeState(PlayerInterface player, boolean ready) throws RemoteException {
		int nbReady = 0;

		for (PlayerInterface currentPlayer : players) {
			if (currentPlayer != player) {
				currentPlayer.getLocalPlayers().setState(player, ready);
			}
			if (currentPlayer.getState()) {
				nbReady++;
			}
		}

		if (nbReady == this.getSize()) {
			isOpen = false;
			this.startGame();
		}
	}

	private void startGame() throws RemoteException {
		for (PlayerInterface player : players) {
			player.getLocalPlayers().setGameStart(true);
		}
	}
}
