package com.alma.splashbimboombidaboum.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.alma.splashbimboombidaboum.client.PlayerInterface;
import com.alma.splashbimboombidaboum.utility.Address;
import com.alma.splashbimboombidaboum.utility.RandomString;

public class RoomReservation extends UnicastRemoteObject implements RoomReservationInterface, Address {
	private Map<String,RoomInterface> rooms = new HashMap<>();
	private final int idLength = 6;

	public RoomReservation() throws RemoteException, MalformedURLException, AlreadyBoundException {

		LocateRegistry.createRegistry(PORT);
		Naming.bind("//" + PREADRRESS + ":" + PORT + "/" + POSTADDRESS, this);
	}

	private synchronized RoomInterface createRoom(int maxPlayer) throws RemoteException {
		RoomInterface currentRoom = null;
		String id = null;

		do{
			id = RandomString.generate(idLength); // Generates a random id
		}while(rooms.containsKey(id));
		currentRoom = new Room(id, maxPlayer);
		this.rooms.put(id,currentRoom);

		return currentRoom;
	}

	public synchronized RoomInterface getRoom(PlayerInterface player) throws RemoteException, MalformedURLException, NotBoundException { // Find an available room (for up to 4 players)
		RoomInterface currentRoom = null;

		for (String room : rooms.keySet()) {
			currentRoom = rooms.get(room);
			if (currentRoom.getSize() + currentRoom.getQueue() < currentRoom.getMaxPlayer()) { // We found an existing one
				return currentRoom.roomConnection(player);
			}
		}

		return (createRoom(4)).roomConnection(player); // We join a brand new one

	}

	public synchronized RoomInterface getRoom(PlayerInterface player, String id) throws RemoteException, MalformedURLException, NotBoundException { // Joins a specific room if exists
		RoomInterface currentRoom = null;
		if(rooms.containsKey(id) && (rooms.get(id).getSize() + rooms.get(id).getQueue() < rooms.get(id).getMaxPlayer())){
			currentRoom = rooms.get(id);
			currentRoom.roomConnection(player);
			return currentRoom;
		}
		return currentRoom;
	}

}
