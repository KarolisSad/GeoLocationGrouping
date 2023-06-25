package ks.group.regionscoordinates.SortingServices;

import ks.group.regionscoordinates.Adapters.JsonToWKT;
import ks.group.regionscoordinates.Model.Location;
import ks.group.regionscoordinates.Model.LocationRegionRelationship;
import ks.group.regionscoordinates.Model.Region;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PolygonService {

    @Autowired
    JsonToWKT jsonToWKT;

    private final GeometryFactory geometryFactory = new GeometryFactory();
    private final WKTReader wktReader = new WKTReader(geometryFactory);

    public ArrayList<LocationRegionRelationship> sortLocationsByRegions(ArrayList<Region> regions, ArrayList<Location> locations)
    {
        ArrayList<LocationRegionRelationship> results = new ArrayList<>();

        // Going through Regions
        for (int i=0; i< regions.size(); i++)
        {
            ArrayList<Location> matchedLocations = new ArrayList<>();
            ArrayList<String> regionsInWKT = jsonToWKT.regionToWKT(regions.get(i));
            // Going through all Sub-Regions
            for (int j=0; j<regionsInWKT.size(); j++)
            {
                // Getting coordinates
                for (int c=0; c<locations.size(); c++)
                {
                    if (isPointInsidePolygon(regionsInWKT.get(j),locations.get(c).getCoordinates()[0],locations.get(c).getCoordinates()[1]))
                    {
                        matchedLocations.add(locations.get(c));
                    }
                }

            }
            results.add(new LocationRegionRelationship(regions.get(i),matchedLocations));
        }
        return results;
    }

    private boolean isPointInsidePolygon(String polygonWkt, double pointX, double pointY) {
        try {
            Geometry polygonGeometry = wktReader.read(polygonWkt);
            Coordinate point = new Coordinate(pointX, pointY);
            return polygonGeometry.contains(geometryFactory.createPoint(point));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
