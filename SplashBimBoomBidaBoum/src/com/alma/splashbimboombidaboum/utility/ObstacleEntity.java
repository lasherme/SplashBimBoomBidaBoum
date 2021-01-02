package com.alma.splashbimboombidaboum.utility;

import java.rmi.RemoteException;

import com.alma.splashbimboombidaboum.client.PlayerInterface;

public class ObstacleEntity extends Entity implements ObstacleEntityInterface {
	private int id;

	public ObstacleEntity(int id) throws RemoteException {
		super();
		this.id = id;
	}

	public ObstacleEntity(float size, int id) throws RemoteException {
		super(size);
		this.id = id;
	}

	public ObstacleEntity(float width, float height, int id) throws RemoteException {
		super(width, height);
		this.id = id;
	}

	public ObstacleEntity(float size, MathVectorInterface position, MathVectorInterface direction, int id)
			throws RemoteException {
		super(size, position, direction);
		this.id = id;
	}

	public ObstacleEntity(float width, float height, MathVectorInterface position, MathVectorInterface direction, int id)
			throws RemoteException {
		super(width, height, position, direction);
		this.id = id;
	}

	public ObstacleEntity(float width, float height, float positionX, float positionY, int id) throws RemoteException {
		super(width, height, positionX, positionY);
		this.id = id;
	}

	public ObstacleEntity(float width, float height, float positionX, float positionY, float directionX, float directionY,
			int id) throws RemoteException {
		super(width, height, positionX, positionY, directionX, directionY);
	}

	public int getId() throws RemoteException {
		return this.id;
	}

	public void setId(int id) throws RemoteException {
		this.id = id;
	}

	public boolean collision(PlayerInterface player) throws RemoteException {
		float leftMax = Math.max(this.getPosition().getX(), player.getCoordinates().getPosition().getX());
		float rightMin = Math.min(this.getPosition().getX() + this.getWidth(),
				player.getCoordinates().getPosition().getX() + player.getCoordinates().getWidth());
		float downMax = Math.max(this.getPosition().getY(), player.getCoordinates().getPosition().getY());
		float upMin = Math.min(this.getPosition().getY() + this.getHeight(),
				player.getCoordinates().getPosition().getY() + player.getCoordinates().getHeight());

		return (leftMax < rightMin) && (downMax < upMin);
	}
}
