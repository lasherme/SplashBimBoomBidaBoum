package com.alma.splashbimboombidaboum.utility;

/**
 * <b>Interface comportant les différentes constantes nécessaire au jeu.</b>
 * 
 * @author Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme
 * @version 1.0
 */
public interface InGameValues {
	/*
	 * Constante de la taille d'un joueur.
	 */
	public final int SIZE = 50;

	/**
	 * Constante de la vitesse d'un joueur.
	 */
	public final int PLAYER_SPEED = 4;
	
	/**
	 * Constante de la vitesse des obstacles.
	 */
	public final int OBSTACLE_SPEED = 2;

	/**
	 * Constante de l'espacement entre deux joueurs.
	 */
	public final float SPACING = 20;

	/**
	 * Constante du taux de rafraîchissement pour la boucle de jeu principale.
	 */
	public final int GAME_REFRESHEMENT = 20;

	/**
	 * Constante de rafraîchissement de l'appartion d'obstacle.
	 */
	public final int OBSTACLE_REFRESHEMENT = 5000;

	/**
	 * Constante gravité sur la composante X.
	 */
	public final float GRAVITY_X = 0;

	/**
	 * Constante de gravité sur la composante Y.
	 */
	public final float GRAVITY_Y = -7;
}
