
package part_4_fileApp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

public class File_IO_App extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        // Creating fields that are matched to their error labels
        HBox nameBox = new HBox(10);
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        Label nameErrorLabel = new Label();
        nameErrorLabel.setStyle("-fx-text-fill: red;");
        nameBox.getChildren().addAll(nameField, nameErrorLabel);

        HBox surnameBox = new HBox(10);
        TextField surnameField = new TextField();
        surnameField.setPromptText("Enter your name");
        Label surnameErrorLabel = new Label();
        surnameErrorLabel.setStyle("-fx-text-fill: red;");
        surnameBox.getChildren().addAll(surnameField, surnameErrorLabel);

        HBox emailBox = new HBox(10);
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        Label emailErrorLabel = new Label();
        emailErrorLabel.setStyle("-fx-text-fill: red;");
        emailBox.getChildren().addAll(emailField, emailErrorLabel);

        HBox ageBox = new HBox(10);
        TextField ageField = new TextField();
        ageField.setPromptText("Enter your age");
        Label ageErrorLabel = new Label();
        ageErrorLabel.setStyle("-fx-text-fill: red;");
        ageBox.getChildren().addAll(ageField, ageErrorLabel);

        HBox programBox = new HBox(10);
        ComboBox<String> program = new ComboBox<>();
        program.getItems().addAll("BDS", "BSEAI");
        Label programErrorLabel = new Label();
        programErrorLabel.setStyle("-fx-text-fill: red;");
        programBox.getChildren().addAll(program, programErrorLabel);

        HBox mainGenderBox = new HBox(10);
        VBox genderSelection = new VBox(10);
        RadioButton maleRadio = new RadioButton("Male");
        RadioButton femaleRadio = new RadioButton("Female");
        genderSelection.getChildren().addAll(maleRadio, femaleRadio);
        Label genderErrorLabel = new Label();
        genderErrorLabel.setStyle("-fx-text-fill: red;");
        mainGenderBox.getChildren().addAll(genderSelection, genderErrorLabel);

        ToggleGroup genderGroup = new ToggleGroup();
        maleRadio.setToggleGroup(genderGroup);
        femaleRadio.setToggleGroup(genderGroup);

        VBox motivationBox = new VBox(10);
        Label motivationErrorLabel = new Label();
        motivationErrorLabel.setStyle("-fx-text-fill: red;");
        TextArea motivationArea = new TextArea();
        motivationArea.setPromptText("Motivational letter");
        motivationBox.getChildren().addAll(motivationArea, motivationErrorLabel);

        Button submitButton = new Button("Send");

        // Button that prints information about everyone registred in console if file exhists
        Button showAllButton = new Button("Show All Registered");
        showAllButton.setOnAction(e -> {
            File file = new File("applicant_data.txt");
            if (file.exists())
            {
                try (BufferedReader reader = new BufferedReader(new FileReader(file)))
                {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
            else
            {
                System.out.println("No data available.");
            }
        });

        // When the button is pressed, we receive info in console if there are no issues
        submitButton.setOnAction(e -> {
            boolean isValid = true;

//Checking if nameField is not empty
            if (nameField.getText().isEmpty())
            {
                nameErrorLabel.setText("Name field cannot be empty!");
                isValid = false;
            } else nameErrorLabel.setText("");

            //Checking if surnameField is not empty
            if (surnameField.getText().isEmpty())
            {
                surnameErrorLabel.setText("Surname field cannot be empty!");
                isValid = false;
            } else surnameErrorLabel.setText("");

            //Checking if emailField is not empty
            if (emailField.getText().isEmpty())
            {
                emailErrorLabel.setText("Email field cannot be empty!");
                isValid = false;
            } else emailErrorLabel.setText("");

            //Checking if there is integer in ageField
            if (ageField.getText().matches("\\d+"))
            {
                int age = Integer.parseInt(ageField.getText());

                //Checking if age is between 15 and 100
                if (age < 15 || age > 100)
                {
                    ageErrorLabel.setText("Age should be between 15 and 100!");
                    isValid = false;
                }
                else
                {
                    ageErrorLabel.setText("");
                }
            }
            else
            {
                ageErrorLabel.setText("Enter correct age!");
                isValid = false;
            }

            //Checking if program is chosen
            if (program.getValue() == null)
            {
                programErrorLabel.setText("Please choose the program!");
                isValid = false;
            }
            else programErrorLabel.setText("");

            //Checking if gender is chosen
            if (genderGroup.getSelectedToggle() == null)
            {
                genderErrorLabel.setText("Please choose your gender!");
                isValid = false;
            }
            else genderErrorLabel.setText("");

            //Checking if motivationArea is not empty
            if (motivationArea.getText().isEmpty())
            {
                motivationErrorLabel.setText("Name field cannot be empty!");
                isValid = false;
            } else motivationErrorLabel.setText("");

            //If no issues - sending info to terminal
            if (isValid)
            {
                String report = "Name: " + nameField.getText() + "\n" +
                        "Surname: " + surnameField.getText() + "\n" +
                        "Email: " + emailField.getText() + "\n" +
                        "Age: " + ageField.getText() + "\n" +
                        "Program: " + program.getValue() + "\n" +
                        "Sex: " + (maleRadio.isSelected() ? "Male" : "Female") + "\n" +
                        "Motivation: " + "\n" + motivationArea.getText();
                System.out.println(report);

                try
                {
                    String fileName = surnameField.getText() + "_" + nameField.getText() + "_form.txt";
                    File file = new File(fileName);
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true)))
                    {
                        writer.write(report);
                        writer.newLine();
                        writer.write("//////////////////////////////");
                        writer.newLine();
                        System.out.println("\nData saved to file: " + fileName);
                    }
                } catch (IOException ex)
                {
                    ex.printStackTrace();
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter("applicant_data.txt", true)))
                {
                    writer.write(report);
                    writer.newLine();
                    writer.write("//////////////////////////////");
                    writer.newLine();
                } catch (IOException ex)
                {
                    ex.printStackTrace();
                }

                Platform.exit();
            }
        });

        // VBox that contains all application
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(nameBox, surnameBox, emailBox, ageBox, programBox, mainGenderBox, motivationBox, submitButton, showAllButton);

        // Creating scene (400px/400px) with overall VBox
        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setTitle("Questionary for AUK applicant");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}