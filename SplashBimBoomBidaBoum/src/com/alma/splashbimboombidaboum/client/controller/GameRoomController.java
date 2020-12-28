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

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameRoomController implements Initializable {
	@FXML
	private Group group;
	@FXML
	private StackPane stackPane;
	private Rectangle playerRectangle;
	private Map<PlayerInterface, Rectangle> enemiesRectangle = new HashMap<PlayerInterface, Rectangle>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			playerRectangle = new Rectangle(Main.player.getCoordinates().getWidth(),
					Main.player.getCoordinates().getHeight(), Color.web(Main.player.getColor()));
			playerRectangle.setX(Main.player.getCoordinates().getX());
			playerRectangle.setY(Main.player.getCoordinates().getY());
			stackPane.getChildren().add(playerRectangle);
			for (PlayerInterface enemy : Main.player.getLocalPlayers().getPlayers()) {
				Rectangle r = new Rectangle(enemy.getCoordinates().getWidth(), enemy.getCoordinates().getHeight(),
						Color.web(enemy.getColor()));
				r.setX(enemy.getCoordinates().getX());
				r.setY(enemy.getCoordinates().getY());
				enemiesRectangle.put(enemy, r);
				stackPane.getChildren().add(r);
			}
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
			Main.player.getCoordinates().setDirection(Direction.LEFT);
			break;
		case D:
			Main.player.getCoordinates().setDirection(Direction.RIGHT);
			break;
		case Z:
			Main.player.getCoordinates().setDirection(Direction.UP);
			break;
		case S:
			Main.player.getCoordinates().setDirection(Direction.DOWN);
			break;
		default:
			break;
		}
	}

	@FXML
	protected void handleOnKeyReleased(KeyEvent e) throws IOException {
		Main.player.getCoordinates().setDirection(Direction.STAY);
	}

	private void game() {

		new Thread(() -> {
			while (true) {
				try {
					playerRectangle.setTranslateX(Main.player.getCoordinates().getX());
					playerRectangle.setTranslateY(Main.player.getCoordinates().getY());

					for (PlayerInterface enemy : this.enemiesRectangle.keySet()) {
						enemiesRectangle.get(enemy).setTranslateX(enemy.getCoordinates().getX());
						enemiesRectangle.get(enemy).setTranslateY(enemy.getCoordinates().getY());
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
