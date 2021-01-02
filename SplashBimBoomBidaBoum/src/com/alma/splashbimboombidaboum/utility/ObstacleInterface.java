package com.alma.splashbimboombidaboum.utility;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.alma.splashbimboombidaboum.client.PlayerInterface;

public interface ObstacleInterface extends Remote {

	public MathVectorInterface getPosition() throws RemoteException;

	public MathVectorInterface getDirection() throws RemoteException;

	public boolean collision(PlayerInterface player) throws RemoteException;

	public float getWidth() throws RemoteException;

	public float getHeight() throws RemoteException;
	
	public int getId() throws RemoteException;

}
