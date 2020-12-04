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
    private Stage stage;
    private Scene scene;
    private Group root;
    private Group boundsGroup;
    private Group componentsGroup;
    private int height = 800;
    private int width = 800;

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
        scene = new Scene(root, width,height);
        initBackground(scene,componentsGroup);
        scene.setOnKeyPressed(onPressHandler);
        scene.setOnKeyReleased(onReleaseHandler);
        root.getChildren().add(componentsGroup);
        root.getChildren().add(boundsGroup);

        final Boolean[] firstFrame = {true};
        new AnimationTimer(){
            public void handle(long now) {
                if(!Main.player.gameEnds && Main.player.getGamestarted()){
                    if(firstFrame[0]){
                        for(Character c : Main.player.characterMap.values()){
                            componentsGroup.getChildren().add(c.getMainChar());
                        }
                        firstFrame[0] = false;
                    }
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

    public abstract void initBackground(Scene scence, Group componentsGroup);

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
}
