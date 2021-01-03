package com.alma.splashbimboombidaboum.server;

import java.rmi.RemoteException;

import com.alma.splashbimboombidaboum.client.PlayerInterface;
import com.alma.splashbimboombidaboum.utility.MathVectorInterface;

/**
 * <b>Classe représentant un obstacle.</b>
 * <p>
 * Elle peut être aussi utilisée comme objet distant. Elle hérite des traits
 * d'une entité.
 * </p>
 * <p>
 * Voir {@link ObstacleEntityInterface} pour plus de renseignements.
 * </p>
 * 
 * @see EntityInterface
 * @author Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme
 * @version 1.0
 */
public class ObstacleEntity extends Entity implements ObstacleEntityInterface {
	/**
	 * Identifiant unique de l'obstacle. L'attribut "id" ne peut pas être modifié.
	 * 
	 * @see ObstacleEntity#getId()
	 */
	private int id;

	/**
	 * Même constructeur que Entity dont l'objet hérite, avec en plus un
	 * identifiant.
	 * 
	 * @param id L'identifiant de l'obstacle.
	 * @throws RemoteException
	 * @see Entity#width
	 * @see Entity#height
	 * @see Entity#position
	 * @see Entity#direction
	 * @see ObstacleEntity#id
	 * @see Entity
	 */
	public ObstacleEntity(int id) throws RemoteException {
		super();
		this.id = id;
	}

	/**
	 * Même constructeur que Entity dont l'objet hérite, avec en plus un
	 * identifiant.
	 * 
	 * @param size La valeur pour la largeur et la hauteur de l'obstacle.
	 * @param id   L'identifiant de l'obstacle.
	 * @throws RemoteException
	 * @see Entity#width
	 * @see Entity#height
	 * @see Entity#position
	 * @see Entity#direction
	 * @see ObstacleEntity#id
	 * @see Entity
	 */
	public ObstacleEntity(float size, int id) throws RemoteException {
		super(size);
		this.id = id;
	}

	/**
	 * Même constructeur que Entity dont l'objet hérite, avec en plus un
	 * identifiant.
	 * 
	 * @param width  La valeur pour la largeur de l'obstacle.
	 * @param height La valeur pour la hauteur de l'obstacle.
	 * @param id     L'identifiant de l'obstacle.
	 * @throws RemoteException
	 * @see Entity#width
	 * @see Entity#height
	 * @see Entity#position
	 * @see Entity#direction
	 * @see ObstacleEntity#id
	 * @see Entity
	 */
	public ObstacleEntity(float width, float height, int id) throws RemoteException {
		super(width, height);
		this.id = id;
	}

	/**
	 * Même constructeur que Entity dont l'objet hérite, avec en plus un
	 * identifiant.
	 * 
	 * @param size      La valeur pour la largeur et la hauteur de l'obstacle.
	 * @param position  Le vecteur position de l'obstacle.
	 * @param direction Le vecteur direction de l'obstacle.
	 * @param id        L'identifiant de l'obstacle.
	 * @throws RemoteException
	 * @see Entity#width
	 * @see Entity#height
	 * @see Entity#position
	 * @see Entity#direction
	 * @see ObstacleEntity#id
	 * @see Entity
	 */
	public ObstacleEntity(float size, MathVectorInterface position, MathVectorInterface direction, int id)
			throws RemoteException {
		super(size, position, direction);
		this.id = id;
	}

	/**
	 * Même constructeur que Entity dont l'objet hérite, avec en plus un
	 * identifiant.
	 * 
	 * @param width     La valeur pour la largeur de l'obstacle.
	 * @param height    La valeur pour la hauteur de l'obstacle.
	 * @param position  Le vecteur position de l'obstacle.
	 * @param direction Le vecteur direction de l'obstacle.
	 * @param id        L'identifiant de l'obstacle.
	 * @throws RemoteException
	 * @see Entity#width
	 * @see Entity#height
	 * @see Entity#position
	 * @see Entity#direction
	 * @see ObstacleEntity#id
	 * @see Entity
	 */
	public ObstacleEntity(float width, float height, MathVectorInterface position, MathVectorInterface direction,
			int id) throws RemoteException {
		super(width, height, position, direction);
		this.id = id;
	}

	/**
	 * Même constructeur que Entity dont l'objet hérite, avec en plus un
	 * identifiant.
	 * 
	 * @param width     La valeur pour la largeur de l'obstacle.
	 * @param height    La valeur pour la hauteur de l'obstacle.
	 * @param positionX La valeur de la composante en X du veteur position.
	 * @param positionY La valeur de la composante en Y du veteur position.
	 * @param id        L'identifiant de l'obstacle.
	 * @throws RemoteException
	 * @see Entity#width
	 * @see Entity#height
	 * @see Entity#position
	 * @see Entity#direction
	 * @see ObstacleEntity#id
	 * @see Entity
	 */
	public ObstacleEntity(float width, float height, float positionX, float positionY, int id) throws RemoteException {
		super(width, height, positionX, positionY);
		this.id = id;
	}

	/**
	 * Même constructeur que Entity dont l'objet hérite, avec en plus un
	 * identifiant.
	 * 
	 * @param width      La valeur pour la largeur de l'obstacle.
	 * @param height     La valeur pour la hauteur de l'obstacle.
	 * @param positionX  La valeur de la composante en X du veteur position.
	 * @param positionY  La valeur de la composante en Y du veteur position.
	 * @param directionX La valeur de la composante en X du veteur direction.
	 * @param directionY La valeur de la composante en Y du veteur direction.
	 * @param id         L'identifiant de l'obstacle.
	 * @throws RemoteException
	 * @see Entity#width
	 * @see Entity#height
	 * @see Entity#position
	 * @see Entity#direction
	 * @see ObstacleEntity#id
	 * @see Entity
	 */
	public ObstacleEntity(float width, float height, float positionX, float positionY, float directionX,
			float directionY, int id) throws RemoteException {
		super(width, height, positionX, positionY, directionX, directionY);
	}

	/**
	 * {@inheritDoc}
	 */
	public int getId() throws RemoteException {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean collision(PlayerInterface player) throws RemoteException {
		float leftMax = Math.max(this.getPosition().getX(), player.getPlayerEntity().getPosition().getX());
		float rightMin = Math.min(this.getPosition().getX() + this.getWidth(),
				player.getPlayerEntity().getPosition().getX() + player.getPlayerEntity().getWidth());
		float downMax = Math.max(this.getPosition().getY(), player.getPlayerEntity().getPosition().getY());
		float upMin = Math.min(this.getPosition().getY() + this.getHeight(),
				player.getPlayerEntity().getPosition().getY() + player.getPlayerEntity().getHeight());

		return (leftMax < rightMin) && (downMax < upMin);
	}
}
