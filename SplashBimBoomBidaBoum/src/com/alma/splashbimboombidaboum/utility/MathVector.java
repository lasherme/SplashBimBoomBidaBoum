package com.alma.splashbimboombidaboum.utility;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * <b>Classe représentant un vecteur en mathématiques.</b>
 * <p>
 * Elle peut être aussi utilisée comme objet distant.
 * </p>
 * <p>
 * Voir {@link MathVectorInterface} pour plus de renseignements.
 * </p>
 * 
 * @author Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme
 * @version 1.0
 */
public class MathVector extends UnicastRemoteObject implements MathVectorInterface {
	/**
	 * Composante en X du vecteur. L'attribut "x" peut être modifié.
	 * 
	 * @see MathVector#getX()
	 * @see MathVector#setX(float)
	 */
	private float x;

	/**
	 * Composante en Y du vecteur. L'attribut "y" peut être modifié.
	 * 
	 * @see MathVector#getY()
	 * @see MathVector#setY(float)
	 */
	private float y;

	/**
	 * Constructeur par défaut : MathVector.
	 * 
	 * <p>
	 * À la construction d'un objet MathVector avec ce constructeur, les valeurs des
	 * composantes en X et Y sont assignées arbitraiement.
	 * </p>
	 * <p>
	 * Voici à quoi elles sont initialisées :
	 * <ul>
	 * <li>x = 0</li>
	 * <li>y = 0</li>
	 * </ul>
	 * </p>
	 * 
	 * @throws RemoteException
	 * @see MathVector#x
	 * @see MathVector#y
	 */
	public MathVector() throws RemoteException {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Constructeur renseignant les valeurs des composantes en X et Y.
	 * 
	 * <p>
	 * À la construction d'un objet MathVector avec ce constructeur, les valeurs des
	 * composantes en X et Y sont initialisées grâce aux paramètres.
	 * </p>
	 * 
	 * @param x La valeur pour la composante en X de ce vecteur.
	 * @param y La valeur pour la composante en Y de ce vecteur.
	 * @throws RemoteException
	 * @see MathVector#x
	 * @see MathVector#y
	 */
	public MathVector(float x, float y) throws RemoteException {
		this.x = x;
		this.y = y;
	}

	/**
	 * {@inheritDoc}
	 */
	public float getX() throws RemoteException {
		return this.x;
	}

	/**
	 * {@inheritDoc}
	 */
	public float getY() throws RemoteException {
		return this.y;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setX(float x) throws RemoteException {
		this.x = x;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setY(float y) throws RemoteException {
		this.y = y;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setVector(float x, float y) throws RemoteException {
		this.setX(x);
		this.setY(y);
	}

	/**
	 * {@inheritDoc}
	 */
	public MathVectorInterface sumVector(MathVectorInterface vector) throws RemoteException {
		return new MathVector(this.getX() + vector.getX(), this.getY() + vector.getY());
	}

	/**
	 * {@inheritDoc}
	 */
	public MathVectorInterface factorVector(float factor) throws RemoteException {
		return new MathVector(this.getX() * factor, this.getY() * factor);
	}

	/**
	 * {@inheritDoc}
	 */
	public MathVectorInterface averageVector(MathVectorInterface vector) throws RemoteException {
		MathVectorInterface vectorBuffer = this.sumVector(vector);
		vectorBuffer.setVector(vectorBuffer.getX() / 2, vectorBuffer.getY() / 2);

		return vectorBuffer;
	}
}
