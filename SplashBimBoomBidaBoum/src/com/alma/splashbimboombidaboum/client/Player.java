package com.alma.splashbimboombidaboum.client;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import com.alma.splashbimboombidaboum.server.RoomInterface;
import com.alma.splashbimboombidaboum.server.RoomReservationInterface;
import com.alma.splashbimboombidaboum.utility.Address;
import com.alma.splashbimboombidaboum.utility.RandomString;
import javafx.scene.input.KeyEvent;

public class Player extends UnicastRemoteObject implements PlayerInterface, Address {

	private String name;
	private RoomReservationInterface server;
	private RoomInterface room = null;
	private String myAddress = null;
	private KeyEvent currentEvent = null;
	boolean deathStatus = false;
	List<String> leaderboard;
	boolean gameEnds = false;
	protected Boolean gamestarted = false;
	Map<String,Obstacle> obstacles = new HashMap<>();
	List<String> ennemies = new ArrayList<>();
	GameLoop liveGame;
	Obstacle o;
	HashMap<String, Character> characterMap = new HashMap<>();


	public Player(String name) throws MalformedURLException, RemoteException, NotBoundException, AlreadyBoundException {
		//Player Preparation
		this.name = name;
		Random r = new Random();
		int port = (2000+r.nextInt(8000));
		this.myAddress = "rmi://" + "192.168.0.14" + ":" + port + "/" + PORT;
		LocateRegistry.createRegistry(port);
		Naming.bind(myAddress,this);
		//Player connection to server
		this.server = (RoomReservationInterface) Naming.lookup("rmi://" + PREADRRESS + ":" + PORT + "/" + POSTADDRESS);
		//System.out.println("Connection established with server : \n\t" + this.server.toString());
		server.getRoom(this);
		System.out.println(room.getId());
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
		this.gamestarted = true;
	}

	public KeyEvent getCurrentAction(){
		return this.currentEvent;
	}
	protected void setCurrentEvent(KeyEvent k){
		this.currentEvent = k;
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
		System.out.println("Staus received" + status);
		this.gameEnds = status;
	}

	@Override
	public double getPosX() throws RemoteException {
		return characterMap.get(this.name).getTranslateX();
	}

	@Override
	public double getPosY() throws RemoteException {
		return characterMap.get(this.name).getTranslateY();
	}

	@Override
	public void setCharacters(HashMap<String,Character> p) throws RemoteException {
		this.characterMap = p;
	}

	@Override
	public void setCharacters(String s, double posx, double posy) throws RemoteException {
		if(!characterMap.containsKey(s)){
			characterMap.put(s,new Character());
			if(s.equals(Main.player.getName())){
				characterMap.get(s).setIsFriendly();
			}
		}
		characterMap.get(s).setTranslateX(posx);
		characterMap.get(s).setTranslateY(posy);
	}

	@Override
	public void setEnnemies(ArrayList<String> players) throws RemoteException{
		this.ennemies = players;
	}

	@Override
	public void setObstacle(String id, double translateX, double translateY) throws RemoteException {
		if(!obstacles.containsKey(id)){
			obstacles.put(id,new Obstacle(id,translateX,translateY));
		}else{
			obstacles.get(id).setTranslateX(translateX);
		}
	}

	@Override
	public void setRoom(RoomInterface ri) {
		this.room = ri;
	}

	public Boolean getGamestarted(){
		return this.gamestarted;
	}


}
