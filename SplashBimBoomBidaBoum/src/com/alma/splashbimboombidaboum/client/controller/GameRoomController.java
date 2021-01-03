package com.alma.splashbimboombidaboum.client.controller;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.alma.splashbimboombidaboum.client.Main;
import com.alma.splashbimboombidaboum.client.PlayerInterface;
import com.alma.splashbimboombidaboum.server.ObstacleEntityInterface;
import com.alma.splashbimboombidaboum.utility.Direction;
import com.alma.splashbimboombidaboum.utility.WindowSize;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameRoomController implements Initializable, WindowSize {
	@FXML
	private StackPane stackPane;
	private Rectangle playerRectangle;
	private Map<PlayerInterface, Rectangle> enemiesRectangle = new HashMap<PlayerInterface, Rectangle>();
	private Map<ObstacleEntityInterface, Rectangle> obstacles = new HashMap<ObstacleEntityInterface, Rectangle>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		stackPane.setPrefHeight(HEIGHT);
		stackPane.setPrefWidth(WIDTH);

		try {
			Main.player.getObservablePlayers().getObstacle()
					.addListener((ListChangeListener.Change<? extends ObstacleEntityInterface> change) -> {
						while (change.next()) {
							if (change.wasAdded()) {
								change.getAddedSubList().forEach(value -> {
									addObstacle(value);
								});
							}
						}
					});

			Main.player.getObservablePlayers().getDeads()
					.addListener((ListChangeListener.Change<? extends PlayerInterface> change) -> {
						while (change.next()) {
							if (change.wasAdded()) {
								change.getAddedSubList().forEach(value -> {
									killPlayer(value);
								});
							}
						}
					});

			for (PlayerInterface enemy : Main.player.getObservablePlayers().getPlayers()) {
				Rectangle r = new Rectangle(enemy.getPlayerEntity().getWidth(), enemy.getPlayerEntity().getHeight(),
						Color.web(enemy.getColor()));
				r.setX(enemy.getPlayerEntity().getPosition().getX());
				r.setY(enemy.getPlayerEntity().getPosition().getY());
				enemiesRectangle.put(enemy, r);
				stackPane.getChildren().add(r);
			}

			playerRectangle = new Rectangle(Main.player.getPlayerEntity().getWidth(),
					Main.player.getPlayerEntity().getHeight(), Color.web(Main.player.getColor()));
			playerRectangle.setX(Main.player.getPlayerEntity().getPosition().getX());
			playerRectangle.setY(Main.player.getPlayerEntity().getPosition().getY());
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
			if (!Main.player.getPlayerEntity().getKeyDirection().contains(Direction.LEFT)) {
				Main.player.getPlayerEntity().addKeyDirection(Direction.LEFT);
			}
			break;
		case D:
			if (!Main.player.getPlayerEntity().getKeyDirection().contains(Direction.RIGHT)) {
				Main.player.getPlayerEntity().addKeyDirection(Direction.RIGHT);
			}
			break;
		case Z:
			if (!Main.player.getPlayerEntity().getKeyDirection().contains(Direction.UP)) {
				Main.player.getPlayerEntity().addKeyDirection(Direction.UP);
			}
			break;
		case S:
			if (!Main.player.getPlayerEntity().getKeyDirection().contains(Direction.DOWN)) {
				Main.player.getPlayerEntity().addKeyDirection(Direction.DOWN);
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
			Main.player.getPlayerEntity().removeKeyDirection(Direction.LEFT);
			break;
		case D:
			Main.player.getPlayerEntity().removeKeyDirection(Direction.RIGHT);
			break;
		case Z:
			Main.player.getPlayerEntity().removeKeyDirection(Direction.UP);
			break;
		case S:
			Main.player.getPlayerEntity().removeKeyDirection(Direction.DOWN);
			break;
		default:
			break;
		}
	}

	private void game() {

		new Thread(() -> {
			try {
				while (Main.player.getRoom().getInGame()) {
					Platform.runLater(() -> {
						try {
							if (stackPane.getChildren().contains(playerRectangle)) {
								playerRectangle.setTranslateX(Main.player.getPlayerEntity().getPosition().getX());
								playerRectangle.setTranslateY(Main.player.getPlayerEntity().getPosition().getY());
							}
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					});

					for (PlayerInterface enemy : this.enemiesRectangle.keySet()) {
						Platform.runLater(() -> {

							try {
								enemiesRectangle.get(enemy).setTranslateX(enemy.getPlayerEntity().getPosition().getX());
								enemiesRectangle.get(enemy).setTranslateY(enemy.getPlayerEntity().getPosition().getY());
							} catch (RemoteException e) {
								e.printStackTrace();
							}
						});
					}

					for (ObstacleEntityInterface obstacle : this.obstacles.keySet()) {
						Platform.runLater(() -> {
							try {
								obstacles.get(obstacle).setTranslateX(obstacle.getPosition().getX());
								obstacles.get(obstacle).setTranslateY(obstacle.getPosition().getY());
							} catch (RemoteException e) {
								e.printStackTrace();
							}
						});
					}
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			try {
				Main.player.getPlayerEntity().getKeyDirection().clear();
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

	private void addObstacle(ObstacleEntityInterface obstacle) {
		try {
			Rectangle obstacleRectangle = new Rectangle(obstacle.getPosition().getX(), obstacle.getPosition().getY(),
					obstacle.getWidth(), obstacle.getHeight());
			obstacleRectangle.setFill(Color.BLACK);
			Platform.runLater(() -> {
				stackPane.getChildren().add(obstacleRectangle);
				obstacles.put(obstacle, obstacleRectangle);
			});
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void removeObstacle(ObstacleEntityInterface obstacle) {
		Platform.runLater(() -> {
			obstacles.remove(obstacle);
		});
	}

	private void killPlayer(PlayerInterface player) {
		try {
			if (Main.player.getName().equals(player.getName())) {
				Platform.runLater(() -> {
					stackPane.getChildren().remove(playerRectangle);
				});
			} else {
				Platform.runLater(() -> {
					stackPane.getChildren().remove(enemiesRectangle.get(player));
				});
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
