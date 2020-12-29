package com.alma.splashbimboombidaboum.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.alma.splashbimboombidaboum.utility.Direction;
import com.alma.splashbimboombidaboum.utility.MathVectorInterface;

public interface CoordinatesInterface extends Remote {

	public float getHeight() throws RemoteException;

	public float getWidth() throws RemoteException;

	public ArrayList<Direction> getDirection() throws RemoteException;

	public void setHeight(float height) throws RemoteException;

	public void setWidth(float width) throws RemoteException;

	public void setSize(float size) throws RemoteException;

	public void addDirection(Direction direction) throws RemoteException;
	
	public void removeDirection(Direction direction) throws RemoteException;

	public MathVectorInterface getPositionVector() throws RemoteException;

	public MathVectorInterface getDirectionVector() throws RemoteException;

}
