package com.alma.splashbimboombidaboum.client;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Random;

import com.alma.splashbimboombidaboum.server.RoomInterface;
import com.alma.splashbimboombidaboum.server.RoomReservationInterface;
import com.alma.splashbimboombidaboum.utility.Address;
import com.alma.splashbimboombidaboum.utility.RandomString;

public class Player extends UnicastRemoteObject implements PlayerInterface, Address {
	private String name;
	private RoomReservationInterface server;
	private RoomInterface room;
	private String myAddress = null;
	private String currentString = null;
	boolean deathStatus = false;
	List<String> leaderboard;
	boolean gameEnds = false;
	public Player(String name) throws MalformedURLException, RemoteException, NotBoundException, AlreadyBoundException {
		//Player Preparation
		this.name = name;
		Random r = new Random();
		int port = (2000+r.nextInt(8000));

		this.myAddress = "//" + PREADRRESS + ":" + port + "/" + RandomString.generate(25);
		LocateRegistry.createRegistry(port);
		Naming.bind(myAddress,this);

		//Player connection to server
		this.server = (RoomReservationInterface) Naming.lookup("//" + PREADRRESS + ":" + PORT + "/" + POSTADDRESS);
		System.out.println("Connection established with server : \n\t" + this.server.toString());
		this.room = server.getRoom(this);
		System.out.println("Room joined : \n\t" + this.room.getId());
		System.out.println("Number of players in the room :"+ this.room.getSize());
	}

	public void setIsReady() throws RemoteException{
		room.isReady(this);

	}
	public boolean getIsAlive(){
		return true;
	}
	public String getMyAddress() throws RemoteException{
		return this.myAddress;
	}
	public void gameStarts(){ //game loop
		System.out.println(name+ "started playing");
		Game g = new Game(this);
		new Thread(g).start();
	}
	public String getCurrentAction(){
		return this.currentString;
	}
	protected void setCurrentString(String s){
		this.currentString = s;
	}
	public String getName(){
		return this.name;
	}
	public void isDead(boolean deathStatus){
		this.deathStatus = deathStatus;
	}
	public boolean getDeathStatus(){
		return this.deathStatus;
	}
	public void setLeaderboard(List<String> leaderboard){
		this.leaderboard = leaderboard;
	}
	public void setGameEnds(boolean status){
		this.gameEnds = status;
	}
}
