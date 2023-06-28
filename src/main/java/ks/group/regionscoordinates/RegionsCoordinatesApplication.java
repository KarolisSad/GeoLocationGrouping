package ks.group.regionscoordinates;

import ks.group.regionscoordinates.FileService.FileInterface.FileReadWriteSerivce;
import ks.group.regionscoordinates.Logic.LogicInterface.RegionLogicInterface;
import ks.group.regionscoordinates.MapService.MapInterface.MapService;
import ks.group.regionscoordinates.Model.Location;
import ks.group.regionscoordinates.Model.LocationRegionRelationship;
import ks.group.regionscoordinates.Model.Region;
import ks.group.regionscoordinates.Tools.MyApiClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
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
        if (args.length == 1)
        {
            MyApiClass.setApiKey(args[0]);
            locationFileLocation = "./IO/locations.txt";
            regionFileLocation = "./IO/regions.txt";
            outputFileLocation = "./IO/result.txt";
        }
        else if(args.length == 3 || args.length == 4)
        {
            System.out.println(validateInput(args[0], args[1], args[2]));
            locationFileLocation = "./IO/locations.txt";
            regionFileLocation = "./IO/regions.txt";
            outputFileLocation = "./IO/result.txt";
            MyApiClass.setApiKey((args.length > 3) ? args[3] : "");
        }
        else {
            locationFileLocation = (args.length > 0) ? args[0] : "./IO/locations.txt";
            regionFileLocation = (args.length > 1) ? args[1] : "./IO/regions.txt";
            outputFileLocation = (args.length > 2) ? args[2] : "./IO/result.txt";
            MyApiClass.setApiKey((args.length > 3) ? args[3] : "");
        }



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

        if (!MyApiClass.API_KEY.equals(""))
        {
            System.out.println("Your map visualisation can be found: "+ imageInfo);
        }
    }

    private String validateInput(String arg1, String arg2, String arg3) {
        StringBuilder errorMessage = new StringBuilder();

        if (!fileExists(arg1)) {
            errorMessage.append("\nLocation file wasn't found - default path is set. (IO Folder)\n");
        }

        if (!fileExists(arg2)) {
            errorMessage.append("Region file wasn't found - default path is set. (IO Folder).\n");
        }

        if (!fileExists(arg3)) {
            errorMessage.append("Output file wasn't found - default path is set. (IO Folder).\n");
        }

        return errorMessage.toString();
    }

    private boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

}