package com.alma.splashbimboombidaboum.client.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.alma.splashbimboombidaboum.client.Main;
import com.alma.splashbimboombidaboum.client.PlayerInterface;
import com.alma.splashbimboombidaboum.utility.Direction;
import com.alma.splashbimboombidaboum.utility.WindowSize;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameRoomController implements Initializable {
	@FXML
	private StackPane stackPane;
	private Rectangle playerRectangle;
	private Map<PlayerInterface, Rectangle> enemiesRectangle = new HashMap<PlayerInterface, Rectangle>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		stackPane.setPrefHeight(WindowSize.height);
		stackPane.setPrefWidth(WindowSize.width);

		try {
			for (PlayerInterface enemy : Main.player.getLocalPlayers().getPlayers()) {
				Rectangle r = new Rectangle(enemy.getCoordinates().getWidth(), enemy.getCoordinates().getHeight(),
						Color.web(enemy.getColor()));
				r.setX(enemy.getCoordinates().getPositionVector().getX());
				r.setY(enemy.getCoordinates().getPositionVector().getY());
				enemiesRectangle.put(enemy, r);
				stackPane.getChildren().add(r);
			}

			playerRectangle = new Rectangle(Main.player.getCoordinates().getWidth(),
					Main.player.getCoordinates().getHeight(), Color.web(Main.player.getColor()));
			playerRectangle.setX(Main.player.getCoordinates().getPositionVector().getX());
			playerRectangle.setY(Main.player.getCoordinates().getPositionVector().getY());
			stackPane.getChildren().add(playerRectangle);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		Platform.runLater(() -> {
			stackPane.requestFocus();
		});

		this.game();
	}

	@FXML
	protected void handleOnKeyPressed(KeyEvent e) throws IOException {
		switch (e.getCode()) {
		case Q:
			if (!Main.player.getCoordinates().getDirection().contains(Direction.LEFT)) {
				Main.player.getCoordinates().addDirection(Direction.LEFT);
			}
			break;
		case D:
			if (!Main.player.getCoordinates().getDirection().contains(Direction.RIGHT)) {
				Main.player.getCoordinates().addDirection(Direction.RIGHT);
			}
			break;
		case Z:
			if (!Main.player.getCoordinates().getDirection().contains(Direction.UP)) {
				Main.player.getCoordinates().addDirection(Direction.UP);
			}
			break;
		case S:
			if (!Main.player.getCoordinates().getDirection().contains(Direction.DOWN)) {
				Main.player.getCoordinates().addDirection(Direction.DOWN);
			}
			break;
		default:
			break;
		}
	}

	@FXML
	protected void handleOnKeyReleased(KeyEvent e) throws IOException {
		switch (e.getCode()) {
		case Q:
			Main.player.getCoordinates().removeDirection(Direction.LEFT);
			break;
		case D:
			Main.player.getCoordinates().removeDirection(Direction.RIGHT);
			break;
		case Z:
			Main.player.getCoordinates().removeDirection(Direction.UP);
			break;
		case S:
			Main.player.getCoordinates().removeDirection(Direction.DOWN);
			break;
		default:
			break;
		}
	}

	private void game() {

		new Thread(() -> {
			try {
				while (Main.player.getRoom().getInGame()) {
					playerRectangle.setTranslateX(Main.player.getCoordinates().getPositionVector().getX());
					playerRectangle.setTranslateY(Main.player.getCoordinates().getPositionVector().getY());

					for (PlayerInterface enemy : this.enemiesRectangle.keySet()) {
						enemiesRectangle.get(enemy).setTranslateX(enemy.getCoordinates().getPositionVector().getX());
						enemiesRectangle.get(enemy).setTranslateY(enemy.getCoordinates().getPositionVector().getY());
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}

			Platform.runLater(() -> {
				try {
					Parent root = FXMLLoader.load(getClass().getResource("../resources/fxml/ScoreBoard.fxml"));
					Stage stage = (Stage) this.stackPane.getScene().getWindow();
					stage.setScene(new Scene(root));
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}).start();
	}
}
