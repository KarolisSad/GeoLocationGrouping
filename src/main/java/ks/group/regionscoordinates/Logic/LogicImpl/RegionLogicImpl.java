package ks.group.regionscoordinates.Logic.LogicImpl;

import ks.group.regionscoordinates.Logic.LogicInterface.RegionLogicInterface;
import ks.group.regionscoordinates.Model.Location;
import ks.group.regionscoordinates.Model.LocationRegionRelationship;
import ks.group.regionscoordinates.Model.Region;
import ks.group.regionscoordinates.SortingServices.MyPolygonService;
import ks.group.regionscoordinates.SortingServices.PolygonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;


@Service
public class RegionLogicImpl implements RegionLogicInterface {

    @Autowired
    PolygonService polygonService;
    @Autowired
    MyPolygonService myPolygonService;

    @Override
    public ArrayList<LocationRegionRelationship> sortLocationsByRegions(ArrayList<Region> regions, ArrayList<Location> locations) throws Exception {

        String errorMessage = validateData(regions,locations);
        if (!errorMessage.equals("success"))
        {
            throw new Exception(errorMessage);
        }

        return myPolygonService.sortLocationsByRegions(regions,locations);


    }

    private String validateData(ArrayList<Region> regions, ArrayList<Location> locations)
    {
        if (regions.isEmpty())
            return "Please make sure that you have at-least one polygon within region.";

        if (locations.isEmpty())
            return "Please make sure that you have at-least one point in Location file.";

        return "success";
    }

}
