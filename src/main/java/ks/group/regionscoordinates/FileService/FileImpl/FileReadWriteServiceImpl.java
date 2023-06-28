package ks.group.regionscoordinates.FileService.FileImpl;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import io.vavr.control.Either;
import ks.group.regionscoordinates.FileService.FileInterface.FileReadWriteSerivce;
import ks.group.regionscoordinates.Model.Location;
import ks.group.regionscoordinates.Model.LocationRegionRelationship;
import ks.group.regionscoordinates.Model.Region;
import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


@Service
public class FileReadWriteServiceImpl implements FileReadWriteSerivce {

    @Override
    public Either<String, ArrayList<Region>> readRegionFile(String fileLoc)  {
        Path filePath = Paths.get(fileLoc);
        try {
            if (Files.size(filePath) > 0) {
                String fileContent = Files.readString(filePath);
                Gson gson = new GsonBuilder().create();

                Type regionListType = new TypeToken<ArrayList<Region>>() {
                }.getType();
                ArrayList<Region> regions = gson.fromJson(fileContent, regionListType);

                String validationMsh = validateRegions(regions);
                if (!validationMsh.equals("success")) {
                    return Either.left(validationMsh);
                }
                return Either.right(regions);
            } else {
                return Either.left("Region file must contain at least one Region.");
            }
        } catch (IOException e) {
            return Either.left("Failed to read from "+ fileLoc + " file.");
        }
    }

    @Override
    public Either<String, ArrayList<Location>> readLocationFile(String fileLoc) {
        Path filePath = Paths.get(fileLoc);
        try {
            if (Files.size(filePath) > 0)
            {
                String fileContent = Files.readString(filePath);
                Gson gson = new GsonBuilder().create();

                Type locationListType = new TypeToken<ArrayList<Location>>() {}.getType();
                ArrayList<Location> locations = gson.fromJson(fileContent, locationListType);

                String validationMsh = validateLocations(locations);
                if (!validationMsh.equals("success"))
                {
                    return Either.left(validationMsh);
                }
                return Either.right(locations);
            }
            else {
                return Either.left("Region file must contain at least one location.");
            }
        } catch (IOException e) {
            return Either.left( "Failed to read from "+ fileLoc + " file.");
        }

    }


    // Currently it prints to the output file with full details not only ID's
    // The fallowing solutions would be possible
    // - to make adapter - but seems overcomplicated solution.
    // - make JSON structure that it would print as desired.
    // I would like to get advice on best approach and implement it.
    @Override
    public String writeToFile(ArrayList<LocationRegionRelationship> list, String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(list, writer);
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to write to the file.";
        }
    }



    // This approach seems to be quite expensive.
    // Other option I was considering is to use validation inside Model - but this would require throw.
    // Another approach would be possible to use JSON validation library to make structure for files.
    private String validateRegions(ArrayList<Region> regions) {
        for (Region region : regions) {
            if (region.getName() == null || region.getName().isEmpty()) {
                return "Missing region name.";
            }

            if (region.getCoordinates() == null || region.getCoordinates().length == 0) {
                return "Missing coordinates.";
            }
        }
        return "success";
    }

    private String validateLocations(ArrayList<Location> locations) {
        for (Location location : locations) {
            if (location.getName() == null || location.getName().isEmpty()) {
                return "Location must have name.";
            }

            if (location.getCoordinates() == null || location.getCoordinates().length == 0) {
                return "Location must have x and y coordinates.";
            }
        }
        return "success";
    }

}