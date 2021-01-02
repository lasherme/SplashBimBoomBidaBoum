package com.alma.splashbimboombidaboum.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import com.alma.splashbimboombidaboum.client.PlayerInterface;
import com.alma.splashbimboombidaboum.utility.Address;
import com.alma.splashbimboombidaboum.utility.Direction;
import com.alma.splashbimboombidaboum.utility.MathVector;
import com.alma.splashbimboombidaboum.utility.MathVectorInterface;
import com.alma.splashbimboombidaboum.utility.Obstacle;
import com.alma.splashbimboombidaboum.utility.ObstacleInterface;
import com.alma.splashbimboombidaboum.utility.PlayerColor;
import com.alma.splashbimboombidaboum.utility.WindowSize;
import com.sun.media.jfxmediaimpl.platform.osx.OSXPlatform;

public class Room extends UnicastRemoteObject implements RoomInterface, Address {
	private String id;
	private int maxPlayer;
	private int queue = 0;
	private RoomReservationInterface roomReservation;
	private ArrayList<PlayerInterface> players = new ArrayList<PlayerInterface>();
	/** true when players can connect to this Room, false otherwise */
	private boolean isOpen = false;
	private boolean inGame = false;
	private ArrayList<PlayerColor> colors = new ArrayList<PlayerColor>();
	private ArrayList<PlayerColor> colorsRemove = new ArrayList<PlayerColor>();
	private CopyOnWriteArrayList<ObstacleInterface> obstacles = new CopyOnWriteArrayList<ObstacleInterface>();
	private ArrayList<PlayerInterface> deads = new ArrayList<PlayerInterface>();

	public Room(String id, int maxPlayer, RoomReservationInterface roomReservation) throws RemoteException {
		this.id = id;
		this.maxPlayer = maxPlayer;
		this.roomReservation = roomReservation;
		this.isOpen = true;

		for (PlayerColor color : PlayerColor.values()) {
			colors.add(color);
		}

		System.out.println("Room ID : " + this.id);
	}

	public String getId() throws RemoteException {
		return this.id;
	}

	public boolean getInGame() throws RemoteException {
		return this.inGame;
	}

	public int getMaxPlayer() throws RemoteException {
		return this.maxPlayer;
	}

	public int getQueue() throws RemoteException {
		return this.queue;
	}

	public int getSize() throws RemoteException {
		return this.players.size();
	}

	public boolean getIsOpen() throws RemoteException {
		return this.isOpen;
	}

	public ArrayList<PlayerInterface> getPlayers() throws RemoteException {
		return this.players;
	}

	public void setMaxPlayer(int maxPlayer) {

		if (maxPlayer >= 4) {
			this.maxPlayer = maxPlayer;
		}
	}

	public void removePlayer(PlayerInterface player) throws RemoteException {

		if (this.players.contains(player)) {
			this.players.remove(player);

			for (PlayerInterface currentPlayer : players) {
				currentPlayer.getLocalPlayers().removePlayer(player);
			}

			PlayerColor colorBuffer = null;
			for (PlayerColor color : colorsRemove) {
				if (color.getPlayerColor().equals(player.getColor())) {
					colorBuffer = color;
					break;
				}
			}
			colorsRemove.remove(colorBuffer);
			colors.add(colorBuffer);
			
			if(players.size() <= 0) {
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

	/**
	 * Connect player to this room.
	 * <p>
	 * Adds player into this players list and give it a color.
	 * 
	 * @param player the player parameter, not null
	 * @return this if it is not full, null otherwise
	 * @throws RemoteException
	 */
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
			int randomColorIndex = (int) (Math.random() * colors.size());
			PlayerColor randomColor = colors.get(randomColorIndex);
			player.setColor(randomColor.getPlayerColor());
			colors.remove(randomColor);
			colorsRemove.add(randomColor);

			// Update players' waiting room with new player.
			for (PlayerInterface currentPlayer : players) {
				currentPlayer.getLocalPlayers().addPlayer(player);
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
				currentPlayer.getLocalPlayers().setState(player, ready);
			}
			if (currentPlayer.getState()) {
				nbReady++;
			}
		}

		if (nbReady == this.getSize() && nbReady > 1) {
			isOpen = false;
			this.startGame();
		}
	}

	private void startGame() throws RemoteException {
		this.initialization();
		for (PlayerInterface player : players) {
			player.getLocalPlayers().setGameStart(true);
		}
		this.game();
	}

	private void initialization() throws RemoteException {
		inGame = true;
		float position = 0;
		float spacing = 20;

		for (PlayerInterface player : players) {
			player.setColor(player.getColor());
			player.getCoordinates().setSize(WindowSize.size);
			player.getCoordinates().getPositionVector().setVector(position, 0);
			player.getCoordinates().getDirectionVector().setVector(0, 0);

			position += WindowSize.size + spacing;
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
							MathVectorInterface playerDirectionVector = player.getCoordinates().getDirectionVector();
							MathVectorInterface playerPositionVector = player.getCoordinates().getPositionVector();
							float x;
							float y;

							for (Direction direction : player.getCoordinates().getDirection()) {
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

							vectorBuffer = directionVector.timeFloatVector(WindowSize.speed);
							vectorBuffer = vectorBuffer.sumVector(gravityVector);
							vectorBuffer = vectorBuffer.averageVector(playerDirectionVector);
							playerDirectionVector.setVector(vectorBuffer.getX(), vectorBuffer.getY());

							vectorBuffer = vectorBuffer.sumVector(playerPositionVector);
							x = Math.min(vectorBuffer.getX() + WindowSize.size, WindowSize.width) - WindowSize.size;
							y = Math.min(vectorBuffer.getY() + WindowSize.size, WindowSize.height) - WindowSize.size;

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
									localPlayer.getLocalPlayers().changeCoordinatesPlayer(player,
											player.getCoordinates().getPositionVector().getX(),
											player.getCoordinates().getPositionVector().getY());
								}

							}

							for (ObstacleInterface obstacle : obstacles) {
								if (obstacle.collision(player)) {
									deads.add(player);
									for (PlayerInterface localPlayer : players) {
										localPlayer.getLocalPlayers().addDead(player);
									}

									if (deads.size() >= players.size() - 1) {
										for(PlayerInterface currentPlayer : players) {
											if(!deads.contains(currentPlayer)) {
												for(PlayerInterface p : players) {
													p.getLocalPlayers().addDead(currentPlayer);
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
					ObstacleInterface obstacle = new Obstacle(20, 20, i++, new MathVector(WindowSize.width, 0),
							new MathVector(-1, 0));
					obstacles.add(obstacle);
					for (PlayerInterface player : players) {
						player.getLocalPlayers().addObstacle(obstacle);
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
				for (ObstacleInterface obstacle : obstacles) {
					try {
						if (obstacle.getPosition().getX() + obstacle.getWidth() < 0) {
							for (PlayerInterface player : players) {
								player.getLocalPlayers().removeObstacle(obstacle);
							}
						} else {
							vecteur = obstacle.getPosition().sumVector(obstacle.getDirection().timeFloatVector(speed));
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
		this.isOpen = true;
		this.inGame = false;

		obstacles = new CopyOnWriteArrayList<ObstacleInterface>();
		deads = new ArrayList<PlayerInterface>();
	}
}
