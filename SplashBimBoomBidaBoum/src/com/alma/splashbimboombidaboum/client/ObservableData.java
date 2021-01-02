package com.alma.splashbimboombidaboum.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.alma.splashbimboombidaboum.utility.ObstacleEntityInterface;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class ObservableData extends UnicastRemoteObject implements ObservableDataInterface {
	private ObservableList<PlayerInterface> players = FXCollections.observableArrayList();
	private ObservableList<ObstacleEntityInterface> obstacles = FXCollections.observableArrayList();
	private ObservableList<PlayerInterface> deads = FXCollections.observableArrayList();
	private BooleanProperty gameStart = new SimpleBooleanProperty(false);
	private ObservableMap<PlayerInterface, Boolean> ready;

	public ObservableData() throws RemoteException {
		this.ready = FXCollections.observableHashMap();
	}

	public ObservableList<PlayerInterface> getPlayers() throws RemoteException {
		return this.players;
	}
	
	public ObservableList<ObstacleEntityInterface> getObstacle() throws RemoteException {
		return this.obstacles;
	}

	public ObservableList<PlayerInterface> getDeads() throws RemoteException {
		return this.deads;
	}


	public ObservableMap<PlayerInterface, Boolean> getReady() throws RemoteException {
		return this.ready;
	}

	public BooleanProperty getGameStart() throws RemoteException {
		return this.gameStart;
	}

	public void setGameStart(boolean gameStart) throws RemoteException {
		this.gameStart.set(gameStart);
	}

	public void addDead(PlayerInterface player) throws RemoteException {
		this.deads.add(player);
	}

	public void addObstacle(ObstacleEntityInterface obstacle) throws RemoteException {
		this.obstacles.add(obstacle);
	}

	public void removeObstacle(ObstacleEntityInterface obstacle) throws RemoteException {
		this.obstacles.remove(obstacle);
	}

	public void addPlayer(PlayerInterface player) throws RemoteException {
		this.ready.put(player, false);
		this.players.add(player);
	}

	public void removePlayer(PlayerInterface player) throws RemoteException {
		this.players.remove(player);
	}

	public void changeCoordinatesPlayer(PlayerInterface player, float x, float y) throws RemoteException {
		for (PlayerInterface currentPlayer : players) {
			if (currentPlayer.getName().equals(player.getName())) {
				currentPlayer.getCoordinates().getPosition().setX(x);
				currentPlayer.getCoordinates().getPosition().setY(y);
			}
			break;
		}
	}

	public void setState(PlayerInterface player, boolean ready) throws RemoteException {
		/*for (PlayerInterface currentPlayer : players) {
			if (currentPlayer.getName().equals(player.getName())) {
				currentPlayer.setState(ready);
			}
		}*/
	}

	public void reset() throws RemoteException {
		this.deads = FXCollections.observableArrayList();
		this.obstacles = FXCollections.observableArrayList();
	}

}
