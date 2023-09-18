package com.example.finysis;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import hibernate.HibernateUtil;
import hibernate.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;

public class SignInController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back_button;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField pass_field;

    @FXML
    private Button signin_in_signin;

    @FXML
    private Button exit_button;

    @FXML
    private Label notFound;


    @FXML
    void initialize() {
        HelloController controller = new HelloController();
        back_button.setOnAction(actionEvent -> {
            back_button.getScene().getWindow().hide();
            HelloController.openAnotherScene("hello-view.fxml");
        });

        signin_in_signin.setOnAction(actionEvent -> {
            System.out.println(login_field.getText() + pass_field.getText());
        });

        exit_button.setOnAction(actionEvent -> {
            back_button.getScene().getWindow().hide();
        });

        signin_in_signin.setOnAction(actionEvent -> {
            String login = login_field.getText();
            String password = pass_field.getText();
            Session session = HibernateUtil.getSessionFactory().openSession();

            if(SignUpController.validate(login) && !password.isEmpty()){
                try{
                    List<User> list = session.createQuery("from User").getResultList();
                    Optional<User> user = list.stream().filter(x -> x.getEmail().equals(login)).filter(x ->
                            x.getPassword().equals(password)).findAny();
                    if(user.isPresent()){
                        setInfoForFoundedUser(login,user);
                    }
                    else{
                        login_field.setText("");
                        pass_field.setText("");
                        notFound.setText("User not found");
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    session.close();
                }
            }
            else{
                if(password.isEmpty()){
                    setInfoForEmptyPassword();
                }
                else{
                    setInfoForInvalidEmail();
                }
            }
        });
    }

    private void setInfoForEmptyPassword(){
        login_field.setText("");
        login_field.setStyle("-fx-text-box-border: red ;\n" +
                "  -fx-focus-color: red ;");
        pass_field.setText("");
        pass_field.setStyle("-fx-text-box-border: red ;\n" +
                "  -fx-focus-color: red ;");
    }

    private void setInfoForInvalidEmail(){
        login_field.setText("");
        pass_field.setText("");
        login_field.setStyle("-fx-text-box-border: red ;\n" +
                "  -fx-focus-color: red ;");
        notFound.setText("Invalid email");
    }

    private void setInfoForFoundedUser(String login,Optional<User> user) throws IOException {
        UserWindowController.displayLogin(login);
        signin_in_signin.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userWindow.fxml"));
        Parent root = loader.load();
        UserWindowController userWindowController = loader.getController();
        userWindowController.displayPurchases(user.get().getGoodList().size());
        userWindowController.displayHelloLabel(user.get().getName());
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}