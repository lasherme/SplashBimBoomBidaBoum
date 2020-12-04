package com.alma.splashbimboombidaboum.client;

import com.alma.splashbimboombidaboum.utility.RandomString;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Random;

public class Obstacle {
    Rectangle component;
    String id ;
    public Obstacle(){
        this.component = new Rectangle();
        component.setFill(Color.BLACK);
        component.setWidth(30);
        component.setHeight(10);
        component.setTranslateX(800);
        component.setTranslateY(new Random().nextInt((int)800));
        this.id = RandomString.generate(new Random().nextInt(5));
    }
    public Obstacle(String id, double x, double y){
        this.id = id;
        this.component = new Rectangle();
        component.setFill(Color.BLACK);
        component.setWidth(30);
        component.setHeight(10);
        component.setTranslateX(x);
        component.setTranslateY(y);
    }

    public void setTranslateX(double val){
        component.setTranslateX(val);
    }
    public Rectangle getComponent(){
        return this.component;
    }
    public double getTranslateX(){
        return this.component.getTranslateX();
    }
    public double getTranslateY(){
        return this.component.getTranslateY();
    }
    public String getId(){
        return this.id;
    }
}
