package com.alma.splashbimboombidaboum.client;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;


public class GameScene extends GameLoop {

    public GameScene(Stage primaryStage) throws InterruptedException {
        super(primaryStage);
        super.stageWidth = 800;
        super.stageHeight = 800;
    }


    @Override
    public void initBackground() {
        Rectangle background = new Rectangle(scene.getWidth(),scene.getHeight());
        background.setFill(Color.web("#f49b00"));
        background.widthProperty().bind(scene.widthProperty());
        background.heightProperty().bind(scene.heightProperty());
        componentsGroup.getChildren().add(background);
        Rectangle floor = new Rectangle(scene.getWidth(),scene.getHeight() - scene.getHeight()/10);
        floor.setFill(Color.web("brown"));
        componentsGroup.getChildren().add(floor);
    }
}
