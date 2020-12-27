package com.alma.splashbimboombidaboum.client;

import java.rmi.RemoteException;

import com.alma.splashbimboombidaboum.client.controller.MainController;
import com.alma.splashbimboombidaboum.utility.Address;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application implements Address {
	public static PlayerInterface player;

	{
		// System.out.println("Hello, I am the Client main !");
		try {
			player = new Player("Boomer");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./resources/fxml/Main.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		Scene scene = new Scene(root);
		primaryStage.setTitle("SplashBimBoomBidaBoum");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@Override
	public void stop() {
		Platform.exit();
		System.exit(0);
	}

}
