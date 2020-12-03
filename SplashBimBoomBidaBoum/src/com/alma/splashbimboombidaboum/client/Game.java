package com.alma.splashbimboombidaboum.client;

import com.alma.splashbimboombidaboum.utility.RandomString;

import java.rmi.RemoteException;
import java.util.Random;

public class Game implements Runnable {
    @Override
    public void run() {

    }
    /*
    Player myself;
    String currentString = null;
    public Game(Player p ){
        myself = p;
    }
    @Override
    public void run() {
        try {
            myself.setIsReady();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        while(!myself.gameEnds){
            try {
                if(!myself.getDeathStatus()) {
                    currentString = myself.getName() + " : "+RandomString.generate(new Random().nextInt(5));
                    myself.setCurrentString(currentString);
                }
                Thread.sleep(2000);
            }catch(Exception e){}
        }
        System.out.println("ici");
        for(String s: myself.leaderboard){
            System.out.println(s);
        }
    }*/
}
