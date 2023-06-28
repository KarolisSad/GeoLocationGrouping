package ks.group.regionscoordinates.FileService.FileImpl;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
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
public ArrayList<Region> readRegionFile(String fileLoc) throws IOException {
    Path filePath = Paths.get(fileLoc);
    if (Files.size(filePath) > 0) {
        String fileContent = Files.readString(filePath);
        Gson gson = new GsonBuilder().create();

        Type regionListType = new TypeToken<ArrayList<Region>>() {
        }.getType();
        ArrayList<Region> regions = gson.fromJson(fileContent, regionListType);

        // TODO make use of validation in system.
        String validationMsh = validateRegions(regions);
        if (!validationMsh.equals("success"))
        {
            System.out.println("ERROR FROM REGION");
            System.out.println("ERROR FROM REGION");
            System.out.println("ERROR FROM REGION");
            System.out.println("ERROR FROM REGION");
        }

        return regions;
    }
    else {
        throw new IOException("Please make sure that file contains Regions.");
    }
}

    @Override
    public ArrayList<Location> readLocationFile(String fileLoc) throws IOException {
        Path filePath = Paths.get(fileLoc);
        if (Files.size(filePath) > 0)
        {
            String fileContent = Files.readString(filePath);
            Gson gson = new GsonBuilder().create();

            Type locationListType = new TypeToken<ArrayList<Location>>() {}.getType();
            ArrayList<Location> locations = gson.fromJson(fileContent, locationListType);

            // TODO make use of validation in system.
            String validationMsh = validateLocations(locations);
            if (!validationMsh.equals("success"))
            {
                System.out.println("ERROR FROM LOCATION");
                System.out.println("ERROR FROM LOCATION");
                System.out.println("ERROR FROM LOCATION");
                System.out.println("ERROR FROM LOCATION");
            }
            return locations;
        }
        else {
            throw new IOException("Please make sure that file contains Locations.");
        }

    }


    @Override
    public String writeToFile(ArrayList<LocationRegionRelationship> list, String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");

        for (int i = 0; i < list.size(); i++) {
            LocationRegionRelationship relationship = list.get(i);
            String regionName = relationship.getRegion().getName();
            ArrayList<String> locationNames = new ArrayList<>();
            for (Location location : relationship.getMatchedLocations()) {
                locationNames.add(location.getName());
            }

            jsonBuilder.append("  {\n");
            jsonBuilder.append("    \"region\": \"").append(regionName).append("\",\n");
            jsonBuilder.append("    \"matched_locations\": ").append(gson.toJson(locationNames)).append("\n");
            jsonBuilder.append("  }");

            if (i < list.size() - 1) {
                jsonBuilder.append(",");
            }
            jsonBuilder.append("\n");
        }

        jsonBuilder.append("]");

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(jsonBuilder.toString());
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
                return "Missing location name.";
            }

            if (location.getCoordinates() == null || location.getCoordinates().length == 0) {
                return "Missing coordinates.";
            }
        }
        return "success";
    }

}