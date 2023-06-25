package ks.group.regionscoordinates.Model;

import java.util.Arrays;

public class Location {
    private String name;
    private double[] coordinates;

    public String getName() {
        return name;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
