package com.alma.splashbimboombidaboum.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alma.splashbimboombidaboum.client.PlayerInterface;

public class Room extends UnicastRemoteObject implements RoomInterface{
	private String id;
	private int maxPlayer;
	private int queue=0;
	private ArrayList<PlayerInterface> players = new ArrayList<>();
	private Map<PlayerInterface,Boolean> playerReadyStatus = new HashMap<>();
	Game runningGame;
	public Room(String id, int maxPlayer) throws RemoteException {
		this.id = id;
		this.maxPlayer = maxPlayer;
	}
	
	public String getId() throws RemoteException {
		return this.id;
	}
	
	public int getMaxPlayer() throws RemoteException{
		return this.maxPlayer;
	}

	public int getQueue() throws RemoteException {
		return this.queue;
	}

	public int getSize() throws RemoteException {
		return this.players.size();
	}
	
	public void setMaxPlayer(int maxPlayer) {
		if(maxPlayer >= 4) {
			this.maxPlayer = maxPlayer;
		}
	}

	public synchronized RoomInterface roomConnection(PlayerInterface player) throws RemoteException, MalformedURLException, NotBoundException {
		RoomInterface currentRoom = null;

		if (this.players.size() < maxPlayer) {
			this.players.add((PlayerInterface) Naming.lookup(player.getMyAddress()));
			this.playerReadyStatus.put(player,false);
			currentRoom = this;
		}

		return currentRoom;
	}



	public void isReady(PlayerInterface player) throws RemoteException {
		playerReadyStatus.put(player,!playerReadyStatus.get(player));
		System.out.println(player.getName()+ "is ready");
		if(readyCheck()){
			ArrayList<String> playersInGame = new ArrayList<>();
			for(PlayerInterface p : playerReadyStatus.keySet()){
				playersInGame.add(p.getName());
			}
			for(PlayerInterface p : playerReadyStatus.keySet()) {
				p.setEnnemies(playersInGame);
			}
				// THE GAME BEGINS!
			System.out.println("All players are ready");
			runningGame = new Game(players,this);
			new Thread(runningGame).start();
		}
	}

	private boolean readyCheck() throws RemoteException {
		if(getSize() > 1) {
			for (PlayerInterface p : playerReadyStatus.keySet()) {
				try {
					if (p.getIsAlive() &&!playerReadyStatus.get(p) ) { //p.getIsAlive() would return a value if the connection still exists. Otherwise we get an exception.
						return false;
					}
				} catch (Exception e) {
					playerReadyStatus.remove(p);
					players.remove(p);
					return readyCheck();
				}
			}
			return true;
		}
		return false;
	}
	public void gameOver(List<String> leaderboard) throws RemoteException {
		for(String s : leaderboard){
			System.out.println(s);
		}
		for(PlayerInterface p : players){
			p.setLeaderboard(leaderboard);
			p.setGameEnds(true);
		}
		runningGame = null;
	}

}
