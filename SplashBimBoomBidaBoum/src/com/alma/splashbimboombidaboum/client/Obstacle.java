package com.alma.splashbimboombidaboum.client;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import java.util.ArrayList;
import java.util.Random;

public class Obstacle extends Rectangle{ //Refactor with Design patter : Composite??

    public Obstacle(){
        Rectangle component = new Rectangle();
        component.setFill(Color.BLACK);
        component.setWidth(30);
        component.setHeight(10);
        component.setTranslateX(800);
        component.setTranslateY(new Random().nextInt((int)744));
    }
    /*public Obstacle(Group componentsGroup,double sceneHeight, double sceneWidth){
        Rectangle component = new Rectangle();
        component.setFill(Color.BLACK);
        component.setWidth(30);
        component.setHeight(10);
        component.setTranslateX(sceneWidth);
        component.setTranslateY(new Random().nextInt((int)sceneHeight));
        componentsGroup.getChildren().add(component);
        obstacles.add(0,component);

    }

     */
    /*
    public static void setxCoord() {
        for (int i = 0;i<obstacles.size();i++) {
            if (obstacles.get(i).getTranslateX() < -30) {
                obstacles.remove(obstacles.get(i));

            } else {
                obstacles.get(i).setTranslateX(obstacles.get(i).getTranslateX() - 5);
            }
        }
    }

     */


}
