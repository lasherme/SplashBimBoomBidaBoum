package com.alma.splashbimboombidaboum.server;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.alma.splashbimboombidaboum.client.PlayerInterface;

public interface RoomReservationInterface extends Remote {
	
	public void getRoom(PlayerInterface player) throws RemoteException, MalformedURLException, NotBoundException;
	
	public void getRoom(PlayerInterface player, String id) throws RemoteException, MalformedURLException, NotBoundException;


}
