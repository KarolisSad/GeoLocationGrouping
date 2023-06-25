package ks.group.regionscoordinates;

import ks.group.regionscoordinates.FileService.FileInterface.FileReadWriteSerivce;
import ks.group.regionscoordinates.Logic.LogicInterface.RegionLogicInterface;
import ks.group.regionscoordinates.MapService.MapInterface.MapService;
import ks.group.regionscoordinates.Model.Location;
import ks.group.regionscoordinates.Model.LocationRegionRelationship;
import ks.group.regionscoordinates.Model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootApplication
public class RegionsCoordinatesApplication implements CommandLineRunner {

    @Autowired
    FileReadWriteSerivce fileService;
    @Autowired
    RegionLogicInterface regionLogic;
    @Autowired
    MapService mapService;

    public static void main(String[] args) {
        SpringApplication.run(RegionsCoordinatesApplication.class, args);
    }
    private String locationFileLocation;
    private String regionFileLocation;
    private String outputFileLocation;
    private ArrayList<Location> locations;
    ArrayList<Region> regions;


    @Override
    public void run(String... args) {
        locationFileLocation = (args.length > 0) ? args[0] : "./IO/locations.txt";
        regionFileLocation = (args.length > 1) ? args[1] : "./IO/regions.txt";
        outputFileLocation = (args.length > 2) ? args[2] : "./IO/result.txt";

        // Reading Files
        try {
        this.locations= fileService.readLocationFile(locationFileLocation);
        this.regions = fileService.readRegionFile(regionFileLocation);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        // Main logic
        ArrayList<LocationRegionRelationship> result = null;
        try {
            result = regionLogic.sortLocationsByRegions(regions,locations);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        // Writing to file
        String fileMessage = fileService.writeToFile(result,outputFileLocation);
        if (fileMessage.equals("success"))
        {
            fileMessage = "Data has been successfully written to: " + outputFileLocation;
        }

        // Creating MAP
        String imageInfo = mapService.createLocationRegionMap(regions,locations);

        // Output
        System.out.println("\n\nAll Region-Location matches were found!");
        System.out.println(fileMessage);
        System.out.println("Your map visualisation can be found: "+ imageInfo);

    }
}