package com.alma.splashbimboombidaboum.utility;

import java.rmi.RemoteException;

import com.alma.splashbimboombidaboum.client.PlayerInterface;

public interface ObstacleEntityInterface extends EntityInterface {

	public int getId() throws RemoteException;

	public boolean collision(PlayerInterface player) throws RemoteException;
}
