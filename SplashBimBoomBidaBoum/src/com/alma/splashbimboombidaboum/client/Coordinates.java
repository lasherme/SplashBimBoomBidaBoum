package com.alma.splashbimboombidaboum.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.alma.splashbimboombidaboum.utility.Direction;

public class Coordinates extends UnicastRemoteObject implements CoordinatesInterface {
	private float x;
	private float y;
	private float height;
	private float width;
	private Direction direction = Direction.STAY;

	public Coordinates() throws RemoteException {
	}

	public Coordinates(float x, float y) throws RemoteException {
		this.x = x;
		this.y = y;
	}

	public Coordinates(float x, float y, float size) throws RemoteException {
		this.x = x;
		this.y = y;
		this.height = size;
		this.width = size;
	}

	public Coordinates(float x, float y, float height, float width) throws RemoteException {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}

	public float getX() throws RemoteException {
		return this.x;
	}

	public float getY() throws RemoteException {
		return this.y;
	}

	public float getHeight() throws RemoteException {
		return this.height;
	}

	public float getWidth() throws RemoteException {
		return this.width;
	}

	public Direction getDirection() throws RemoteException {
		return this.direction;
	}

	public void setX(float x) throws RemoteException {
		this.x = x;
	}

	public void setY(float y) throws RemoteException {
		this.y = y;
	}

	public void setHeight(float height) throws RemoteException {
		this.height = height;
	}

	public void setWidth(float width) throws RemoteException {
		this.width = width;
	}

	public void setSize(float size) throws RemoteException {
		this.height = size;
		this.width = size;
	}

	public void setDirection(Direction direction) throws RemoteException {
		this.direction = direction;
	}
}
