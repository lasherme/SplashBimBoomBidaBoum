package com.alma.splashbimboombidaboum.utility;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MathVectorInterface extends Remote {

	public void setVector(float x, float y) throws RemoteException;

	public float getX() throws RemoteException;

	public float getY() throws RemoteException;

	public void setX(float x) throws RemoteException;

	public void setY(float y) throws RemoteException;

	public MathVectorInterface sumVector(MathVectorInterface vector) throws RemoteException;

	public MathVectorInterface timeFloatVector(float real) throws RemoteException;

	public MathVectorInterface averageVector(MathVectorInterface vector) throws RemoteException;
}
