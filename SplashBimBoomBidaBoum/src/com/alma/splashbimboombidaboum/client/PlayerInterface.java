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
    void setPosX(int val) throws RemoteException;
    int getPosX() throws RemoteException;
    void setPosY(int val) throws RemoteException;
    int getPosY() throws RemoteException;
    void setObstacle(Obstacle o) throws RemoteException;
    void setCharacters(HashMap<String,Character> map) throws RemoteException;
    void setEnnemies(ArrayList<String> players) throws RemoteException;
}
