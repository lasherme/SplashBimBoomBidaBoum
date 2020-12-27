package com.alma.splashbimboombidaboum.client.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

import com.alma.splashbimboombidaboum.client.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlayMenuController implements Initializable {
	@FXML
	private VBox mainVBox;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Main.player.connection();
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	protected void handleJoinRandomButtonAction(ActionEvent e) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/fxml/WaitingRoom.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		Stage stage = (Stage) this.mainVBox.getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}

	@FXML
	protected void handleReturnButtonAction(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../resources/fxml/Main.fxml"));
		Stage stage = (Stage) this.mainVBox.getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}
}










