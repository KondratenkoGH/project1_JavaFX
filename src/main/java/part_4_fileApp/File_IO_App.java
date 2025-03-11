package part_4_fileApp;

import javafx.application.Application;
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

        // Button that prints information about everyone registered in console if file exists
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

        // --- New Code Start: Applicant Dropdown Declaration ---
        ComboBox<String> applicantDropdown = new ComboBox<>();
        applicantDropdown.setPromptText("Select Applicant");
        // --- New Code End: Applicant Dropdown Declaration ---

        // When the button is pressed, we receive info in console if there are no issues
        submitButton.setOnAction(e -> {
            boolean isValid = true;

            // Checking if nameField is not empty
            if (nameField.getText().isEmpty())
            {
                nameErrorLabel.setText("Name field cannot be empty!");
                isValid = false;
            } else nameErrorLabel.setText("");

            // Checking if surnameField is not empty
            if (surnameField.getText().isEmpty())
            {
                surnameErrorLabel.setText("Surname field cannot be empty!");
                isValid = false;
            } else surnameErrorLabel.setText("");

            // Checking if emailField is not empty
            if (emailField.getText().isEmpty())
            {
                emailErrorLabel.setText("Email field cannot be empty!");
                isValid = false;
            } else emailErrorLabel.setText("");

            // Checking if there is integer in ageField
            if (ageField.getText().matches("\\d+"))
            {
                int age = Integer.parseInt(ageField.getText());

                // Checking if age is between 15 and 100
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

            // Checking if program is chosen
            if (program.getValue() == null)
            {
                programErrorLabel.setText("Please choose the program!");
                isValid = false;
            }
            else programErrorLabel.setText("");

            // Checking if gender is chosen
            if (genderGroup.getSelectedToggle() == null)
            {
                genderErrorLabel.setText("Please choose your gender!");
                isValid = false;
            }
            else genderErrorLabel.setText("");

            // Checking if motivationArea is not empty
            if (motivationArea.getText().isEmpty())
            {
                motivationErrorLabel.setText("Name field cannot be empty!");
                isValid = false;
            } else motivationErrorLabel.setText("");

            // If no issues - sending info to console and saving to files
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

                // --- Modification Start: Remove exit and update dropdown ---
                // Platform.exit();
                loadApplicantFiles(applicantDropdown);
                // --- Modification End: Remove exit and update dropdown ---
            }
        });

        // --- New Code Start: Applicant Dropdown Event Handler ---
        applicantDropdown.setOnAction(e -> {
            String selectedFile = applicantDropdown.getValue();
            if (selectedFile != null) {
                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    String line;
                    StringBuilder motivationBuilder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("Name: ")) {
                            nameField.setText(line.substring(6));
                        } else if (line.startsWith("Surname: ")) {
                            surnameField.setText(line.substring(9));
                        } else if (line.startsWith("Email: ")) {
                            emailField.setText(line.substring(7));
                        } else if (line.startsWith("Age: ")) {
                            ageField.setText(line.substring(5));
                        } else if (line.startsWith("Program: ")) {
                            program.setValue(line.substring(9));
                        } else if (line.startsWith("Sex: ")) {
                            if (line.contains("Male"))
                                maleRadio.setSelected(true);
                            else
                                femaleRadio.setSelected(true);
                        } else if (line.startsWith("Motivation: ")) {
                            // Clear previous motivation text
                            motivationBuilder.setLength(0);
                        } else if (line.equals("//////////////////////////////")) {
                            break;
                        } else {
                            motivationBuilder.append(line).append("\n");
                        }
                    }
                    motivationArea.setText(motivationBuilder.toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // --- New Code End: Applicant Dropdown Event Handler ---

        // VBox that contains all application elements
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        // --- Modification Start: Add applicantDropdown to layout ---
        layout.getChildren().addAll(nameBox, surnameBox, emailBox, ageBox, programBox, mainGenderBox, motivationBox, submitButton, showAllButton, applicantDropdown);
        // --- Modification End: Add applicantDropdown to layout ---

        // --- New Code Start: Populate dropdown on startup ---
        loadApplicantFiles(applicantDropdown);
        // --- New Code End: Populate dropdown on startup ---

        // Creating scene (500px x 500px) with overall VBox
        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setTitle("Questionary for AUK applicant");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --- New Code Start: loadApplicantFiles method ---
    private void loadApplicantFiles(ComboBox<String> dropdown) {
        File currentDir = new File(".");
        File[] files = currentDir.listFiles((dir, name) -> name.endsWith("_form.txt"));
        if (files != null) {
            dropdown.getItems().clear();
            for (File f : files) {
                dropdown.getItems().add(f.getName());
            }
        }
    }
    // --- New Code End: loadApplicantFiles method ---
}
