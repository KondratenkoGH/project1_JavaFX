package part_2_layouts;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalTime;

public class Layouts extends Application {

    private static final int SIZE = 400;
    private static final int RADIUS = SIZE / 2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // root layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);  // Centering the content
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        gridPane.setPadding(new javafx.geometry.Insets(20));  // Padding
        gridPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));  // Background color

        // Canvas for the clock
        Canvas canvas = new Canvas(SIZE, SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // HBox for holding the canvas
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(canvas);

        //  VBox for holding additional UI elements
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(10);

        // Example label
        javafx.scene.control.Label label = new javafx.scene.control.Label("Yep, this is an almost working clock");
        label.setFont(new Font(20));
        vbox.getChildren().add(label);

        // Add clock and label containers into GridPane
        gridPane.add(hbox, 0, 0);
        gridPane.add(vbox, 0, 1);

        // Timeline for updating the clock
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            drawClock(gc);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // scene and the stage
        primaryStage.setTitle("Clock with Layout Demonstrations");
        primaryStage.setScene(new Scene(gridPane, 600, 600));
        primaryStage.show();
    }

    private void drawClock(GraphicsContext gc) {
        // Get the current time
        LocalTime time = LocalTime.now();
        int hours = time.getHour() % 12;  // Use 12-hour format
        int minutes = time.getMinute();
        int seconds = time.getSecond();

        gc.clearRect(0, 0, SIZE, SIZE);

        // Apply rotation to the entire clock (rotate 90 degrees to place 12 at the top) --- NOT WORKING
        gc.save();
        gc.translate(RADIUS, RADIUS);  // origin to the center of the clock
        gc.rotate(-90);

        // Draw the clock face
        gc.setFill(Color.WHITE);
        gc.fillOval(-RADIUS, -RADIUS, SIZE, SIZE);  // Draw circle with center at origin
        gc.setFill(Color.BLACK);
        gc.fillOval(-5, -5, 10, 10);  // center dot

        drawHands(gc, hours, minutes, seconds);

        gc.restore();  // Restore the previous state of the GraphicsContext

        // Draw the numbers
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(30));
        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(360 * (i / 12.0) - 90);
            double x = RADIUS + Math.cos(angle) * (RADIUS - 40);
            double y = RADIUS + Math.sin(angle) * (RADIUS - 40);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText(String.valueOf(i), x, y);
        }
    }

    private void drawHands(GraphicsContext gc, int hours, int minutes, int seconds) {
        // Draw hour hand
        double hourAngle = Math.toRadians(360 * (hours + minutes / 60.0) / 12);
        double hourX = Math.cos(hourAngle) * (RADIUS - 60);
        double hourY = Math.sin(hourAngle) * (RADIUS - 60);
        gc.setLineWidth(8);
        gc.strokeLine(0, 0, hourX, hourY);

        // Draw minute hand
        double minuteAngle = Math.toRadians(360 * (minutes + seconds / 60.0) / 60);
        double minuteX = Math.cos(minuteAngle) * (RADIUS - 40);
        double minuteY = Math.sin(minuteAngle) * (RADIUS - 40);
        gc.setLineWidth(6);
        gc.strokeLine(0, 0, minuteX, minuteY);

        // Draw second hand
        double secondAngle = Math.toRadians(360 * (seconds / 60.0));
        double secondX = Math.cos(secondAngle) * (RADIUS - 20);
        double secondY = Math.sin(secondAngle) * (RADIUS - 20);
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeLine(0, 0, secondX, secondY);
    }
}
