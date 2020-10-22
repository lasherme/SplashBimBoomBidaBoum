package com.alma.splashbimboombidaboum.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.alma.splashbimboombidaboum.client.PlayerInterface;
import com.alma.splashbimboombidaboum.utility.Address;

public class RoomReservation extends UnicastRemoteObject implements RoomReservationInterface, Address {
	private ArrayList<RoomInterface> rooms = new ArrayList<RoomInterface>();

	public RoomReservation() throws RemoteException, MalformedURLException, AlreadyBoundException {

		LocateRegistry.createRegistry(PORT);
		Naming.bind("//" + PREADRRESS + ":" + PORT + "/" + POSTADDRESS, this);
	}

	private synchronized RoomInterface createRoom(int maxPlayer) throws RemoteException {
		RoomInterface currentRoom = null;

		currentRoom = new Room("TO DO ID !!!", maxPlayer);
		this.rooms.add(currentRoom);

		return currentRoom;
	}

	public RoomInterface getRoom(PlayerInterface player) throws RemoteException {
		RoomInterface currentRoom = null;

		for (RoomInterface room : rooms) {
			if (room.getSize() + room.getQueue() < room.getMaxPlayer()) {
				currentRoom = room.roomConnection(player);
			}
		}
		if (currentRoom == null) {
			currentRoom = createRoom(4);
		}

		return currentRoom;
	}

	public RoomInterface getRoom(PlayerInterface player, String id) throws RemoteException {
		RoomInterface currentRoom = null;

		for (RoomInterface room : rooms) {
			if (room.getId() == null) {
				if (room.getSize() + room.getQueue() < room.getMaxPlayer()) {
					currentRoom = room.roomConnection(player);
				}
			}
		}

		return currentRoom;
	}

}
