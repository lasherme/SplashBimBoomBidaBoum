package com.alma.splashbimboombidaboum.client;

import com.alma.splashbimboombidaboum.utility.RandomString;

import java.util.Random;

public class Game implements Runnable {
    Player myself;
    String currentString = null;
    public Game(Player p ){
        myself = p;
    }
    @Override
    public void run() {
        while(!myself.gameEnds){
            try {
                if(!myself.getDeathStatus()) {
                    currentString = myself.getName() + " : "+RandomString.generate(new Random().nextInt(5));
                    myself.setCurrentString(currentString);
                    Thread.sleep(2000);
                }
            }catch(Exception e){}
        }
        for(String s: myself.leaderboard){
            System.out.println(s);
        }
    }
}
