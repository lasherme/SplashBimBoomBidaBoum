package com.alma.splashbimboombidaboum.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import com.alma.splashbimboombidaboum.client.PlayerInterface;
import com.alma.splashbimboombidaboum.utility.Address;

public class RoomReservation extends UnicastRemoteObject implements RoomReservationInterface, Address {
	private Map<String, RoomInterface> rooms = new Hashtable<String, RoomInterface>();

	public RoomReservation() throws RemoteException, MalformedURLException, AlreadyBoundException {

		LocateRegistry.createRegistry(PORT);
		Naming.bind("//" + PREADRRESS + ":" + PORT + "/" + POSTADDRESS, this);
	}

	private RoomInterface createRoom(int maxPlayer) throws RemoteException {
		RoomInterface currentRoom = null;

		currentRoom = new Room(UUID.randomUUID().toString(), maxPlayer, this);
		this.rooms.put(currentRoom.getId(), currentRoom);

		return currentRoom;
	}

	public void removeRoom(RoomInterface room) throws RemoteException {
		System.out.println("Room deleted : " + room.getId());
		rooms.remove(room.getId());
	}

	public RoomInterface getRoom(PlayerInterface player) throws RemoteException {
		RoomInterface currentRoom = null;
		RoomInterface iterateRoom = null;

		for (String roomId : this.rooms.keySet()) {
			iterateRoom = this.rooms.get(roomId);
			if (iterateRoom.getSize() + iterateRoom.getQueue() < iterateRoom.getMaxPlayer()
					&& iterateRoom.getIsOpen()) {
				currentRoom = iterateRoom.roomConnection(player);
				break;
			}
		}
		if (currentRoom == null) {
			currentRoom = createRoom(4);
			currentRoom = currentRoom.roomConnection(player);
		}

		return currentRoom;
	}

	public RoomInterface getRoom(PlayerInterface player, String roomId) throws RemoteException {
		RoomInterface currentRoom = null;

		if ((currentRoom = this.rooms.get(roomId)) != null) {
			if (currentRoom.getSize() + currentRoom.getQueue() < currentRoom.getMaxPlayer()
					&& currentRoom.getIsOpen()) {
				currentRoom.roomConnection(player);
			} else {
				currentRoom = null;
			}
		}

		return currentRoom;
	}

}
