package main;

import com.sun.javafx.geom.Vec2f;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class City {

    private String name;
    private Vec2f position;
    private HashMap<String, City> connectedCities = new HashMap<>();  //name/city

    public City(String name, Vec2f position){
        this.name = name;
        this.position = position;
    }

    public void findRoute(City destination, ArrayList<String> visitedCities){
        if(visitedCities.contains(this.getName())){
            return;
        }

        visitedCities.add(this.getName());

        if(this.equals(destination)){
            DataManager.instance.getAllCurrentRoutes().add(visitedCities);
            System.out.println("Found route! "+ Arrays.toString(visitedCities.toArray()));
        }

        for(City connectedCity: connectedCities.values()){
            connectedCity.findRoute(destination, new ArrayList<>(visitedCities));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vec2f getPosition() {
        return position;
    }

    public void setPosition(Vec2f position) {
        this.position = position;
    }

    public HashMap<String, City> getConnectedCities() {
        return connectedCities;
    }

    public void setConnectedCities(HashMap<String, City> connectedCities) {
        this.connectedCities = connectedCities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(getName(), city.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", connectedCities=" + connectedCities.keySet().toString() +
                '}';
    }
}
