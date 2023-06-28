package ks.group.regionscoordinates.MapService.MapImpl;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import ks.group.regionscoordinates.MapService.MapInterface.MapService;
import ks.group.regionscoordinates.Model.Location;
import ks.group.regionscoordinates.Model.Region;
import ks.group.regionscoordinates.Tools.MyApiClass;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MapBoxImpl implements MapService {

    double centerLongitude = 24.0;
    double centerLatitude = 55.0;
    int zoomLevel = 6;
    int imageWidth = 1200;
    int imageHeight = 1200;

    @Override
    public String createLocationRegionMap(ArrayList<Region> regionList, ArrayList<Location> locationList) {
        List<Feature> features = new ArrayList<>();

        // Adding all points to the features
        locationList.stream()
                .map(location -> Point.fromLngLat(location.getCoordinates()[0], location.getCoordinates()[1]))
                .map(Feature::fromGeometry)
                .forEach(features::add);

        // Going through all regions
        regionList.stream()
                .flatMap(region -> Arrays.stream(region.getCoordinates()))
                .forEach(subRegion -> {
                    List<Point> polygonPoints = new ArrayList<>();
                    // Going through coordinates
                    Arrays.stream(subRegion)
                            .map(coordinate -> Point.fromLngLat(coordinate[0], coordinate[1]))
                            .forEach(polygonPoints::add);
                    Polygon polygon = Polygon.fromLngLats(List.of(polygonPoints));
                    features.add(Feature.fromGeometry(polygon));
                });

        FeatureCollection featureCollection = FeatureCollection.fromFeatures(features);
        String geoJsonString = featureCollection.toJson();

        String staticImageUrl = String.format("https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/geojson(%s)/%f,%f,%d/%dx%d?access_token=%s",
                geoJsonString, centerLongitude, centerLatitude, zoomLevel, imageWidth, imageHeight, MyApiClass.API_KEY);

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
