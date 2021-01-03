package com.alma.splashbimboombidaboum.utility;

import java.rmi.RemoteException;

import com.alma.splashbimboombidaboum.client.PlayerInterface;

/**
 * <b>Interface représentant un obstacle</b>
 * <p>
 * Elle peut être utilisée comme objet distant. Elle hérite des traits de
 * l'interface d'une entité.
 * </p>
 * 
 * Un obstacle est caractérisé par toutes les informations d'une entité en plus
 * de la suivante :
 * <ul>
 * <li>Un identifiant unique</li>
 * </ul>
 * </p>
 * 
 * @see EntityInterface
 * @author Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme
 * @version 1.0
 */
public interface ObstacleEntityInterface extends EntityInterface {
	/**
	 * Retourne l'identifiant de l'obstacle.
	 * 
	 * @return L'identifiant de l'obstacle.
	 * @throws RemoteException
	 */
	public int getId() throws RemoteException;

	/**
	 * Retourne un boolean qui informe sur la collision ou non de cet obstacle avec
	 * un joueur.
	 * 
	 * @param player Le joueur avec lequel il y a collision ou non.
	 * @return true s'il y a collision, false sinon.
	 * @throws RemoteException
	 * @see PlayerInterface
	 */
	public boolean collision(PlayerInterface player) throws RemoteException;
}
