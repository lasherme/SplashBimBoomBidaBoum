package com.alma.splashbimboombidaboum.utility;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <b>Interface représentant les vecteurs en mathématiques</b>
 * <p>
 * Elle peut être utilisée comme objet distant.
 * </p>
 * <p>
 * Un vecteur est caractérisé par les informations suivantes :
 * <ul>
 * <li>Une composante en X</li>
 * <li>Une composante en Y</li>
 * </ul>
 * </p>
 * 
 * @author Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme
 * @version 1.0
 */
public interface MathVectorInterface extends Remote {

	/**
	 * Retourne la composante X du vecteur.
	 * 
	 * @return La composante X du vecteur.
	 * @throws RemoteException
	 */
	public float getX() throws RemoteException;

	/**
	 * Retourne la composante Y du vecteur.
	 * 
	 * @return La composante Y du vecteur.
	 * @throws RemoteException
	 */
	public float getY() throws RemoteException;

	/**
	 * Met à jour la composante X du vecteur.
	 * 
	 * @param x La nouvelle composante en X.
	 * @throws RemoteException
	 */
	public void setX(float x) throws RemoteException;

	/**
	 * Met à jour la composante Y du vecteur.
	 * 
	 * @param y La nouvelle composante en Y.
	 * @throws RemoteException
	 */
	public void setY(float y) throws RemoteException;

	/**
	 * Met à jour les composantes en X et Y du vecteur.
	 * 
	 * @param x La nouvelle composante en X.
	 * @param y La nouvelle composante en Y.
	 * @throws RemoteException
	 */
	public void setVector(float x, float y) throws RemoteException;

	/**
	 * Retourne un vecteur qui fait la somme de ce vecteur avec celui passé en
	 * paramètre.
	 * 
	 * @param vector Le vecteur avec lequel faire la somme.
	 * @return Un vecteur qui est la somme des deux vecteurs.
	 * @throws RemoteException
	 */
	public MathVectorInterface sumVector(MathVectorInterface vector) throws RemoteException;

	/**
	 * Retourne un vecteur qui est la multiplication de ce vecteur par le réel passé
	 * en paramètre.
	 * 
	 * @param factor Le réel à multiplier avec ce vecteur.
	 * @return Un vecteur qui est la multiplication de ce vecteur par le réel.
	 * @throws RemoteException
	 */
	public MathVectorInterface factorVector(float factor) throws RemoteException;

	/**
	 * Retourne un vecteur qui est la moyenne des composantes de ce vecteur et celui
	 * passé en paramètre.
	 * 
	 * @param vector Le vecteur avec lequel faire la moyenne.
	 * @return Un vecteur qui est la moyenne des deux vecteurs.
	 * @throws RemoteException
	 */
	public MathVectorInterface averageVector(MathVectorInterface vector) throws RemoteException;
}
