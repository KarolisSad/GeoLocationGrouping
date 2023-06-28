package ks.group.regionscoordinates.Model;

import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;

public class Region {
    private String name;
    private double[][][] coordinates;
    private ArrayList<Coordinate[]> subRegionCoordinates = new ArrayList<>();

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

    // Inside Array we have "flat" list of the SUB-Region coordinates
    public ArrayList<Coordinate[]> getSubRegionsCoordinates() {
        subRegionCoordinates = new ArrayList<>();

        for (int i = 0; i < coordinates.length; i++) {
            Coordinate[] coordinatesToAdd = new Coordinate[coordinates[i].length];
            for (int j = 0; j < coordinates[i].length; j++) {
                double latitude = coordinates[i][j][0];
                double longitude = coordinates[i][j][1];
                Coordinate coordinate = new Coordinate(latitude, longitude);
                coordinatesToAdd[j] = coordinate;
            }
            subRegionCoordinates.add(coordinatesToAdd);
        }
        return subRegionCoordinates;
    }

    @Override
    public String toString() {
        return "Region{" +
                "name='" + name + '\'' +
                ", coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
