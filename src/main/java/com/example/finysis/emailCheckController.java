package com.example.finysis;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.EmailSender;

import javax.mail.MessagingException;

public class emailCheckController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField codeField;

    @FXML
    private Button confirmButton;

    private static String login;

    @FXML
    void initialize() throws MessagingException, IOException {
        EmailSender.sendValidationCode(login);
    }

    public static void setLogin(String email){
        login = email;
    }

}
