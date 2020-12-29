package com.alma.splashbimboombidaboum.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Vector;

import com.alma.splashbimboombidaboum.client.Player;
import com.alma.splashbimboombidaboum.client.PlayerInterface;
import com.alma.splashbimboombidaboum.utility.Address;
import com.alma.splashbimboombidaboum.utility.Direction;
import com.alma.splashbimboombidaboum.utility.MathVector;
import com.alma.splashbimboombidaboum.utility.MathVectorInterface;
import com.alma.splashbimboombidaboum.utility.PlayerColor;

import javafx.scene.paint.Color;

public class Room extends UnicastRemoteObject implements RoomInterface, Address {
	private String id;
	private int maxPlayer;
	private int queue = 0;
	private ArrayList<PlayerInterface> players = new ArrayList<PlayerInterface>();
	/** true when players can connect to this Room, false otherwise */
	private boolean isOpen = false;
	private ArrayList<PlayerColor> colors = new ArrayList<PlayerColor>();
	private ArrayList<PlayerColor> colorsRemove = new ArrayList<PlayerColor>();

	public Room(String id, int maxPlayer) throws RemoteException {
		this.id = id;
		this.maxPlayer = maxPlayer;
		this.isOpen = true;

		for (PlayerColor color : PlayerColor.values()) {
			colors.add(color);
		}

		System.out.println("Room ID : " + this.id);
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

	public int getSize() throws RemoteException {
		return this.players.size();
	}

	public boolean getIsOpen() throws RemoteException {
		return this.isOpen;
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
			// Create unique name in the room
			while (!checkName(player.getName())) {
				player.setName(name + i++);
			}

			// Give random color for the player
			int randomColorIndex = (int) (Math.random() * colors.size());
			PlayerColor randomColor = colors.get(randomColorIndex);
			player.setColor(randomColor.getPlayerColor());
			colors.remove(randomColor);
			colorsRemove.add(randomColor);

			// Update players' waiting room with new player
			for (PlayerInterface currentPlayer : players) {
				currentPlayer.getLocalPlayers().addPlayer(player);
				player.getLocalPlayers().addPlayer(currentPlayer);
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

		if (nbReady == this.getSize()) {
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
		float position = 0;
		float size = 30;
		float spacing = 20;

		for (PlayerInterface player : players) {
			player.setColor(player.getColor());
			player.getCoordinates().setSize(size);
			player.getCoordinates().getPositionVector().setVector(position, 0);
			player.getCoordinates().getDirectionVector().setVector(0, 0);

			position += size + spacing;
		}
	}

	private void game() throws RemoteException {
		float speed = 5;
		MathVectorInterface gravityVector = new MathVector(0, (float) -9.81);

		new Thread(() -> {

			for (PlayerInterface player : players) {
				new Thread(() -> {
					while (true) {
						MathVectorInterface vectorBuffer;
						try {
							vectorBuffer = new MathVector();
							MathVectorInterface directionVector = new MathVector();
							MathVectorInterface playerDirectionVector = player.getCoordinates().getDirectionVector();
							MathVectorInterface playerPositionVector = player.getCoordinates().getPositionVector();

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
										directionVector = directionVector.sumVector(new MathVector(0, 50));
									}
									break;
								default:
									break;
								}
							}

							vectorBuffer = directionVector.timeFloatVector(speed);
							vectorBuffer = vectorBuffer.sumVector(gravityVector);
							vectorBuffer = vectorBuffer.averageVector(playerDirectionVector);
							playerDirectionVector.setVector(vectorBuffer.getX(), vectorBuffer.getY());

							vectorBuffer = vectorBuffer.sumVector(playerPositionVector);
							playerPositionVector.setVector(vectorBuffer.getX(), Math.max(0, vectorBuffer.getY()));
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}

						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
			while (true) {
				for (PlayerInterface player : players) {
					try {

						for (PlayerInterface localPlayer : players) {
							if (localPlayer != player) {
								localPlayer.getLocalPlayers().changeCoordinatesPlayer(player,
										player.getCoordinates().getPositionVector().getX(),
										player.getCoordinates().getPositionVector().getY());
							}
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
