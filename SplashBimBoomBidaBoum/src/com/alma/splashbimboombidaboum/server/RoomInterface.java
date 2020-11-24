package com.alma.splashbimboombidaboum.server;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.alma.splashbimboombidaboum.client.PlayerInterface;

public interface RoomInterface extends Remote {

	 String getId() throws RemoteException;

	 int getMaxPlayer() throws RemoteException;

	 int getQueue() throws RemoteException;

	 int getSize() throws RemoteException;

	RoomInterface roomConnection(PlayerInterface player) throws RemoteException, MalformedURLException, NotBoundException;

	 void isReady(PlayerInterface player) throws RemoteException;
}
