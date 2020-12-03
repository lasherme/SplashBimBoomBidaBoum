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
	private RoomInterface room;
	private String myAddress = null;
	private KeyEvent currentEvent = null;
	boolean deathStatus = false;
	List<String> leaderboard;
	boolean gameEnds = false;
	protected Boolean gamestarted = false;
	List<Obstacle> obstacles = new ArrayList<>();
	List<String> ennemies = new ArrayList<>();

	int posX = 0; //Starts on the left
	int posY = 744-130; //Sceneheight-130
	GameLoop liveGame;
	Obstacle o;
	HashMap<String, Character> characterMap = new HashMap<>();
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
		//Game g = new Game(this);
		this.gamestarted = true;
		//new Thread(g).start();
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
	public void setPosX(int val)throws RemoteException {
		this.posX = val;
	}

	@Override
	public int getPosX() throws RemoteException {
		return this.posX;
	}

	@Override
	public void setPosY(int val)throws RemoteException {
		this.posY = val;
	}

	@Override
	public int getPosY() throws RemoteException {
		return this.posY;
	}

	@Override
	public void setObstacle(Obstacle o)throws RemoteException {
		this.obstacles.add(o);
	}

	@Override
	public void setCharacters(HashMap<String,Character> p) throws RemoteException {
		this.characterMap = p;
	}

	@Override
	public void setEnnemies(ArrayList<String> players) throws RemoteException{
		this.ennemies = players;
	}

	public Boolean getGamestarted(){
		return this.gamestarted;
	}
	public void setLiveGame(GameLoop g){
		this.liveGame = g;
	}
}
