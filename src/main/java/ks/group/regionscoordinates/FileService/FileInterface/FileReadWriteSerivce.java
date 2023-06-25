package ks.group.regionscoordinates.FileService.FileInterface;

import ks.group.regionscoordinates.Model.Location;
import ks.group.regionscoordinates.Model.LocationRegionRelationship;
import ks.group.regionscoordinates.Model.Region;

import java.io.IOException;
import java.util.ArrayList;

public interface FileReadWriteSerivce {
    ArrayList<Region> readRegionFile(String fileLoc) throws IOException;
    ArrayList<Location> readLocationFile(String fileLoc) throws IOException;
    String writeToFile(ArrayList<LocationRegionRelationship> list, String filePath);
}
