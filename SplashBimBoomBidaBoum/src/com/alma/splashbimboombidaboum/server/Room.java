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
import com.alma.splashbimboombidaboum.utility.ObstacleEntity;
import com.alma.splashbimboombidaboum.utility.ObstacleEntityInterface;
import com.alma.splashbimboombidaboum.utility.PlayerColor;
import com.alma.splashbimboombidaboum.utility.WindowSize;

/**
 * 
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
	
	public String getId() throws RemoteException {
		return this.id;
	}

	public int getMaxPlayer() throws RemoteException {
		return this.maxPlayer;
	}

	public int getQueue() throws RemoteException {
		return this.queue;
	}

	public boolean getInGame() throws RemoteException {
		return this.inGame;
	}

	public ArrayList<PlayerInterface> getPlayers() throws RemoteException {
		return this.players;
	}

	/*
	 * 
	 * 
	 * 
	 */

	public void setMaxPlayer(int maxPlayer) {

		if (maxPlayer > 1 && this.players.size() > maxPlayer) {
			this.maxPlayer = maxPlayer;
		}
	}

	public void removePlayer(PlayerInterface player) throws RemoteException {

		if (this.players.contains(player)) {
			this.players.remove(player);

			for (PlayerInterface currentPlayer : players) {
				currentPlayer.getObservablePlayers().removePlayer(player);
			}

			PlayerColor colorBuffer = null;
			for (PlayerColor color : colorsRemove) {
				if (color.getPlayerColor().equals(player.getColor())) {
					colorBuffer = color;
					break;
				}
			}
			colorsRemove.remove(colorBuffer);
			colorsRemain.add(colorBuffer);

			if (players.size() <= 0) {
				this.roomReservation.removeRoom(this);
			}
		}
	}

	public RoomInterface roomConnection(PlayerInterface player) throws RemoteException {
		RoomInterface currentRoom = null;

		queue++;
		currentRoom = roomConnectionBis(player);
		queue--;

		return currentRoom;
	}

	private synchronized RoomInterface roomConnectionBis(PlayerInterface player) throws RemoteException {
		RoomInterface currentRoom = null;
		int i;
		String name = player.getName();

		if (this.players.size() < maxPlayer) {
			i = 1;
			// Create unique name in the room if 2 player have the same.
			while (!checkName(player.getName())) {
				player.setName(name + i++);
			}

			// Give random color for the player.
			int randomColorIndex = (int) (Math.random() * colorsRemain.size());
			PlayerColor randomColor = colorsRemain.get(randomColorIndex);
			player.setColor(randomColor.getPlayerColor());
			colorsRemain.remove(randomColor);
			colorsRemove.add(randomColor);

			// Update players' waiting room with new player.
			for (PlayerInterface currentPlayer : players) {
				currentPlayer.getObservablePlayers().addPlayer(player);
			}
			this.players.add(player);
			currentRoom = this;
		}

		return currentRoom;
	}

	private boolean checkName(String name) throws RemoteException {
		for (PlayerInterface player : players) {
			if (player.getName().equals(name)) {
				return false;
			}
		}

		return true;
	}

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

		if (nbReady == this.getPlayers().size() && nbReady > 1) {
			inGame = true;
			this.startGame();
		}
	}

	private void startGame() throws RemoteException {
		this.initialization();
		for (PlayerInterface player : players) {
			player.getObservablePlayers().setGameStart(true);
		}
		this.game();
	}

	private void initialization() throws RemoteException {
		float position = 0;
		float spacing = 20;

		for (PlayerInterface player : players) {
			player.setColor(player.getColor());
			player.getCoordinates().setSize(SIZE);
			player.getCoordinates().getPosition().setVector(position, 0);
			player.getCoordinates().getDirection().setVector(0, 0);

			position += SIZE + spacing;
		}
	}

	private void game() throws RemoteException {
		MathVectorInterface gravityVector = new MathVector(0, -5);

		new Thread(() -> {

			for (PlayerInterface player : players) {
				new Thread(() -> {
					while (inGame && !deads.contains(player)) {
						MathVectorInterface vectorBuffer;
						try {
							vectorBuffer = new MathVector();
							MathVectorInterface directionVector = new MathVector();
							MathVectorInterface playerDirectionVector = player.getCoordinates().getDirection();
							MathVectorInterface playerPositionVector = player.getCoordinates().getPosition();
							float x;
							float y;

							for (Direction direction : player.getCoordinates().getKeyDirection()) {
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

							vectorBuffer = directionVector.factorVector(SPEED);
							vectorBuffer = vectorBuffer.sumVector(gravityVector);
							vectorBuffer = vectorBuffer.averageVector(playerDirectionVector);
							playerDirectionVector.setVector(vectorBuffer.getX(), vectorBuffer.getY());

							vectorBuffer = vectorBuffer.sumVector(playerPositionVector);
							x = Math.min(vectorBuffer.getX() + SIZE, WIDTH) - SIZE;
							y = Math.min(vectorBuffer.getY() + SIZE, HEIGHT) - SIZE;

							playerPositionVector.setVector(Math.max(0, x), Math.max(0, y));
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}

						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();

			}

			while (inGame) {
				for (PlayerInterface player : players) {
					if (!deads.contains(player)) {
						try {

							for (PlayerInterface localPlayer : players) {
								if (localPlayer != player) {
									localPlayer.getObservablePlayers().changeCoordinatesPlayer(player,
											player.getCoordinates().getPosition().getX(),
											player.getCoordinates().getPosition().getY());
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
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			this.refresh();
		}).start();

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
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}).start();

		new Thread(() -> {
			MathVectorInterface vecteur;
			float speed = 2;

			while (inGame) {
				for (ObstacleEntityInterface obstacle : obstacles) {
					try {
						if (obstacle.getPosition().getX() + obstacle.getWidth() < 0) {
							for (PlayerInterface player : players) {
								player.getObservablePlayers().removeObstacle(obstacle);
							}
						} else {
							vecteur = obstacle.getPosition().sumVector(obstacle.getDirection().factorVector(speed));
							obstacle.getPosition().setVector(vecteur.getX(), vecteur.getY());
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}

				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	private void refresh() {
		this.inGame = false;

		obstacles = new CopyOnWriteArrayList<ObstacleEntityInterface>();
		deads = new ArrayList<PlayerInterface>();
	}
}
