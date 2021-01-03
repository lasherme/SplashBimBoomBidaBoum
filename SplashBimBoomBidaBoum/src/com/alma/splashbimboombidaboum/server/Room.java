package com.alma.splashbimboombidaboum.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.alma.splashbimboombidaboum.client.PlayerInterface;
import com.alma.splashbimboombidaboum.utility.Address;
import com.alma.splashbimboombidaboum.utility.Direction;
import com.alma.splashbimboombidaboum.utility.InGameValues;
import com.alma.splashbimboombidaboum.utility.MathVector;
import com.alma.splashbimboombidaboum.utility.MathVectorInterface;
import com.alma.splashbimboombidaboum.utility.PlayerColor;
import com.alma.splashbimboombidaboum.utility.WindowSize;

/**
 * <b>Classe représentant le lobby.</b>
 * <p>
 * Elle peut être aussi utilisée comme objet distant.
 * </p>
 * <p>
 * Voir {@link RoomInterface} pour plus de renseignements.
 * </p>
 * 
 * @see Address
 * @see InGameValues
 * @see WindowSize
 * @author Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme
 * @version 1.0
 */
public class Room extends UnicastRemoteObject implements RoomInterface, Address, InGameValues, WindowSize {
	/**
	 * L'identifiant du lobby. L'attribut "id" ne peut pas être modifié.
	 * 
	 * @see Room#getId()
	 */
	private final String id;

	/**
	 * Le nombre de joueur max du lobby. L'attribut "maxPlayer" peut être modifié.
	 * 
	 * @see Room#getMaxPlayer()
	 * @see Room#setMaxPlayer(int)
	 */
	private int maxPlayer;

	/**
	 * Le nombre de joueur en attente de réservation du lobby. L'attribut "queue" ne
	 * peut pas être modifié.
	 * 
	 * @see Room#getQueue()
	 */
	private int queue = 0;

	/**
	 * Le boolean permettant de savoir si la partie est en cours. L'attribut
	 * "inGame" ne peut pas être modifié.
	 * 
	 * @see Room#getInGame()
	 */
	private boolean inGame = false;

	/**
	 * La référence vers l'objet qui permet la réservation des lobbys. L'attribut
	 * "roomReservation" ne peut pas être modifié.
	 */
	private RoomReservationInterface roomReservation;

	/**
	 * La liste des joueurs présent dans le lobby. L'attribut "players" peut être
	 * modifié.
	 * 
	 * @see Room#getPlayers()
	 * @see Room#removePlayer(PlayerInterface)
	 * @see Room#roomConnectionBis(PlayerInterface)
	 * @see PlayerInterface
	 */
	private ArrayList<PlayerInterface> players = new ArrayList<PlayerInterface>();

	/**
	 * La liste des joueurs morts au cours de la partie. L'attribut "deads" ne peut
	 * pas être modifié.
	 * 
	 * @see PlayerInterface
	 */
	private ArrayList<PlayerInterface> deads = new ArrayList<PlayerInterface>();

	/**
	 * La liste "thread safe" des obstacles créés au cours de la partie. L'attribut
	 * "obstacles" ne peut pas être modifié.
	 * 
	 * @see ObstacleEntityInterface
	 */
	private CopyOnWriteArrayList<ObstacleEntityInterface> obstacles = new CopyOnWriteArrayList<ObstacleEntityInterface>();

	/**
	 * La liste des couleurs restantes du lobby. L'attribut "colorsRemain" ne peut
	 * pas être modifié.
	 *
	 * @see PlayerColor
	 */
	private ArrayList<PlayerColor> colorsRemain = new ArrayList<PlayerColor>();

	/**
	 * La liste des couleurs déjà utilisées par les autres joueurs de la partie.
	 * L'attribut "colorsRemove" ne peut pas être modifié.
	 * 
	 * @see PlayerColor
	 */
	private ArrayList<PlayerColor> colorsRemove = new ArrayList<PlayerColor>();

