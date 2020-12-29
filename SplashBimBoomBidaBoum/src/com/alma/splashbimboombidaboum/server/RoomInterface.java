package com.alma.splashbimboombidaboum.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.alma.splashbimboombidaboum.client.PlayerInterface;

public interface RoomInterface extends Remote {

	public String getId() throws RemoteException;

	public int getMaxPlayer() throws RemoteException;

	public int getQueue() throws RemoteException;

	public int getSize() throws RemoteException;

	public boolean getInGame() throws RemoteException;

	public boolean getIsOpen() throws RemoteException;

	public void removePlayer(PlayerInterface player) throws RemoteException;

	public RoomInterface roomConnection(PlayerInterface player) throws RemoteException;

	public void changeState(PlayerInterface player, boolean ready) throws RemoteException;
}
