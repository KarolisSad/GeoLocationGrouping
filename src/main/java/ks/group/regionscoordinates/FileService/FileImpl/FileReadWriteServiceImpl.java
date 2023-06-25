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
        return gson.fromJson(fileContent, locationListType);
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

}