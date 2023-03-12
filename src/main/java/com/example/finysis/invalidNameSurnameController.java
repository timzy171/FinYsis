package com.example.finysis;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class invalidNameSurnameController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back_button;

    @FXML
    void initialize() {
        back_button.setOnAction(actionEvent -> {
            back_button.getScene().getWindow().hide();
            HelloController.openAnotherScene("signUp.fxml");
        });
    }

}
