package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Observable;

public class Main extends Application {

    @FXML
    private ComboBox originDropDown;
    @FXML
    private ComboBox destinationDropDown;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    @FXML
    private void initialize(){
        ObservableList<Object> citiesList = FXCollections.observableArrayList(DataManager.instance.getCities().keySet().toArray());
        originDropDown.setItems(citiesList);
        destinationDropDown.setItems(citiesList);
    }


    public static void main(String[] args) {
        try {
            DataManager.instance.loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        launch(args);
    }
}
