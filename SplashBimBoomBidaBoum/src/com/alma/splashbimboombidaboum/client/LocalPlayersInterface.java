package com.alma.splashbimboombidaboum.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.alma.splashbimboombidaboum.utility.ObstacleInterface;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface LocalPlayersInterface extends Remote {

	public ObservableList<PlayerInterface> getPlayers() throws RemoteException;

	public ObservableList<ObstacleInterface> getObstacle() throws RemoteException;

	public void addObstacle(ObstacleInterface obstacle) throws RemoteException;

	public ObservableMap<PlayerInterface, Boolean> getReady() throws RemoteException;

	public ObservableList<PlayerInterface> getDeads() throws RemoteException;

	public void addDead(PlayerInterface player) throws RemoteException;

	public void removeObstacle(ObstacleInterface obstacle) throws RemoteException;

	public void addPlayer(PlayerInterface player) throws RemoteException;

	public void removePlayer(PlayerInterface player) throws RemoteException;

	public void changeCoordinatesPlayer(PlayerInterface player, float x, float y) throws RemoteException;

	public void setState(PlayerInterface player, boolean ready) throws RemoteException;

	public BooleanProperty getGameStart() throws RemoteException;

	public void reset() throws RemoteException;

	public void setGameStart(boolean gameStart) throws RemoteException;
}
