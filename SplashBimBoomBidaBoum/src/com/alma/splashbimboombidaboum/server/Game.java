package com.alma.splashbimboombidaboum.server;

import com.alma.splashbimboombidaboum.client.Obstacle;
import com.alma.splashbimboombidaboum.client.PlayerInterface;
import com.alma.splashbimboombidaboum.client.Character;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

import java.rmi.RemoteException;
import java.util.*;

public class Game implements Runnable {
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private ArrayList<String> leaderboard = new ArrayList<>();
    private List<PlayerInterface> inGamePlayers = new ArrayList<>(); //people in game
    private List<PlayerInterface> alive = new ArrayList<>(); // people still alive
    private Map<PlayerInterface,String> input = new HashMap<>();
    private HashMap<String,Character> characterMap = new HashMap<>();
    private Room myroom;
    private int speed = 5;
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
                for (PlayerInterface p : inGamePlayers) {
                    try {
                        p.isDead(false);
                        p.setGameEnds(false);
                        characterMap.put(p.getName(),new Character());
                        p.gameStarts();
                    } catch (Exception e) {
                        System.out.println(e.toString());
                        inGamePlayers.remove(p);
                    }
                }
                for (PlayerInterface p : inGamePlayers) { //Sending models to players
                    try {
                        characterMap.get(p.getName()).setIsFriendly();
                        p.setCharacters(characterMap);
                        characterMap.get(p.getName()).setIsFriendly();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            while (alive.size() > 1) {
                    try {
                        checkColision();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    setxCoord();
                    //Creation des obstacles
                    if (new Random().nextInt(100) * 100 < 3 && obstacles.size() < 5) {
                        obstacles.add(new Obstacle());
                    }
                    //Gestion de la partie
                    for (int i = 0; i < alive.size(); i++) {
                        PlayerInterface p = alive.get(i);
                        try {
                            KeyEvent event = p.getCurrentAction(); //Set timeout????
                            if (event != null) {
                                switch (event.getCode()) {
                                    case D:
                                    case RIGHT:
                                        characterMap.get(p).moveRight();
                                        break;
                                    case Q:
                                    case LEFT:
                                        characterMap.get(p).moveLeft();
                                        break;
                                    case UP:
                                    case Z:
                                        characterMap.get(p).moveUp();
                                        break;
                                    case DOWN:
                                    case S:
                                        characterMap.get(p).moveDown();
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e.toString());
                            inGamePlayers.remove(p);
                            alive.remove(p);
                        }
                    }
                    for (PlayerInterface p : inGamePlayers) {
                        try { //Update player pos;
                            characterMap.get(p.getName()).setIsFriendly();
                            p.setCharacters(characterMap);
                            System.out.println(characterMap.keySet().size());
                            characterMap.get(p.getName()).setIsFriendly();
                        }catch(Exception e){}
                        for (Obstacle o : obstacles) {
                            try {
                                p.setObstacle(o);
                            } catch (Exception e) {

                            }
                        }
                    }
                }
                System.out.println("Fin de partie");
                try {
                    leaderboard.add(alive.get(0).getName());
                } catch (
                        RemoteException e) {
                    e.printStackTrace();
                }
                try {
                    myroom.gameOver(leaderboard);
                } catch (
                        RemoteException e) {
                    e.printStackTrace();
                }




        /*
        TODO:
        INFORMER LES CLIENTS ET LEURS DONNER LE CLASSEMENT
         */

    }
    private void checkColision() throws RemoteException {
        Rectangle compare =new Rectangle();
        compare.setArcHeight(15);
        compare.setArcWidth(15);
        compare.setWidth(60);
        compare.setHeight(60);
        for(PlayerInterface player : alive) {
            compare.setTranslateX(player.getPosX());
            compare.setTranslateY(player.getPosY());
            for (Rectangle obstacle : obstacles) {
                if (obstacle.getBoundsInParent().intersects(compare.getBoundsInParent())){
                    try {
                        player.isDead(true);
                        leaderboard.add(player.getName());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public void setxCoord(){
        for (int i = 0;i< obstacles.size();i++) {
            if (obstacles.get(i).getTranslateX() < -30) {
                obstacles.remove(obstacles.get(i));
            } else {
                obstacles.get(i).setTranslateX(obstacles.get(i).getTranslateX() - 5);
            }
        }
    }
}
