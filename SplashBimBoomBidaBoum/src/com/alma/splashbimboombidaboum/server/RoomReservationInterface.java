package com.alma.splashbimboombidaboum.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.alma.splashbimboombidaboum.client.PlayerInterface;

public interface RoomReservationInterface extends Remote {
	
	public RoomInterface getRoom(PlayerInterface player) throws RemoteException;
	
	public RoomInterface getRoom(PlayerInterface player, String roomId) throws RemoteException;
}
