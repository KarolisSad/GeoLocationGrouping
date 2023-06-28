package ks.group.regionscoordinates.Logic.LogicInterface;

import io.vavr.control.Either;
import ks.group.regionscoordinates.Model.Location;
import ks.group.regionscoordinates.Model.LocationRegionRelationship;
import ks.group.regionscoordinates.Model.Region;

import java.util.ArrayList;

public interface RegionLogicInterface {
Either<String,ArrayList<LocationRegionRelationship>> sortLocationsByRegions(ArrayList<Region> regions, ArrayList<Location> locations) ;
}
