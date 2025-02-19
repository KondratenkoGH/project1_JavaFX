package part_1_Arrays;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class CrazyHelloWorld extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        // main elements
        TextField textField1 = new TextField();
        Button button1 = new Button("Copy text to Field 2");
        TextField textField2 = new TextField();
        Button buttonLabel = new Button("Copy text to Label");
        Label label = new Label("Label");
        Button buttonAfterLabel = new Button("Copy text to Field 3");
        TextField textField3 = new TextField();
        Button copyToButton1 = new Button("Copy text to Field 1");

        // radiobuttons
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton enableCopy = new RadioButton("Enable Copy");
        RadioButton disableCopy = new RadioButton("Disable Copy");
        enableCopy.setToggleGroup(toggleGroup);
        disableCopy.setToggleGroup(toggleGroup);
        enableCopy.setSelected(true);

        // Method that allows or disables to copy texts
        Runnable updateButtons = () -> {
            button1.setDisable(!enableCopy.isSelected());
            buttonLabel.setDisable(!enableCopy.isSelected());
            buttonAfterLabel.setDisable(!enableCopy.isSelected());
            copyToButton1.setDisable(!enableCopy.isSelected());
        };

        // If radiobuttons are changed, move to updateButtons
        enableCopy.setOnAction(e -> updateButtons.run());
        disableCopy.setOnAction(e -> updateButtons.run());

        // If button1 pressed, copy text from textField1 to textField2 and so on
        button1.setOnAction(e -> textField2.setText(textField1.getText()));
        buttonLabel.setOnAction(e -> label.setText(textField2.getText()));
        buttonAfterLabel.setOnAction(e -> textField3.setText(label.getText()));
        copyToButton1.setOnAction(e -> textField1.setText(textField3.getText()));

        // Placing elements
        VBox layout = new VBox(10, textField1, button1, textField2, buttonLabel, label, buttonAfterLabel, textField3, copyToButton1, enableCopy, disableCopy);
        Scene scene = new Scene(layout, 400, 400);

        // run
        primaryStage.setTitle("Crazy HelloWorld");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}