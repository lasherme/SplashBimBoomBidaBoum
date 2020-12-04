package com.alma.splashbimboombidaboum.client;

import javafx.scene.input.KeyEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface PlayerInterface extends Remote{
    String getMyAddress()  throws RemoteException;
    boolean getIsAlive() throws RemoteException;
    void gameStarts() throws RemoteException;
    KeyEvent getCurrentAction() throws RemoteException;
    String getName() throws RemoteException;
    void isDead(boolean deathStatus) throws RemoteException;
    void setLeaderboard(List<String> leaderboard) throws RemoteException;
    void setGameEnds(boolean status) throws RemoteException;
    double getPosX() throws RemoteException;
    double getPosY() throws RemoteException;
    void setCharacters(HashMap<String,Character> map) throws RemoteException; //marche pas, serialisation blablabla
    void setCharacters(String s , double posx,double posy) throws RemoteException;

    void setEnnemies(ArrayList<String> players) throws RemoteException;

    void setObstacle(String id, double translateX, double translateY)throws RemoteException;
}
