package com.alma.splashbimboombidaboum.client.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import com.alma.splashbimboombidaboum.client.Main;
import com.alma.splashbimboombidaboum.client.PlayerInterface;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController implements Initializable {
	@FXML
	private TextField playerName;
	@FXML
	private VBox mainVBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			playerName.setText(Main.player.getName());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void handlePlayButtonAction(ActionEvent e) throws IOException {
		Main.player.setName(this.playerName.getText());
		Parent root = FXMLLoader.load(getClass().getResource("../resources/fxml/PlayMenu.fxml"));
		Stage stage = (Stage) this.mainVBox.getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}

	@FXML
	protected void handleExitButtonAction(ActionEvent e) {
		Platform.exit();
		System.exit(0);
	}

}
