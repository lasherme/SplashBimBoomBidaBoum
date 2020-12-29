package com.alma.splashbimboombidaboum.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.alma.splashbimboombidaboum.client.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScoreBoardController implements Initializable {
	@FXML
	private VBox mainVBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	protected void handleReturnButtonAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) this.mainVBox.getScene().getWindow();
		stage.setScene(Main.player.getScene());
		stage.show();
	}
}
