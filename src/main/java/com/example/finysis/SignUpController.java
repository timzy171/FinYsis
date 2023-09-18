package com.example.finysis;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class SignUpController {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }
    @FXML
    private ResourceBundle resources;

    @FXML
    private Label message_field;

    @FXML
    private URL location;

    @FXML
    private Button back_button;

    @FXML
    private TextField login_field;

    @FXML
    private TextField name_field;

    @FXML
    private PasswordField pass_field;

    @FXML
    private Button register_button;

    @FXML
    private TextField surname_field;

    @FXML
    private Button exit_button;

    @FXML
    void initialize() {
        HelloController controller = new HelloController();
        back_button.setOnAction(actionEvent -> {
            back_button.getScene().getWindow().hide();
            HelloController.openAnotherScene("hello-view.fxml");
        });

        register_button.setOnAction(actionEvent -> {
            String name = name_field.getText();
            String surname = surname_field.getText();
            String login = login_field.getText();
            String password = pass_field.getText();

            if(name.isEmpty() || surname.isEmpty() || login.isEmpty() || password.isEmpty()){
                name_field.setStyle("-fx-text-box-border: white ;\n" +
                        "  -fx-focus-color: white ;");
                login_field.setStyle("-fx-text-box-border: white ;\n" +
                        "  -fx-focus-color: white ;");
                surname_field.setStyle("-fx-text-box-border: white ;\n" +
                        "  -fx-focus-color: white ;");
                pass_field.setStyle("-fx-text-box-border: white ;\n" +
                        "  -fx-focus-color: white ;");
                if(name.isEmpty()){
                    name_field.setText("");
                    name_field.setStyle("-fx-text-box-border: red ;\n" +
                            "  -fx-focus-color: red ;");
                }
                if(surname.isEmpty()){
                    surname_field.setText("");
                    surname_field.setStyle("-fx-text-box-border: red ;\n" +
                            "  -fx-focus-color: red ;");
                }
                if(login.isEmpty()){
                    login_field.setText("");
                    login_field.setStyle("-fx-text-box-border: red ;\n" +
                            "  -fx-focus-color: red ;");
                }
                if(Objects.equals(pass_field.getText(), "")){
                    pass_field.setText("");
                    pass_field.setStyle("-fx-text-box-border: red ;\n" +
                            "  -fx-focus-color: red ;");
                }

                message_field.setText("Empty field");
            }

            else if(!SignUpController.validate(login)){
                message_field.setText("Invalid email");
                System.out.println("yes");
                login_field.setText("");
                login_field.setStyle("-fx-text-box-border: red ;\n" +
                        "  -fx-focus-color: red ;");

            }

            else if(!name.matches("[a-zA-Z]+\\.?") || !surname.matches("[a-zA-Z]+\\.?")){
                back_button.getScene().getWindow().hide();
                HelloController.openAnotherScene("emailCheck.fxml");
            }

            else {
                register_button.getScene().getWindow().hide();
                Session session = HibernateUtil.getSessionFactory().openSession();
                try (session) {
                    addNewUser(name,surname,login,password,session);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        exit_button.setOnAction(actionEvent -> {
            exit_button.getScene().getWindow().hide();
        });
    }


    private void addNewUser(String name,String surname,String login,String password,Session session) throws IOException {
        User user = new User(name, surname, login, password);
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        UserWindowController.displayLogin(login);
        register_button.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userWindow.fxml"));
        Parent root = loader.load();
        UserWindowController userWindowController = loader.getController();
        userWindowController.displayPurchases(user.getGoodList().size());
        userWindowController.displayHelloLabel(user.getName());
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
