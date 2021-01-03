package com.alma.splashbimboombidaboum.utility;

import com.alma.splashbimboombidaboum.client.PlayerInterface;

/**
 * <b>Enumération des couleurs que peuvent prendre les joueurs, avec leur code
 * hexadécimal.</b>
 * 
 * @see PlayerInterface
 * @author Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme
 * @version 1.0
 */
public enum PlayerColor {
	/**
	 * Couleur bleue.
	 */
	BLUE("#1278d3"),
	/**
	 * Couleur rouge.
	 */
	RED("#d32912"),
	/**
	 * Couleur violette.
	 */
	PURPLE("#a112d3"),
	/**
	 * Couleur jaune.
	 */
	YELLOW("#ecf61f"),
	/**
	 * Couleur rose.
	 */
	PINK("#ff03ec"),
	/**
	 * Couleur verte.
	 */
	GREEN("#5cf50a");

	/**
	 * Code hexadécimal de la couleur associée. L'attribut "value" ne paut pas être
	 * modifié.
	 * 
	 * @see PlayerColor#getPlayerColor()
	 */
	private String value;

	/**
	 * Constructeur renseignant le code de la couleur.
	 * 
	 * @param value Le code de la couleur.
	 */
	private PlayerColor(String value) {
		this.value = value;
	}

	/**
	 * Retourne le code hexadécimal de la couleur.
	 * 
	 * @return Le code hexadécimal.
	 */
	public String getPlayerColor() {
		return this.value;
	}
}
