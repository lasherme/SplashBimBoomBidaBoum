package com.alma.splashbimboombidaboum.utility;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MathVector extends UnicastRemoteObject implements MathVectorInterface {
	private float x;
	private float y;

	public MathVector() throws RemoteException {
		this.x = 0;
		this.y = 0;
	}

	public MathVector(float x, float y) throws RemoteException {
		this.x = x;
		this.y = y;
	}

	public void setVector(float x, float y) throws RemoteException {
		this.x = x;
		this.y = y;
	}

	public void setX(float x) throws RemoteException {
		this.x = x;
	}

	public void setY(float y) throws RemoteException {
		this.y = y;
	}

	public float getX() throws RemoteException {
		return this.x;
	}

	public float getY() throws RemoteException {
		return this.y;
	}

	public MathVectorInterface sumVector(MathVectorInterface vector) throws RemoteException {
		MathVectorInterface vectorBuffer = new MathVector();
		vectorBuffer.setVector(this.getX() + vector.getX(), this.getY() + vector.getY());

		return vectorBuffer;
	}

	public MathVectorInterface timeFloatVector(float real) throws RemoteException {
		MathVectorInterface vectorBuffer = new MathVector();
		vectorBuffer.setVector(this.getX() * real, this.getY() * real);

		return vectorBuffer;
	}

	public MathVectorInterface averageVector(MathVectorInterface vector) throws RemoteException {
		MathVectorInterface vectorBuffer = this.sumVector(vector);
		vectorBuffer.setVector(vectorBuffer.getX() / 2, vectorBuffer.getY() / 2);

		return vectorBuffer;
	}
}
