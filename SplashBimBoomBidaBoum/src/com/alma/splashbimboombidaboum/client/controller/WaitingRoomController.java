package com.alma.splashbimboombidaboum.client.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import com.alma.splashbimboombidaboum.client.Main;
import com.alma.splashbimboombidaboum.client.PlayerInterface;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class WaitingRoomController implements Initializable {
	@FXML
	private VBox mainVBox;
	@FXML
	private HBox playerHBox;
	@FXML
	private Label roomLabel;
	@FXML
	private Label playerLabel;
	@FXML
	private Circle playerCircle;
	@FXML
	private Button playerButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Main.player.getLocalPlayers().getPlayers()
					.addListener((ListChangeListener.Change<? extends PlayerInterface> change) -> {
						while (change.next()) {
							if (change.wasAdded()) {
								change.getAddedSubList().forEach(value -> {
									addPlayer(value);
								});
							}
							if (change.wasRemoved()) {
								change.getRemoved().forEach(value -> {
									removePlayer(value);
								});
							}
						}
					});
			Main.player.getLocalPlayers().getGameStart().addListener((observable, oldValue, newValue) -> {
				if (newValue) {
					Platform.runLater(() -> {
						try {
							Parent root = FXMLLoader.load(getClass().getResource("../resources/fxml/GameRoom.fxml"));
							Stage stage = (Stage) this.mainVBox.getScene().getWindow();
							stage.setScene(new Scene(root));
							stage.show();
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
				}
			});
			Main.player.roomConnection();
			this.playerLabel.setText(Main.player.getName());
			roomLabel.setText("Room ID : " + Main.player.getRoom().getId());
		} catch (

		RemoteException e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void handleLeaveButtonAction(ActionEvent e) throws IOException {
		Main.player.getRoom().removePlayer(Main.player);
		Main.player.setRoom(null);
		Main.player.setState(false);
		Parent root = FXMLLoader.load(getClass().getResource("../resources/fxml/PlayMenu.fxml"));
		Stage stage = (Stage) this.mainVBox.getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}

	@FXML
	protected void handleStatueButtonAction(ActionEvent e) throws IOException {
		Platform.runLater(() -> {
			try {
				if (!Main.player.getState()) {
					playerCircle.setFill(Color.web("#37FF8B"));
					playerButton.setText("Cancel");
				} else {
					playerCircle.setFill(Color.web("#D72638"));
					playerButton.setText("Ready");
				}
				Main.player.setState(!Main.player.getState());
				Main.player.getRoom().changeState(Main.player, Main.player.getState());
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});
	}

	private void addPlayer(PlayerInterface player) {
		VBox vb = new VBox();
		Circle c = new Circle();

		Platform.runLater(() -> {
			this.playerHBox.getChildren().add(vb);
			try {
				vb.getChildren().add(new Label(player.getName()));
				vb.getChildren().add(c);
				c.setRadius(5);
				c.setFill(Color.web("#D72638"));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		});
	}

	private void removePlayer(PlayerInterface player) {
		Platform.runLater(() -> {
			boolean b = true;
			int i = 0;
			VBox vb = null;
			Label l;

			while (b && i < playerHBox.getChildren().size()) {
				vb = (VBox) playerHBox.getChildren().get(i);
				l = (Label) vb.getChildren().get(0);

				try {
					if (l.getText().equals(player.getName())) {
						b = !b;
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				i++;
			}
			vb.getChildren().clear();
			playerHBox.getChildren().remove(vb);
		});
	}
}
