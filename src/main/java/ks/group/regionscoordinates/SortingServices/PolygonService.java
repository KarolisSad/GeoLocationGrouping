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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolygonService {

    @Autowired
    JsonToWKT jsonToWKT;

    private final GeometryFactory geometryFactory = new GeometryFactory();
    private final WKTReader wktReader = new WKTReader(geometryFactory);

    public ArrayList<LocationRegionRelationship> sortLocationsByRegions(ArrayList<Region> regions, ArrayList<Location> locations) {
        return regions.stream()
                .map(region -> {
                    List<Location> matchedLocations = jsonToWKT.regionToWKT(region).stream()
                            .flatMap(polygonWkt -> locations.stream()
                                    .filter(location -> isPointInsidePolygon(polygonWkt, location.getCoordinates()[0], location.getCoordinates()[1])))
                            .toList();
                    return new LocationRegionRelationship(region, new ArrayList<>(matchedLocations));
                })
                .collect(Collectors.toCollection(ArrayList::new));
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
