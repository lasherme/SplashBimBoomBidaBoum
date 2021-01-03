package com.alma.splashbimboombidaboum.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.alma.splashbimboombidaboum.utility.MathVector;
import com.alma.splashbimboombidaboum.utility.MathVectorInterface;

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
	 * Vecteur direction de l'entité. L'attribut "direction" peut être modifié.
	 * 
	 * @see Entity#getDirection()
	 * @see Entity#setDirection(MathVectorInterface)
	 * @see Entity#setDirection(float, float)
	 */
	private MathVectorInterface direction;

	/**
	 * Contructeur par défaut : Entity.
	 * 
	 * <p>
	 * À la construction d'un objet Entity avec ce constructeur, toutes les valeurs
	 * sont assignées arbitrairement.
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
	 * <p>
	 * À la construction d'un objet Entity avec ce constructeur, les valeurs des
	 * vecteurs sont assignées arbitrairement.
	 * </p>
	 * <p>
	 * Voici à quoi les vecteurs sont initialisés :
	 * <ul>
	 * <li>position = new MathVector(0,0)</li>
	 * <li>direction = new MathVector(0,0)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param size La valeur pour la largeur et la hauteur de l'entité.
	 * @throws RemoteException
	 * @see Entity#width
	 * @see Entity#height
	 * @see Entity#position
	 * @see Entity#direction
	 * @see MathVectorInterface
	 */
	public Entity(float size) throws RemoteException {
		this(size, size);
	}

	/**
	 * Constructeur renseignant la largeur et la hauteur : Entity.
	 * 
	 * <p>
	 * À la construction d'un objet Entity avec ce constructeur, les valeurs des
	 * vecteurs sont assignées arbitrairement.
	 * </p>
	 * <p>
	 * Voici à quoi les vecteurs sont initialisés :
	 * <ul>
	 * <li>position = new MathVector(0,0)</li>
	 * <li>direction = new MathVector(0,0)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param width  La valeur pour la largeur de l'entité.
	 * @param height La valeur pour la hauteur de l'entité.
	 * @throws RemoteException
	 * @see Entity#width
	 * @see Entity#height
	 * @see Entity#position
	 * @see Entity#direction
	 * @see MathVectorInterface
	 */
	public Entity(float width, float height) throws RemoteException {
		this(width, height, 0, 0, 0, 0);
	}

	/**
	 * Constructeur renseignant la taille et les vecteurs : Entity.
	 * 
	 * <p>
	 * À la construction d'un objet Entity avec ce constructeur, toutes les valeurs
	 * sont initialisées grâce aux paramètres.
	 * </p>
	 * 
	 * @param size      La valeur pour la largeur et la hauteur de l'entité.
	 * @param position  Le vecteur position de l'entité.
	 * @param direction Le vecteur direction de l'entité.
	 * @throws RemoteException
	 * @see Entity#width
	 * @see Entity#height
	 * @see Entity#position
	 * @see Entity#direction
	 * @see MathVectorInterface
	 */
	public Entity(float size, MathVectorInterface position, MathVectorInterface direction) throws RemoteException {
		this(size, size, position, direction);
	}

	/**
	 * Constructeur renseignant la largeur, la hauteur et les vecteurs : Entity.
	 * 
	 * <p>
	 * À la construction d'un objet Entity avec ce constructeur, toutes les valeurs
	 * sont initialisées grâce aux paramètres.
	 * </p>
	 * 
	 * 
	 * @param width     La valeur pour la largeur de l'entité.
	 * @param height    La valeur pour la hauteur de l'entité.
	 * @param position  Le vecteur position de l'entité.
	 * @param direction Le vecteur direction de l'entité.
	 * @throws RemoteException
	 * @see Entity#width
	 * @see Entity#height
	 * @see Entity#position
	 * @see Entity#direction
	 * @see MathVectorInterface
	 */
	public Entity(float width, float height, MathVectorInterface position, MathVectorInterface direction)
			throws RemoteException {
		this(width, height, position.getX(), position.getY(), direction.getX(), direction.getY());
	}

	/**
	 * Constructeur renseignant la largeur, la hauteur et les valeurs du vecteur
	 * position : Entity.
	 * 
	 * <p>
	 * À la construction d'un objet Entity avec ce constructeur, les valeurs du
	 * vecteur direction sont assignées arbitrairement.
	 * </p>
	 * <p>
	 * Voici à quoi le vecteur direction est initialisé :
	 * <ul>
	 * <li>direction = new MathVector(0,0)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param width     La valeur pour la largeur de l'entité.
	 * @param height    La valeur pour la hauteur de l'entité.
	 * @param positionX La valeur de la composante en X du veteur position.
	 * @param positionY La valeur de la composante en Y du veteur position.
	 * @throws RemoteException
	 * @see Entity#width
	 * @see Entity#height
	 * @see Entity#position
	 * @see Entity#direction
	 * @see MathVectorInterface
	 */
	public Entity(float width, float height, float positionX, float positionY) throws RemoteException {
		this(width, height, positionX, positionY, 0, 0);
	}

	/**
	 * Constructeur renseignant la largeur, la hauteur et les valeurs des vecteurs :
	 * Entity.
	 * 
	 * <p>
	 * À la construction d'un objet Entity avec ce constructeur, toutes les valeurs
	 * sont initialisées grâce aux paramètres.
	 * </p>
	 * 
	 * @param width      La valeur pour la largeur de l'entité.
	 * @param height     La valeur pour la hauteur de l'entité.
	 * @param positionX  La valeur de la composante en X du veteur position.
	 * @param positionY  La valeur de la composante en Y du veteur position.
	 * @param directionX La valeur de la composante en X du veteur direction.
	 * @param directionY La valeur de la composante en Y du veteur direction.
	 * @throws RemoteException
	 * @see Entity#width
	 * @see Entity#height
	 * @see Entity#position
	 * @see Entity#direction
	 * @see MathVectorInterface
	 */
	public Entity(float width, float height, float positionX, float positionY, float directionX, float directionY)
			throws RemoteException {
		this.width = width;
		this.height = height;
		this.position = new MathVector(positionX, positionY);
		this.direction = new MathVector(directionX, directionY);
	}

	/**
	 * {@inheritDoc}
	 */
	public float getWidth() throws RemoteException {
		return this.width;
	}

	/**
	 * {@inheritDoc}
	 */
	public float getHeight() throws RemoteException {
		return this.height;
	}

	/**
	 * {@inheritDoc}
	 */
	public MathVectorInterface getPosition() throws RemoteException {
		return this.position;
	}

	/**
	 * {@inheritDoc}
	 */
	public MathVectorInterface getDirection() throws RemoteException {
		return this.direction;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setWidth(float width) throws RemoteException {
		this.width = width;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setHeight(float height) throws RemoteException {
		this.height = height;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSize(float size) throws RemoteException {
		this.setWidth(size);
		this.setHeight(size);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setPosition(MathVectorInterface position) throws RemoteException {
		this.setPosition(position.getX(), position.getY());
	}

	/**
	 * {@inheritDoc}
	 */
	public void setPosition(float x, float y) throws RemoteException {
		this.position.setX(x);
		this.position.setY(y);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDirection(MathVectorInterface direction) throws RemoteException {
		this.setPosition(direction.getX(), direction.getY());
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDirection(float x, float y) throws RemoteException {
		this.direction.setX(x);
		this.direction.setY(y);
	}
}
