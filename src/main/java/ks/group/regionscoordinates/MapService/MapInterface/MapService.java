package ks.group.regionscoordinates.MapService.MapInterface;

import ks.group.regionscoordinates.Model.Location;
import ks.group.regionscoordinates.Model.Region;

import java.util.ArrayList;

public interface MapService {
    String createLocationRegionMap(ArrayList<Region> regionList, ArrayList<Location> locationList);
}
