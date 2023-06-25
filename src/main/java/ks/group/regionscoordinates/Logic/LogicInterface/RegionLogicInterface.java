package ks.group.regionscoordinates.Logic.LogicInterface;

import ks.group.regionscoordinates.Model.Location;
import ks.group.regionscoordinates.Model.LocationRegionRelationship;
import ks.group.regionscoordinates.Model.Region;

import java.util.ArrayList;

public interface RegionLogicInterface {
ArrayList<LocationRegionRelationship> sortLocationsByRegions(ArrayList<Region> regions, ArrayList<Location> locations) throws Exception;
}
