package com.alma.splashbimboombidaboum.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.alma.splashbimboombidaboum.server.RoomInterface;
import com.alma.splashbimboombidaboum.server.RoomReservationInterface;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

public interface PlayerInterface extends Remote {

	public boolean getState() throws RemoteException;

	public void setState(boolean ready) throws RemoteException;

	public void setColor(String color) throws RemoteException;

	public String getColor() throws RemoteException;

	public Scene getScene() throws RemoteException;

	public void setScene(Scene scene) throws RemoteException;

	public CoordinatesInterface getCoordinates() throws RemoteException;

	public LocalPlayersInterface getLocalPlayers() throws RemoteException;

	public RoomReservationInterface getServer() throws RemoteException;

	public RoomInterface getRoom() throws RemoteException;

	public String getName() throws RemoteException;

	public void setRoom(RoomInterface room) throws RemoteException;

	public void setName(String name) throws RemoteException;

	public RoomReservationInterface connection() throws MalformedURLException, RemoteException, NotBoundException;

	public RoomInterface roomConnection() throws RemoteException;

}
