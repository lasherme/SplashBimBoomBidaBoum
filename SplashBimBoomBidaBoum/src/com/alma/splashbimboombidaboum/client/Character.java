package com.alma.splashbimboombidaboum.client;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Character implements Serializable {
    Rectangle mainChar;
    boolean isAlive;
    int speed;


    public Character(){
        mainChar = new Rectangle();
        mainChar.setArcHeight(15);
        mainChar.setArcWidth(15);
        mainChar.setWidth(60);
        mainChar.setHeight(60);

       mainChar.setFill(Color.RED);


        mainChar.setTranslateY(614); //744 - 130
        isAlive = true;
        speed = 5;
    }
    public void moveUp(){
        mainChar.setTranslateY(mainChar.getTranslateY() - 10);
    }
    public void moveDown(){
        mainChar.setTranslateY(mainChar.getTranslateY() + 10);
    }
    public void moveRight(){
        mainChar.setTranslateX(mainChar.getTranslateX() + 10);
    }
    public void moveLeft(){
        mainChar.setTranslateX(mainChar.getTranslateX() - 10);
    }
    public void setTranslateX(double val){ mainChar.setTranslateX(val);}
    public void setTranslateY(double val){ mainChar.setTranslateY(val);}

    public boolean isAlive(){
        return isAlive;
    }
    public void kill(){
        isAlive = false;
    }

    public double getTranslateX(){
        return mainChar.getTranslateX();
    }

    public double getTranslateY(){
        return mainChar.getTranslateY();
    }
    public void setIsFriendly(){
        mainChar.setFill(Color.BLUE);
    }
    public void addToComponentGroup(Group componentGroup){
        componentGroup.getChildren().add(mainChar);
    }
    public void removeFromComponentGroup(Group g){
        g.getChildren().remove(mainChar);
    }

}
