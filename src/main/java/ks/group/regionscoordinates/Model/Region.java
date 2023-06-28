package ks.group.regionscoordinates.Model;

import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;

public class Region {
    private String name;
    private double[][][] coordinates;

    public Region(String name, double[][][] coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public double[][][] getCoordinates() {
        return coordinates;
    }


    @Override
    public String toString() {
        return "Region{" +
                "name='" + name + '\'' +
                ", coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
