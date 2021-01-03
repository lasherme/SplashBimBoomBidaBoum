package com.alma.splashbimboombidaboum.utility;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <b>Interface représentant une entité.</b>
 * <p>
 * Elle peut être utilisée comme objet distant.
 * </p>
 * <p>
 * Une entité est caractérisée par les informations suivantes :
 * <ul>
 * <li>Une largeur</li>
 * <li>Une hauteur</li>
 * <li>Un vecteur position</li>
 * <li>Un vecteur directeur</li>
 * </ul>
 * </p>
 * 
 * @see MathVectorInterface
 * @author Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme
 * @version 1.0
 */
public interface EntityInterface extends Remote {
	/**
	 * Retourne la largeur de l'entité.
	 * 
	 * @return La largeur de l'entité.
	 * @throws RemoteException
	 */
	public float getWidth() throws RemoteException;

	/**
	 * Retourne la hauteur de cette entité.
	 * 
	 * @return La hauteur de l'entité.
	 * @throws RemoteException
	 */
	public float getHeight() throws RemoteException;

	/**
	 * Retourne le vecteur position de l'entité.
	 * 
	 * @return Le vecteur position.
	 * @throws RemoteException
	 * @see MathVectorInterface
	 */
	public MathVectorInterface getPosition() throws RemoteException;

	/**
	 * Retourne le vecteur directeur de l'entité.
	 * 
	 * @return Le vecteur directeur.
	 * @throws RemoteException
	 * @see MathVectorInterface
	 */
	public MathVectorInterface getDirection() throws RemoteException;

	/**
	 * Met à jour la largeur de l'entité.
	 * 
	 * @param width La nouvelle largeur.
	 * @throws RemoteException
	 */
	public void setWidth(float width) throws RemoteException;

	/**
	 * Met à jour la hauteur de l'entité.
	 * 
	 * @param height La nouvelle hauteur.
	 * @throws RemoteException
	 */
	public void setHeight(float height) throws RemoteException;

	/**
	 * Met à jour la largeur et la hauteur de l'entité.
	 * 
	 * @param size La nouvelle largeur et hauteur.
	 * @throws RemoteException
	 */
	public void setSize(float size) throws RemoteException;

	/**
	 * Met à jour le vecteur position de l'entité, à l'aide d'un vecteur.
	 * 
	 * @param position Le nouveau vecteur position.
	 * @throws RemoteException
	 * @see MathVectorInterface
	 */
	public void setPosition(MathVectorInterface position) throws RemoteException;

	/**
	 * Met à jour le vecteur position de l'entité, à l'aide de coordonnées.
	 * 
	 * @param x La nouvelle coordonnée en x.
	 * @param y La nouvelle coordonnée en y.
	 * @throws RemoteException
	 * @see MathVectorInterface
	 */
	public void setPosition(float x, float y) throws RemoteException;

	/**
	 * Met à jour le vecteur directeur de l'entité, à l'aide d'un vecteur.
	 * 
	 * @param direction Le nouveau vecteur directeur.
	 * @throws RemoteException
	 * @see MathVectorInterface
	 */
	public void setDirection(MathVectorInterface direction) throws RemoteException;

	/**
	 * Met à jour le vecteur directeur de l'entité, à l'aide de coordonnées.
	 * 
	 * @param x La nouvelle coordonnée en x.
	 * @param y La nouvelle coordonnée en y.
	 * @throws RemoteException
	 * @see MathVectorInterface
	 */
	public void setDirection(float x, float y) throws RemoteException;
}
