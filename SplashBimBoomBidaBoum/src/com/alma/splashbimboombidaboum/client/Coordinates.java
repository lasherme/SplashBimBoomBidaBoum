package com.alma.splashbimboombidaboum.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.alma.splashbimboombidaboum.utility.Direction;
import com.alma.splashbimboombidaboum.utility.MathVector;
import com.alma.splashbimboombidaboum.utility.MathVectorInterface;

public class Coordinates extends UnicastRemoteObject implements CoordinatesInterface {
	private float x;
	private float y;
	private MathVectorInterface positionVector;
	private MathVectorInterface directionVector;
	private float height;
	private float width;
	private ArrayList<Direction> direction = new ArrayList<Direction>();

	public Coordinates() throws RemoteException {
		this.positionVector = new MathVector();
		this.directionVector = new MathVector();
	}

	public Coordinates(float x, float y) throws RemoteException {
		this.positionVector = new MathVector(x, y);
		this.directionVector = new MathVector();
	}

	public Coordinates(float x, float y, float size) throws RemoteException {
		this.positionVector = new MathVector(x, y);
		this.directionVector = new MathVector();
		this.height = size;
		this.width = size;
	}

	public Coordinates(float x, float y, float height, float width) throws RemoteException {
		this.positionVector = new MathVector(x, y);
		this.directionVector = new MathVector();
		this.height = height;
		this.width = width;
	}

	public MathVectorInterface getPositionVector() throws RemoteException {
		return this.positionVector;
	}

	public MathVectorInterface getDirectionVector() throws RemoteException {
		return this.directionVector;
	}

	public float getHeight() throws RemoteException {
		return this.height;
	}

	public float getWidth() throws RemoteException {
		return this.width;
	}

	public ArrayList<Direction> getDirection() throws RemoteException {
		return this.direction;
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

	public void addDirection(Direction direction) throws RemoteException {
		this.direction.add(direction);
	}

	public void removeDirection(Direction direction) throws RemoteException {
		this.direction.remove(direction);
	}
}
