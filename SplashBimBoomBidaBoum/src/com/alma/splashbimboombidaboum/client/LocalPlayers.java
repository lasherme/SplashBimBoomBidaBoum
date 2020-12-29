package com.alma.splashbimboombidaboum.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LocalPlayers extends UnicastRemoteObject implements LocalPlayersInterface {
	private ObservableList<PlayerInterface> players;
	private BooleanProperty gameStart = new SimpleBooleanProperty(false);

	public LocalPlayers() throws RemoteException {
		this.players = FXCollections.observableArrayList();
	}

	public ObservableList<PlayerInterface> getPlayers() throws RemoteException {
		return this.players;
	}

	public void addPlayer(PlayerInterface player) throws RemoteException {
		this.players.add(player);
	}

	public void removePlayer(PlayerInterface player) throws RemoteException {
		this.players.remove(player);
	}

	public void changeCoordinatesPlayer(PlayerInterface player, float x, float y) throws RemoteException {
		for (PlayerInterface currentPlayer : players) {
			if (currentPlayer.getName().equals(player.getName())) {
				currentPlayer.getCoordinates().getPositionVector().setX(x);
				currentPlayer.getCoordinates().getPositionVector().setY(y);
			}
			break;
		}
	}

	public void setState(PlayerInterface player, boolean ready) throws RemoteException {
		for (PlayerInterface currentPlayer : players) {
			if (currentPlayer.getName().equals(player.getName())) {
				currentPlayer.setState(ready);
			}
		}
	}

	public BooleanProperty getGameStart() throws RemoteException {
		return this.gameStart;
	}

	public void setGameStart(boolean gameStart) throws RemoteException {
		this.gameStart.set(gameStart);
	}
}
