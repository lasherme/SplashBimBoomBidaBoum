package com.alma.splashbimboombidaboum.client;

import com.alma.splashbimboombidaboum.utility.RandomString;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Main extends Application{
	protected static Player player;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		primaryStage.setTitle("MyGame");
		new MainMenu(primaryStage).display();
		primaryStage.show();
		System.out.println("Hello, I am the Client main !");
	}
}
