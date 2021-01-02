package com.alma.splashbimboombidaboum.utility;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.alma.splashbimboombidaboum.client.PlayerInterface;

public class Obstacle extends UnicastRemoteObject implements ObstacleInterface {
	private MathVectorInterface position = new MathVector();
	private MathVectorInterface direction = new MathVector();
	private float width;
	private float height;
	private int id;

	public Obstacle() throws RemoteException {

	}

	public Obstacle(float width, float height, int id) throws RemoteException {
		this.width = width;
		this.height = height;
		this.id = id;
	}

	public Obstacle(float width, float height, int id, MathVectorInterface position, MathVectorInterface direction)
			throws RemoteException {
		this.width = width;
		this.height = height;
		this.id = id;
		this.position = position;
		this.direction = direction;
	}

	public MathVectorInterface getPosition() throws RemoteException {
		return this.position;
	}

	public MathVectorInterface getDirection() throws RemoteException {
		return this.direction;
	}

	public float getWidth() throws RemoteException {
		return this.width;
	}

	public float getHeight() throws RemoteException {
		return this.height;
	}

	public int getId() throws RemoteException {
		return this.id;
	}

	public boolean collision(PlayerInterface player) throws RemoteException {
		float leftMax = Math.max(this.getPosition().getX(), player.getCoordinates().getPositionVector().getX());
		float rightMin = Math.min(this.getPosition().getX() + this.getWidth(),
				player.getCoordinates().getPositionVector().getX() + player.getCoordinates().getWidth());
		float downMax = Math.max(this.getPosition().getY(), player.getCoordinates().getPositionVector().getY());
		float upMin = Math.min(this.getPosition().getY() + this.getHeight(),
				player.getCoordinates().getPositionVector().getY() + player.getCoordinates().getHeight());

		return (leftMax < rightMin) && (downMax < upMin);
	}
}
