package com.cat;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 300;
    private static final int ELEMENT_WIDTH = 30;
    private static final int ELEMENT_HEIGHT = 30;
    private double xSpeed = 2.5;
    private double ySpeed = 2.5;
    private Timer timer = new Timer();

    private int x = 0;
    private int y = 0;
    private GraphicsContext graphicsContext;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Ball");
        Canvas canvas = new Canvas();
        canvas.setWidth(BOARD_WIDTH);
        canvas.setHeight(BOARD_HEIGHT);
        BorderPane borderPane = new BorderPane(canvas);
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        graphicsContext = canvas.getGraphicsContext2D();
        draw();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                x += xSpeed;
                y += ySpeed;
                if (x + ELEMENT_WIDTH > graphicsContext.getCanvas().getWidth()) {
                    xSpeed = -xSpeed;
                }
                if (y + ELEMENT_HEIGHT > graphicsContext.getCanvas().getHeight()) {
                    ySpeed = -ySpeed;
                }
                if (x < 0) {
                    xSpeed = -xSpeed;
                }
                if (y < 0) {
                    ySpeed = -ySpeed;
                }
                clear();
                draw();
                scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.SPACE) {
                            timer.cancel();
                            timer.purge();
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 10, 10);
    }

    private void clear() {
        graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
    }

    private void draw() {
        graphicsContext.setLineWidth(10);
        graphicsContext.setStroke(Color.BLUE);
        graphicsContext.strokeOval(x, y, ELEMENT_WIDTH, ELEMENT_HEIGHT);
        graphicsContext.setFill(Color.GREEN);
        graphicsContext.fillOval(x, y, ELEMENT_WIDTH, ELEMENT_HEIGHT);
    }
}
