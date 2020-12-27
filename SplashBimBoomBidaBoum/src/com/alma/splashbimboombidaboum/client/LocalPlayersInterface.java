package com.alma.splashbimboombidaboum.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;

public interface LocalPlayersInterface extends Remote {

	public ObservableList<PlayerInterface> getPlayers() throws RemoteException;

	public void addPlayer(PlayerInterface player) throws RemoteException;

	public void removePlayer(PlayerInterface player) throws RemoteException;

	public void setState(PlayerInterface player, boolean ready) throws RemoteException;

	public BooleanProperty getGameStart() throws RemoteException;
	
	public void setGameStart(boolean gameStart) throws RemoteException;
}
