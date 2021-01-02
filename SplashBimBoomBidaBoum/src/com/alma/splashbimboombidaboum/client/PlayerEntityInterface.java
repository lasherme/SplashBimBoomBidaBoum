package com.alma.splashbimboombidaboum.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.alma.splashbimboombidaboum.utility.Direction;
import com.alma.splashbimboombidaboum.utility.EntityInterface;
import com.alma.splashbimboombidaboum.utility.MathVectorInterface;

public interface PlayerEntityInterface extends EntityInterface {

	public ArrayList<Direction> getKeyDirection() throws RemoteException;

	public void addKeyDirection(Direction keyDirection) throws RemoteException;

	public void removeKeyDirection(Direction keyDirection) throws RemoteException;
}
