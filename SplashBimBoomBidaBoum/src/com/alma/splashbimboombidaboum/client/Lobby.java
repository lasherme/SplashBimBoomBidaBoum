package com.alma.splashbimboombidaboum.client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.rmi.RemoteException;

public class Lobby {
    private final int  PANEL_WIDTH = 800;
    private final int PANEL_HEIGHT = 500;
    Stage stage;
    Scene StartMenu;

    public Lobby(Stage primaryStage){
            primaryStage.setResizable(false);
            Group root = new Group();
            stage = primaryStage;
            StartMenu = new Scene(root,PANEL_WIDTH,PANEL_WIDTH);
            Button start = new Button("Ready");
            start.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    //Reset game status
                    try {
                        Main.player.setIsReady();
                        new GameScene(stage).display();
                    } catch (RemoteException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            start.setTranslateX(PANEL_WIDTH/2);
            start.setTranslateY(PANEL_HEIGHT/2);
            root.getChildren().add(start);
    }
    public void display(){
        stage.setScene(StartMenu);
        stage.centerOnScreen();
    }
}

