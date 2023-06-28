package ks.group.regionscoordinates.Model;

import java.util.Arrays;

public class Region {
    private String name;
    private double[][][] coordinates;

    public Region(String name, double[][][] coordinates) {
        System.out.println("added");
        System.out.println("name " + name);
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
