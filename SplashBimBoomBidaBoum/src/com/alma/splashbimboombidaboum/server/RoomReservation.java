package com.alma.splashbimboombidaboum.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.alma.splashbimboombidaboum.client.PlayerInterface;
import com.alma.splashbimboombidaboum.utility.Address;

public class RoomReservation extends UnicastRemoteObject implements RoomReservationInterface, Address {
	private Map<String, RoomInterface> rooms = new HashMap<String, RoomInterface>();

	public RoomReservation() throws RemoteException, MalformedURLException, AlreadyBoundException {

		LocateRegistry.createRegistry(PORT);
		Naming.bind("//" + PREADRRESS + ":" + PORT + "/" + POSTADDRESS, this);
	}

	private synchronized RoomInterface createRoom(int maxPlayer) throws RemoteException {
		RoomInterface currentRoom = null;

		currentRoom = new Room(UUID.randomUUID().toString(), maxPlayer);
		System.out.println(currentRoom.getId());
		this.rooms.put(currentRoom.getId(), currentRoom);

		return currentRoom;
	}

	public RoomInterface getRoom(PlayerInterface player) throws RemoteException {
		RoomInterface currentRoom = null;
		RoomInterface iterateRoom = null;

		for (String roomId : this.rooms.keySet()) {
			iterateRoom = this.rooms.get(roomId);
			if (iterateRoom.getSize() + iterateRoom.getQueue() < iterateRoom.getMaxPlayer()) {
				currentRoom = iterateRoom.roomConnection(player);
			}
		}
		if (currentRoom == null) {
			currentRoom = createRoom(4);
			currentRoom.roomConnection(player);
		}

		return currentRoom;
	}

	public RoomInterface getRoom(PlayerInterface player, String roomId) throws RemoteException {
		RoomInterface currentRoom = null;

		if ((currentRoom = this.rooms.get(roomId)) != null) {
			if (currentRoom.getSize() + currentRoom.getQueue() < currentRoom.getMaxPlayer()) {
				currentRoom.roomConnection(player);
			} else {
				currentRoom = null;
			}
		}

		return currentRoom;
	}

}
