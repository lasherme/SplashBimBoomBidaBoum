package com.alma.splashbimboombidaboum.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PlayerInterface extends Remote{
    String getMyAddress()  throws RemoteException;
    boolean getIsAlive() throws RemoteException;
    void gameStarts() throws RemoteException;
    String getCurrentAction() throws RemoteException;
    String getName() throws RemoteException;
    void isDead(boolean deathStatus) throws RemoteException;
    void setLeaderboard(List<String> leaderboard) throws RemoteException;
    void setGameEnds(boolean status) throws RemoteException;
}
