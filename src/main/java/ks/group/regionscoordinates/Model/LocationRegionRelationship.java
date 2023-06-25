package ks.group.regionscoordinates.Model;

import java.util.ArrayList;

public class LocationRegionRelationship {
    private Region region;
    private ArrayList<Location> matchedLocations;


    public LocationRegionRelationship(Region region, ArrayList<Location> matchedLocations) {
        this.region = region;
        this.matchedLocations = matchedLocations;
    }

    public ArrayList<Location> getMatchedLocations() {
        return matchedLocations;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }


    @Override
    public String toString() {
        return "LocationRegionRelationship{" +
                "region=" + region +
                ", matchedLocations=" + matchedLocations +
                '}';
    }
}


