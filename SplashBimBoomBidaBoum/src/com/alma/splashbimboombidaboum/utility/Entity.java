package com.alma.splashbimboombidaboum.utility;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * <b>Classe abstraite représentant une entité.</b>
 * <p>
 * Elle peut être aussi utilisée comme objet distant.
 * </p>
 * <p>
 * Voir {@link EntityInterface} pour plus de renseignements.
 * </p>
 * 
 * @see MathVectorInterface
 * @author Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme
 * @version 1.0
 */
public abstract class Entity extends UnicastRemoteObject implements EntityInterface {
	/**
	 * Largeur de l'entité. L'attribut "width" peut être modifié.
	 * 
	 * @see Entity#getWidth()
	 * @see Entity#setWidth(float)
	 */
	private float width;
	/**
	 * Hauteur de l'entité. L'attribut "height" peut être modifié.
	 * 
	 * @see Entity#getHeight()
	 * @see Entity#setHeight(float)
	 */
	private float height;
	/**
	 * Vecteur position de l'entité. L'attribut "position" peut être modifié.
	 * 
	 * @see Entity#getPosition()
	 * @see Entity#setPosition(MathVectorInterface)
	 * @see Entity#setPosition(float, float)
	 */
	private MathVectorInterface position;
	/**
	 * Vecteur directeur de l'entité. L'attribut "direction" peut être modifié.
	 * 
	 * @see Entity#getDirection()
	 * @see Entity#setDirection(MathVectorInterface)
	 * @see Entity#setDirection(float, float)
	 */
	private MathVectorInterface direction;

	/**
	 * Contructeur par défaut : Entity.
	 * <p>
	 * À la construction d'un objet Entity avec ce constructeur, les valeurs sont
	 * assignées arbitrairement.
	 * </p>
	 * <p>
	 * Voici à quoi elles sont initialisées :
	 * <ul>
	 * <li>width = 10</li>
	 * <li>height = 10</li>
	 * <li>position = new MathVector(0,0)</li>
	 * <li>direction = new MathVector(0,0)</li>
	 * </ul>
	 * </p>
	 * 
	 * @throws RemoteException
	 * @see Entity#width
	 * @see Entity#height
	 * @see Entity#position
	 * @see Entity#direction
	 * @see MathVectorInterface
	 */
	public Entity() throws RemoteException {
		this(10);
	}

	/**
	 * Constructeur renseignant la taille : Entity.
	 * 
	 * 
	 * @param size
	 * @throws RemoteException
	 */
	public Entity(float size) throws RemoteException {
		this(size, size);
	}

	public Entity(float width, float height) throws RemoteException {
		this(width, height, 0, 0, 0, 0);
	}

	public Entity(float size, MathVectorInterface position, MathVectorInterface direction) throws RemoteException {
		this(size, size, position, direction);
	}

	public Entity(float width, float height, MathVectorInterface position, MathVectorInterface direction)
			throws RemoteException {
		this(width, height, position.getX(), position.getY(), direction.getX(), direction.getY());
	}

	public Entity(float width, float height, float positionX, float positionY) throws RemoteException {
		this(width, height, positionX, positionY, 0, 0);
	}

	public Entity(float width, float height, float positionX, float positionY, float directionX, float directionY)
			throws RemoteException {
		this.width = width;
		this.height = height;
		this.position = new MathVector(positionX, positionY);
		this.direction = new MathVector(directionX, directionY);
	}

	public float getWidth() throws RemoteException {
		return this.width;
	}

	public float getHeight() throws RemoteException {
		return this.height;
	}

	public MathVectorInterface getPosition() throws RemoteException {
		return this.position;
	}

	public MathVectorInterface getDirection() throws RemoteException {
		return this.direction;
	}

	public void setWidth(float width) throws RemoteException {
		this.width = width;
	}

	public void setHeight(float height) throws RemoteException {
		this.height = height;
	}

	public void setSize(float size) throws RemoteException {
		this.setWidth(size);
		this.setHeight(size);
	}

	public void setPosition(MathVectorInterface position) throws RemoteException {
		this.setPosition(position.getX(), position.getY());
	}

	public void setPosition(float x, float y) throws RemoteException {
		this.position.setX(x);
		this.position.setY(y);
	}

	public void setDirection(MathVectorInterface direction) throws RemoteException {
		this.setPosition(direction.getX(), direction.getY());
	}

	public void setDirection(float x, float y) throws RemoteException {
		this.direction.setX(x);
		this.direction.setY(y);
	}
}