	/**
	 * Constructeur : Room
	 * 
	 * <p>
	 * À la construction d'un objet Room, les valeurs suivantes sont initialisées :
	 * <ul>
	 * <li>id</li>
	 * <li>maxPlayer</li>
	 * <li>roomReservation</li>
	 * <li>colorsRemain</li>
	 * </ul>
	 * </p>
	 * 
	 * @param id              L'identifiant unique du lobby.
	 * @param maxPlayer       Le nombre maximum de joueur dans le lobby.
	 * @param roomReservation La référence vers l'objet des réservations des lobbys.
	 * @throws RemoteException
	 * @see PlayerColor
	 * @see RoomReservationInterface
	 */
	public Room(String id, int maxPlayer, RoomReservationInterface roomReservation) throws RemoteException {
		this.id = id;
		this.maxPlayer = maxPlayer;
		this.roomReservation = roomReservation;

		for (PlayerColor color : PlayerColor.values()) {
			colorsRemain.add(color);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String getId() throws RemoteException {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getMaxPlayer() throws RemoteException {
		return this.maxPlayer;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getQueue() throws RemoteException {
		return this.queue;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean getInGame() throws RemoteException {
		return this.inGame;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<PlayerInterface> getPlayers() throws RemoteException {
		return this.players;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMaxPlayer(int maxPlayer) throws RemoteException {

		if (maxPlayer > 1 && this.players.size() > maxPlayer) {
			this.maxPlayer = maxPlayer;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void removePlayer(PlayerInterface player) throws RemoteException {
		PlayerColor colorBuffer = null;

		if (this.players.contains(player)) {
			this.players.remove(player);

			// On supprime le joueur de l'affichage des autres joueurs
			for (PlayerInterface currentPlayer : players) {
				currentPlayer.getObservablePlayers().removePlayer(player);
			}

			// On remet la couleur du joueur dans les couleurs restantes.
			for (PlayerColor color : colorsRemove) {
				if (color.getPlayerColor().equals(player.getColor())) {
					colorBuffer = color;
					break;
				}
			}
			colorsRemove.remove(colorBuffer);
			colorsRemain.add(colorBuffer);

			// S'il n'y a plus de joueur présent dans le lobby, nous le supprimons.
			if (players.size() <= 0) {
				this.roomReservation.removeRoom(this);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void changeState(PlayerInterface player, boolean ready) throws RemoteException {
		int nbReady = 0;

		for (PlayerInterface currentPlayer : players) {
			if (currentPlayer != player) {
				currentPlayer.getObservablePlayers().setState(player, ready);
			}
			if (currentPlayer.getState()) {
				nbReady++;
			}
		}

		// On lance la partie si tous les joueurs sont prêt et qu'il n'y a pas qu'un
		// seul joueur.
		if (nbReady == this.getPlayers().size() && nbReady > 1) {
			inGame = true;
			this.startGame();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public RoomInterface roomConnection(PlayerInterface player) throws RemoteException {
		RoomInterface currentRoom = null;

		queue++;
		currentRoom = roomConnectionBis(player);
		queue--;

		return currentRoom;
	}

	/**
	 * Retourne ce lobby s'il y a toujours de la place. Stocke le joueur passé en
	 * paramètre dans la liste des joueurs et lui attribut une couleur
	 * aléatoirement.
	 * 
	 * @param player Le joueur qui veut se connecter dans le lobby.
	 * @return Ce lobby s'il y a de la place, null sinon.
	 * @throws RemoteException
	 * @see Room#roomConnection(PlayerInterface)
	 * @see PlayerInterface
	 */
	private synchronized RoomInterface roomConnectionBis(PlayerInterface player) throws RemoteException {
		RoomInterface currentRoom = null;
		int i;
		String name = player.getName();

		if (this.players.size() < maxPlayer) {
			i = 1;
			// Crée un nom unique si deux joueurs on le même pseudo.
			while (!checkName(player.getName())) {
				player.setName(name + i++);
			}

			// Attribut une couleur aléatoire au joueur.
			int randomColorIndex = (int) (Math.random() * colorsRemain.size());
			PlayerColor randomColor = colorsRemain.get(randomColorIndex);
			player.setColor(randomColor.getPlayerColor());
			colorsRemain.remove(randomColor);
			colorsRemove.add(randomColor);

			// Ajoute le joueur passé en paramètre à la liste d'attente des autres joueurs
			// présent dans le lobby.
			for (PlayerInterface currentPlayer : players) {
				currentPlayer.getObservablePlayers().addPlayer(player);
			}
			this.players.add(player);
			currentRoom = this;
		}

		return currentRoom;
	}

	/**
	 * Permet de savoir si le pseudo passé en paramètre appartient déjà à un joueur
	 * présent dans le lobby.
	 * 
	 * @param name Un pseudo.
	 * @return true si le pseudo n'existe pas, false sinon.
	 * @throws RemoteException
	 * @see PlayerInterface
	 */
	private boolean checkName(String name) throws RemoteException {
		for (PlayerInterface player : players) {
			if (player.getName().equals(name)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Permet d'initialiser le lobby afin de pouvoir lancer la partie ensuite.
	 * 
	 * @throws RemoteException
	 * @see Room#initialization()
	 * @see Room#game()
	 */
	private void startGame() throws RemoteException {
		this.initialization();
		for (PlayerInterface player : players) {
			player.getObservablePlayers().setGameStart(true);
		}
		this.playerDeplacementThread();
		this.generationObstacleThread();
		this.obstaclesDeplacementThread();
		this.game();
	}

	/**
	 * Initialise toutes les valeurs afin de bien commencer la partie.
	 * 
	 * @throws RemoteException
	 */
	private void initialization() throws RemoteException {
		float position = 0;

		for (PlayerInterface player : players) {
			player.setColor(player.getColor());
			player.getPlayerEntity().setSize(SIZE);
			player.getPlayerEntity().getPosition().setVector(position, 0);
			player.getPlayerEntity().getDirection().setVector(0, 0);

			position += SIZE + SPACING;
		}
	}

	/**
	 * Permet de créer un thread qui gère le déplacement des joueurs dans la partie.
	 * 
	 * @throws RemoteException
	 * @see MathVectorInterface
	 * @see PlayerInterface
	 */
	private void playerDeplacementThread() throws RemoteException {
		MathVectorInterface gravityVector = new MathVector(GRAVITY_X, GRAVITY_Y);

		for (PlayerInterface player : players) {
			new Thread(() -> {
				while (inGame && !deads.contains(player)) {
					MathVectorInterface vectorBuffer;
					try {
						vectorBuffer = new MathVector();
						MathVectorInterface directionVector = new MathVector();
						MathVectorInterface playerDirectionVector = player.getPlayerEntity().getDirection();
						MathVectorInterface playerPositionVector = player.getPlayerEntity().getPosition();
						float x;
						float y;

						// Regarde dans quel direction se déplace le joueur.
						for (Direction direction : player.getPlayerEntity().getKeyDirection()) {
							switch (direction) {
							case RIGHT:
								directionVector = directionVector.sumVector(new MathVector(1, 0));
								break;
							case LEFT:
								directionVector = directionVector.sumVector(new MathVector(-1, 0));
								break;
							case DOWN:
								directionVector = directionVector.sumVector(new MathVector(0, -1));
								break;
							case UP:
								if (playerPositionVector.getY() <= 0) {
									directionVector = directionVector.sumVector(new MathVector(0, 30));
								}
								break;
							default:
								break;
							}
						}

						// Met à jour le vecteur direction du joueur en local.
						vectorBuffer = directionVector.factorVector(PLAYER_SPEED);
						vectorBuffer = vectorBuffer.sumVector(gravityVector);
						vectorBuffer = vectorBuffer.averageVector(playerDirectionVector);
						playerDirectionVector.setVector(vectorBuffer.getX(), vectorBuffer.getY());

						// Met à jour le vecteur position du joueur en local.
						vectorBuffer = vectorBuffer.sumVector(playerPositionVector);
						x = Math.min(vectorBuffer.getX() + SIZE, WIDTH) - SIZE;
						y = Math.min(vectorBuffer.getY() + SIZE, HEIGHT) - SIZE;
						playerPositionVector.setVector(Math.max(0, x), Math.max(0, y));
					} catch (RemoteException e) {
						e.printStackTrace();
					}

					try {
						Thread.sleep(GAME_REFRESHEMENT);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	/**
	 * Permet de créer un thread qui gère la génération des obstacles de la partie.
	 * 
	 * @see MathVectorInterface
	 * @see ObstacleEntityInterface
	 * @see PlayerInterface
	 */
	private void generationObstacleThread() {
		new Thread(() -> {
			int i = 0;

			while (inGame) {
				try {
					ObstacleEntityInterface obstacle = new ObstacleEntity(20, 20, new MathVector(WIDTH, 0),
							new MathVector(-1, 0), i++);
					obstacles.add(obstacle);
					for (PlayerInterface player : players) {
						player.getObservablePlayers().addObstacle(obstacle);
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				try {
					Thread.sleep(OBSTACLE_REFRESHEMENT);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * Permet de créer un thread qui gère le déplacement des obstacles dans la
	 * partie.
	 * 
	 * @see MathVectorInterface
	 * @see ObstacleEntityInterface
	 */
	private void obstaclesDeplacementThread() {
		new Thread(() -> {
			MathVectorInterface vecteur;

			while (inGame) {
				for (ObstacleEntityInterface obstacle : obstacles) {
					try {
						if (obstacle.getPosition().getX() + obstacle.getWidth() < 0) {
							for (PlayerInterface player : players) {
								player.getObservablePlayers().removeObstacle(obstacle);
							}
						} else {
							vecteur = obstacle.getPosition()
									.sumVector(obstacle.getDirection().factorVector(OBSTACLE_SPEED));
							obstacle.getPosition().setVector(vecteur.getX(), vecteur.getY());
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}

				}
				try {
					Thread.sleep(GAME_REFRESHEMENT);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * La méthode principale qui permet de faire tourner la partie comme il faut.
	 * 
	 * @throws RemoteException
	 */
	private void game() throws RemoteException {

		new Thread(() -> {

			while (inGame) {
				for (PlayerInterface player : players) {
					if (!deads.contains(player)) {
						try {

							for (PlayerInterface localPlayer : players) {
								if (localPlayer != player) {
									localPlayer.getObservablePlayers().changeCoordinatesPlayer(player,
											player.getPlayerEntity().getPosition().getX(),
											player.getPlayerEntity().getPosition().getY());
								}

							}

							for (ObstacleEntityInterface obstacle : obstacles) {
								if (obstacle.collision(player)) {
									deads.add(player);
									for (PlayerInterface localPlayer : players) {
										localPlayer.getObservablePlayers().addDead(player);
									}

									if (deads.size() >= players.size() - 1) {
										for (PlayerInterface currentPlayer : players) {
											if (!deads.contains(currentPlayer)) {
												for (PlayerInterface p : players) {
													p.getObservablePlayers().addDead(currentPlayer);
												}
												break;
											}
										}
										inGame = false;
									}
								}
							}

						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
				}

				try {
					Thread.sleep(GAME_REFRESHEMENT);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			this.refresh();
		}).start();
	}

	/**
	 * Permet de réinitialiser le lobby afin de pouvoir relancer une partie derrière.
	 */
	private void refresh() {
		this.inGame = false;

		obstacles = new CopyOnWriteArrayList<ObstacleEntityInterface>();
		deads = new ArrayList<PlayerInterface>();
	}
}
