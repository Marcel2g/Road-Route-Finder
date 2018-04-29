package main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

import com.sun.javafx.geom.Vec2f;
import org.json.*;

public class DataManager {

    public static DataManager instance = new DataManager();
    private HashMap<String, City> cities = new HashMap<>();
    private ArrayList<ArrayList> allCurrentRoutes = new ArrayList<>();

    private DataManager(){}

    public void loadData() throws IOException {
        HashMap<String, ArrayList<String>> citiesWithConnectingCities = new HashMap<>();
        Path path = Paths.get("data.json");
        StringBuilder jsonText = new StringBuilder();
        try(Stream<String> lines = Files.lines(path)){
            lines.forEach(jsonText::append);
        }catch (Exception e){
            System.err.println(e);
        }

        JSONObject obj = new JSONObject(jsonText.toString());
        for(Object city: obj.getJSONArray("cities")){
            //get the name of the current city
            String cityName = ((JSONObject)city).get("name").toString();

            //get the location of the current city
            Vec2f location = new Vec2f();
            location.x = Float.parseFloat(((JSONObject)((JSONObject)city).get("coordinates")).get("x").toString());
            location.y = Float.parseFloat(((JSONObject)((JSONObject)city).get("coordinates")).get("y").toString());

            //populate a local map with the current city and its connected cities
            ArrayList<String> connectingCityNames = new ArrayList<>();
            for(Object name:  ((JSONObject)city).getJSONArray("connects")){
                connectingCityNames.add(name.toString());
            }

            //create a City object from the above data and insert into the cities map
            City newCity = new City(cityName, location);
            cities.put(cityName, newCity);
            citiesWithConnectingCities.put(cityName, connectingCityNames);
        }

        for(City city: cities.values()){
            for(String connectedCityName: citiesWithConnectingCities.get(city.getName())){
                city.getConnectedCities().put(connectedCityName, cities.get(connectedCityName));
            }
        }

        for(City city: cities.values()){
            System.out.println(city.toString());
        }
    }

    public HashMap<String, City> getCities() {
        return cities;
    }

    public void setCities(HashMap<String, City> cities) {
        this.cities = cities;
    }

    public ArrayList<ArrayList> getAllCurrentRoutes() {
        return allCurrentRoutes;
    }

    public void setAllCurrentRoutes(ArrayList<ArrayList> allCurrentRoutes) {
        this.allCurrentRoutes = allCurrentRoutes;
    }
}
