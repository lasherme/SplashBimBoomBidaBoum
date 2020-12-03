package com.alma.splashbimboombidaboum.client;

import com.alma.splashbimboombidaboum.utility.RandomString;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Random;

public class MainMenu {
    Stage stage;
    Scene StartMenu;
    private final int  PANEL_WIDTH = 800;
    private final int PANEL_HEIGHT = 500;

    public MainMenu(Stage primaryStage){
        primaryStage.setResizable(false);
        Group root = new Group();
        stage = primaryStage;
        StartMenu = new Scene(root,PANEL_WIDTH,PANEL_WIDTH);
        Button start = new Button("Play");
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Reset game status
                try {
                    Main.player = new Player(RandomString.generate(new Random().nextInt(5)));
                    new Lobby(stage).display();
                } catch (MalformedURLException | RemoteException | NotBoundException | AlreadyBoundException e) {

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
