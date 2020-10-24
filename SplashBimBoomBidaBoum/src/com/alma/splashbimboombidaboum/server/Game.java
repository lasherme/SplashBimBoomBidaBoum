package com.alma.splashbimboombidaboum.server;

import com.alma.splashbimboombidaboum.client.PlayerInterface;

import java.rmi.RemoteException;
import java.util.*;

public class Game implements Runnable {
    private ArrayList<String> leaderboard = new ArrayList<>();
    private List<PlayerInterface> inGamePlayers = new ArrayList<>(); //people in game
    private List<PlayerInterface> alive = new ArrayList<>(); // people still alive
    private Map<PlayerInterface,String> input = new HashMap<>();
    private Room myroom;
    public Game(List<PlayerInterface> players, Room room) throws RemoteException {
        this.myroom = room;
        inGamePlayers = players;
        for(PlayerInterface p : players){
            input.put(p,"grien");
            alive.add(p);
        }
    }

    @Override
    public void run(){
        System.out.println("im running");
        for(PlayerInterface p : inGamePlayers){
            try{
                p.isDead(false);
                p.setGameEnds(false);
                p.gameStarts();
            }catch (Exception e){
                System.out.println(e.toString());
                inGamePlayers.remove(p);

            }
        }
        while(alive.size()>1){
            //Gestion de la partie

            for(int i = 0;i<alive.size();i++){
                PlayerInterface p = alive.get(i);
                try {
                    String s = p.getCurrentAction(); //Set timeout????
                    if (input.get(p) != s && s != null) {
                        input.put(p,s);
                        System.out.println(s);
                        //Calcul position
                        if(new Random().nextInt(100)>88){
                            leaderboard.add(p.getName());
                            alive.remove(p);
                            p.isDead(true);
                        }
                    }
                }catch(Exception e){
                    System.out.println(e.toString());
                    inGamePlayers.remove(p);
                    alive.remove(p);
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fin de partie");
        try {
            leaderboard.add(alive.get(0).getName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            myroom.gameOver(leaderboard);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        /*
        TODO:
        INFORMER LES CLIENTS ET LEURS DONNER LE CLASSEMENT
         */
    }
}
