package ks.group.regionscoordinates.SortingServices;

import ks.group.regionscoordinates.Model.Location;
import ks.group.regionscoordinates.Model.LocationRegionRelationship;
import ks.group.regionscoordinates.Model.Region;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyPolygonService {


    public ArrayList<LocationRegionRelationship> sortLocationsByRegions(ArrayList<Region> regions, ArrayList<Location> locations) {
        return regions.stream()
                .map(region -> {
                    List<Location> foundLocations = Arrays.stream(region.getCoordinates())
                            .flatMap(subRegion -> locations.stream()
                                    .filter(location -> isPointInsidePolygon(subRegion, location.getCoordinates()[0], location.getCoordinates()[1])))
                            .toList();
                    return new LocationRegionRelationship(region, new ArrayList<>(foundLocations));
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }


    private boolean isPointInsidePolygon(double[][] subRegion , double pointX, double pointY) {
        int n = subRegion.length;
        int intersectionCount = 0;

        for (int i = 0; i < n; i++) {
            double x1 = subRegion[i][0];
            double y1 = subRegion[i][1];
            double x2 = subRegion[(i + 1) % n][0];
            double y2 = subRegion[(i + 1) % n][1];

            if (isIntersecting(pointX, pointY, x1, y1, x2, y2)) {
                intersectionCount++;
            }
        }

        return intersectionCount % 2 == 1;
    }

    private boolean isIntersecting(double pointX, double pointY, double x1, double y1, double x2, double y2) {
        boolean isIntersecting = false;

        if ((y1 > pointY && y2 <= pointY) || (y2 > pointY && y1 <= pointY)) {
            double slope = (y2 - y1) / (x2 - x1);
            double intersectX = x1 + (pointY - y1) / slope;

            if (intersectX > pointX) {
                isIntersecting = true;
            }
        }

        return isIntersecting;
    }

}
