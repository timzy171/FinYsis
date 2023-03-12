package com.example.finysis;

import java.net.URL;

import java.util.*;

import hibernate.HibernateUtil;
import hibernate.entity.Good;
import hibernate.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Session;


public class userWindowController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private static Label login;

    @FXML
    private Label myPurchases;

    @FXML
    private Label helloLabel;

    @FXML
    private TextField purchaseName;

    @FXML
    private TextField purchaseCost;

    @FXML
    ChoiceBox<String> purchaseType;

    @FXML
    private Button buy;

    @FXML
    private Label successLabel;

    @FXML
    private Button logOut_button;

    @FXML
    private Button exit_button;

    @FXML
    private Button showMostFrequentTypeButton;

    @FXML
    private Label mostFrequentTypeLabel;

    private String[] types = new String[]{"Food","Services","Medicines","Housing payment","Other"};

    public static void displayLogin(String login_field){
        login = new Label();
        login.setText(login_field);
    }

    public void displayPurchases(int count){
        myPurchases.setText("My purchases: " + String.valueOf(count));
    }

    public void displayHelloLabel(String name){
        helloLabel.setText("Hello, " + name);
    }

    private User user;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> list = session.createQuery("from User").getResultList();
        user = list.stream().filter(x -> Objects.equals(x.getEmail(), login.getText())).findAny().get();
        purchaseType.getItems().addAll(types);
        buy.setOnAction(event -> {
            session.getTransaction().begin();
            String name = purchaseName.getText();
            double cost = Double.parseDouble(purchaseCost.getText());
            String type = purchaseType.getValue();
            Good good = new Good(name,cost,type,user);user.addGoodToUser(good);
            successLabel.setText("Success !");
            displayPurchases(user.getGoodList().size());
            session.getTransaction().commit();
        });

        logOut_button.setOnAction(event -> {
            logOut_button.getScene().getWindow().hide();
            HelloController.openAnotherScene("signIn.fxml");
        });

        exit_button.setOnAction(event -> {
            exit_button.getScene().getWindow().hide();
        });

        showMostFrequentTypeButton.setOnAction(event -> {
            getMostFrequentType();
            successLabel.setText("");
        });
    }

    private void getMostFrequentType(){
        if(user.getGoodList().size() > 0){
            String result = "";
            int maxCount = 0;
            List<String> typeList = user.getGoodList().stream().map(Good::getType).toList();
            for(String str : typeList){
                if(typeList.stream().filter(str::equals).count() > maxCount){
                    result = str;
                }
            }
            mostFrequentTypeLabel.setText(result);
        }
        else{
            mostFrequentTypeLabel.setText("Nothing");
        }

    }
}


