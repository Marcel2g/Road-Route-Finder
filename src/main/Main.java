package main;

import com.sun.javafx.geom.Vec2d;
import com.sun.javafx.geom.Vec2f;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.image.*;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class Main extends Application {

    @FXML
    private ComboBox originDropDown;
    @FXML
    private ComboBox destinationDropDown;
    @FXML
    private ComboBox waypointLocation;
    @FXML
    private ComboBox avoidedLocation;
    @FXML
    private ComboBox foundRoutes;
    @FXML
    private ImageView map;



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Road Route Finder");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    @FXML
    private void initialize(){
        ObservableList<Object> citiesList = FXCollections.observableArrayList(DataManager.instance.getCities().keySet().toArray());
        originDropDown.setItems(citiesList);
        destinationDropDown.setItems(citiesList);
        ObservableList<Object> extraList = FXCollections.observableArrayList(citiesList);
        extraList.add(0,"None");
        waypointLocation.setItems(extraList);
        avoidedLocation.setItems(extraList);
    }

    @FXML
    private void calculateRoute(){
        try{
            System.out.println("Starting at: "+originDropDown.getValue());
            System.out.println("Arriving at: "+destinationDropDown.getValue());
            City origin = DataManager.instance.getCities().get(originDropDown.getValue());
            City destination = DataManager.instance.getCities().get(destinationDropDown.getValue());
            DataManager.instance.setAllCurrentRoutes(new ArrayList<>());
            origin.findRoute(destination, new ArrayList<>());
            ObservableList<Object> currentFoundRoutes = FXCollections.observableArrayList(DataManager.instance.getAllCurrentRoutes());
            for(int i = currentFoundRoutes.size()-1; i>=0; i--){
                if(!currentFoundRoutes.get(i).toString().contains(waypointLocation.getValue().toString())
                        ||
                currentFoundRoutes.get(i).toString().contains(avoidedLocation.getValue().toString())){
                    currentFoundRoutes.remove(i);
                }
            }
            foundRoutes.setItems(currentFoundRoutes);

        }catch (Exception e){
        }

    }

    //TODO: Kevin's part.
    public void drawMap(){
        map.setImage(new Image(new File("/Users/marcelkowalik/Downloads/mapofireland.jpg").toPath().toUri().toString()));
        for(City city: DataManager.instance.getCities().values()){
            Vec2d maxRealPos = new Vec2d(54.478445, -7.401967);
            Vec2d maxPos = new Vec2d(2.3624530, 2.1022389);
            double ratioX = maxPos.x / (maxPos.x - (maxRealPos.x - city.getPosition().x));
            double ratioY = maxPos.y / (maxPos.y - (maxRealPos.y - city.getPosition().y));
            System.out.println(city.getName()+" ratio = ("+ratioX+", "+ratioY+")");
        }
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
