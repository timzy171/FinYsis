package com.example.finysis;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signIn;

    @FXML
    private Button signUp;

    protected static void openAnotherScene(String path){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloController.class.getResource(path));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }



    @FXML
    void initialize() {
        signIn.setOnAction(actionEvent -> {
            signIn.getScene().getWindow().hide();
            openAnotherScene("signIn.fxml");

        });

        signUp.setOnAction(actionEvent -> {
            signUp.getScene().getWindow().hide();
            openAnotherScene("signUp.fxml");
        });

    }

}
