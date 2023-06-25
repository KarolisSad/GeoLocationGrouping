package ks.group.regionscoordinates.MapService.MapImpl;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import ks.group.regionscoordinates.MapService.MapInterface.MapService;
import ks.group.regionscoordinates.Model.Location;
import ks.group.regionscoordinates.Model.Region;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class MapBoxImpl implements MapService {

    private final String accessToken = "ssk.eyJ1Ijoia2Fyb2xpc3NhZCIsImEiOiJjbGo2NWpqNDIwNGxmM2ttdWM5N200ZmIwIn0.79_s71hVWdmrAvN0f5VQWQ";
    double centerLongitude = 24.0;
    double centerLatitude = 55.0;
    int zoomLevel = 6;
    int imageWidth = 1200;
    int imageHeight = 1200;

    @Override
    public String createLocationRegionMap(ArrayList<Region> regionList, ArrayList<Location> locationList) {

        List<Feature> features = new ArrayList<>();

        // Adding all point to the feature
        for (int i=0; i<locationList.size(); i++)
        {
            Point pointToAdd = Point.fromLngLat(locationList.get(i).getCoordinates()[0],locationList.get(i).getCoordinates()[1]);
            features.add(Feature.fromGeometry(pointToAdd));
        }

        // Going through all regions
        for (int i=0; i<regionList.size(); i++)
        {
            // Going through all sub-regions
            for (int j=0; j<regionList.get(i).getCoordinates().length; j++)
            {
                List<Point> polygonPoints = new ArrayList<>();
                // Going through coordinates
                for (int c=0; c<regionList.get(i).getCoordinates()[j].length; c++)
                {
                    polygonPoints.add(Point.fromLngLat(regionList.get(i).getCoordinates()[j][c][0],regionList.get(i).getCoordinates()[j][c][1]));
                }
                Polygon polygon = Polygon.fromLngLats(List.of(polygonPoints));
                features.add(Feature.fromGeometry(polygon));
            }
        }

        FeatureCollection featureCollection = FeatureCollection.fromFeatures(features);
        String geoJsonString = featureCollection.toJson();

        String staticImageUrl = String.format("https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/geojson(%s)/%f,%f,%d/%dx%d?access_token=%s",
                geoJsonString, centerLongitude, centerLatitude, zoomLevel, imageWidth, imageHeight, accessToken);

        try (InputStream inputStream = new URL(staticImageUrl).openStream()) {
            Path imagePath = Path.of("./IO/map_image.png");
            Files.deleteIfExists(imagePath);
            Files.copy(inputStream, imagePath);
            return "Image saved to: " + imagePath.toAbsolutePath();
        } catch (IOException e) {
            return "Invalid access token. Please enter a valid Mapbox API key.";
        }

    }

}
