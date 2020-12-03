package com.alma.splashbimboombidaboum.client;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Character implements Serializable {
    Rectangle mainChar;
    boolean isAlive;
    int speed;
    private boolean ennemy = true;

    public Character(){
        mainChar = new Rectangle();
        mainChar.setArcHeight(15);
        mainChar.setArcWidth(15);
        mainChar.setWidth(60);
        mainChar.setHeight(60);
        if(ennemy){
            mainChar.setFill(Color.RED);
        }
        else{
            mainChar.setFill(Color.BLUE);
        }
        mainChar.setTranslateY(614); //744 - 130
        isAlive = true;
        speed = 5;
    }
    public void moveUp(){
        mainChar.setTranslateY(mainChar.getTranslateY() - 20);
    }
    public void moveDown(){
        mainChar.setTranslateY(mainChar.getTranslateY() + 20);
    }
    public void moveRight(){
        mainChar.setTranslateX(mainChar.getTranslateX() + speed);
    }
    public void moveLeft(){
        mainChar.setTranslateX(mainChar.getTranslateX() - speed);
    }
    public void setTranslateX(double val){ mainChar.setTranslateX(val);}
    public void setTranslateY(double val){ mainChar.setTranslateY(val);}

    public boolean isAlive(){
        return isAlive;
    }
    public void kill(){
        isAlive = false;
    }

    public double getMaxY(){
        return mainChar.getBoundsInParent().getMaxY();
    }
    public double getMinY(){
        return mainChar.getBoundsInParent().getMinY();
    }

    public double getMaxX(){
        return mainChar.getBoundsInParent().getMaxX();
    }
    public double getMinX(){
        return mainChar.getBoundsInParent().getMinX();
    }
    public void setIsFriendly(){
        ennemy = !ennemy;
    }
    public void addToComponentGroup(Group componentGroup){
        componentGroup.getChildren().add(mainChar);
    }
    public void removeFromComponentGroup(Group g){
        g.getChildren().remove(mainChar);
    }

}
