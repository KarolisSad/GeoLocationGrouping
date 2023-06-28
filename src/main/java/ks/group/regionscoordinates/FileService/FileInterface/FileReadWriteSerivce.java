package ks.group.regionscoordinates.FileService.FileInterface;

import io.vavr.control.Either;
import ks.group.regionscoordinates.Model.Location;
import ks.group.regionscoordinates.Model.LocationRegionRelationship;
import ks.group.regionscoordinates.Model.Region;

import java.io.IOException;
import java.util.ArrayList;

public interface FileReadWriteSerivce {
    Either<String, ArrayList<Region>> readRegionFile(String fileLoc);
    Either<String, ArrayList<Location>> readLocationFile(String fileLoc);
    String writeToFile(ArrayList<LocationRegionRelationship> list, String filePath);
}
