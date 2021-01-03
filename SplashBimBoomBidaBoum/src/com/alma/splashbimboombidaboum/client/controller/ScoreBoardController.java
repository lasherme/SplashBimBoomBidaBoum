package com.alma.splashbimboombidaboum.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import com.alma.splashbimboombidaboum.client.Main;
import com.alma.splashbimboombidaboum.client.PlayerInterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScoreBoardController implements Initializable {
	@FXML
	private VBox mainVBox;
	@FXML
	private VBox playersVBox;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Collections.reverse(Main.player.getObservablePlayers().getDeads());
			int i = 1;
			for(PlayerInterface player : Main.player.getObservablePlayers().getDeads()) {
				playersVBox.getChildren().add(new Label(i++ + ". "+ player.getName()));
			}
			Main.player.getWaitingRoom().handleStatueButtonAction(null);
			Main.player.getObservablePlayers().reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void handleReturnButtonAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) this.mainVBox.getScene().getWindow();
		stage.setScene(Main.player.getScene());
		stage.show();
	}
}
