package com.alma.splashbimboombidaboum.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.alma.splashbimboombidaboum.utility.Direction;

public interface CoordinatesInterface extends Remote {

	public float getX() throws RemoteException;

	public float getY() throws RemoteException;

	public float getHeight() throws RemoteException;

	public float getWidth() throws RemoteException;

	public Direction getDirection() throws RemoteException;

	public void setX(float x) throws RemoteException;

	public void setY(float y) throws RemoteException;

	public void setHeight(float height) throws RemoteException;

	public void setWidth(float width) throws RemoteException;

	public void setSize(float size) throws RemoteException;

	public void setDirection(Direction direction) throws RemoteException;

}
