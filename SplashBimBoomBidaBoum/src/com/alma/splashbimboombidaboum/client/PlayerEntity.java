package com.alma.splashbimboombidaboum.client;

import java.rmi.RemoteException;
import java.util.ArrayList;

import com.alma.splashbimboombidaboum.server.Entity;
import com.alma.splashbimboombidaboum.utility.Direction;
import com.alma.splashbimboombidaboum.utility.MathVectorInterface;

public class PlayerEntity extends Entity implements PlayerEntityInterface {
	private ArrayList<Direction> keyDirection = new ArrayList<Direction>();

	public PlayerEntity() throws RemoteException {
		super();
	}

	public PlayerEntity(float size, int id) throws RemoteException {
		super(size);
	}

	public PlayerEntity(float width, float height, int id) throws RemoteException {
		super(width, height);
	}

	public PlayerEntity(float size, MathVectorInterface position, MathVectorInterface direction, int id)
			throws RemoteException {
		super(size, position, direction);
	}

	public PlayerEntity(float width, float height, MathVectorInterface position, MathVectorInterface direction, int id)
			throws RemoteException {
		super(width, height, position, direction);
	}

	public PlayerEntity(float width, float height, float positionX, float positionY, int id) throws RemoteException {
		super(width, height, positionX, positionY);
	}

	public PlayerEntity(float width, float height, float positionX, float positionY, float directionX, float directionY,
			int id) throws RemoteException {
		super(width, height, positionX, positionY, directionX, directionY);
	}

	public ArrayList<Direction> getKeyDirection() throws RemoteException {
		return this.keyDirection;
	}

	public void addKeyDirection(Direction keyDirection) throws RemoteException {
		this.keyDirection.add(keyDirection);
	}

	public void removeKeyDirection(Direction keyDirection) throws RemoteException {
		this.keyDirection.remove(keyDirection);
	}
}
