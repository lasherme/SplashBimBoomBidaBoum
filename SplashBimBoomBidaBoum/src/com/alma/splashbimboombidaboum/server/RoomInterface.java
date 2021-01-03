package com.alma.splashbimboombidaboum.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.alma.splashbimboombidaboum.client.PlayerInterface;

/**
 * 
 * 
 * @author Éloi Filaudeau, Louis Boursier, Nicolas Hawa, Loïc Lasherme
 * @version 1.0
 */
public interface RoomInterface extends Remote {
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public String getId() throws RemoteException;
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public int getMaxPlayer() throws RemoteException;
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public int getQueue() throws RemoteException;
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public boolean getInGame() throws RemoteException;
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<PlayerInterface> getPlayers() throws RemoteException;

	/**
	 * 
	 * @param player
	 * @throws RemoteException
	 */
	public void removePlayer(PlayerInterface player) throws RemoteException;
	
	/**
	 * 
	 * @param player
	 * @return
	 * @throws RemoteException
	 */
	public RoomInterface roomConnection(PlayerInterface player) throws RemoteException;
	
	/**
	 * 
	 * @param player
	 * @param ready
	 * @throws RemoteException
	 */
	public void changeState(PlayerInterface player, boolean ready) throws RemoteException;
}
