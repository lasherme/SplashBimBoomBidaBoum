package com.alma.splashbimboombidaboum.client;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Random;


public abstract class GameLoop {
    Stage stage;
    Scene scene;
    Group root;
    Group boundsGroup;
    Group componentsGroup;

    double stageWidth;
    double stageHeight;

    private boolean rightPressed;
    private boolean leftPressed;
    private boolean spacePressed;
    private boolean downPressed;
    Character player;


    public GameLoop(Stage primaryStage) throws InterruptedException {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });

        stage = primaryStage;
        root = new Group();
        componentsGroup = new Group();
        boundsGroup = new Group();
        scene = new Scene(root, 800,744);
        initBackground();
        scene.setOnKeyPressed(onPressHandler);
        scene.setOnKeyReleased(onReleaseHandler);
        root.getChildren().add(componentsGroup);
        root.getChildren().add(boundsGroup);
        Main.player.setLiveGame(this);
        HashMap<String, Character> oldpos = new HashMap<>();
        final Boolean[] firstFrame = {true};
        new AnimationTimer(){
            public void handle(long now) {
                if(!Main.player.gameEnds && Main.player.getGamestarted()){
                    if(firstFrame[0]){
                        for(Character c : Main.player.characterMap.values()){
                            componentsGroup.getChildren().add(c.mainChar);
                        }
                        firstFrame[0] = false;
                    }
                    System.out.println(Main.player.obstacles.size());
                    for(String o : Main.player.obstacles.keySet()){
                        if(!componentsGroup.getChildren().contains(Main.player.obstacles.get(o).getComponent())){
                            componentsGroup.getChildren().add(Main.player.obstacles.get(o).getComponent());
                        }
                        if(Main.player.obstacles.get(o).getTranslateX() < -30){
                            Main.player.obstacles.remove(o);
                        }
                    }
                }
            }


        }.start();
    }

    public abstract void initBackground();

    public void display() {
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    EventHandler onPressHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            Main.player.setCurrentEvent(event);
        }
    };
    EventHandler onReleaseHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            Main.player.setCurrentEvent(null);
        }
    };
    public double getStageHeight(){
        return this.stageHeight;
    }
    public double getStageWidth(){
        return this.stageWidth;
    }
}