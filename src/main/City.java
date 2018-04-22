package main;

import com.sun.javafx.geom.Vec2f;

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
}
