package ks.group.regionscoordinates.Adapters;

import ks.group.regionscoordinates.Model.Region;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

// Service is responsible for translating Region coordinates to WKT format.

@Service
public class JsonToWKT {

    public ArrayList<String> regionToWKT(Region region) {
        ArrayList<String> wktList = new ArrayList<>();

        // Going through SUB-Polygons within polygon
        for (int i = 0; i < region.getCoordinates().length; i++) {
            StringBuilder wktBuilder = new StringBuilder();
            wktBuilder.append("POLYGON(");

            double[][] polygon = region.getCoordinates()[i];
            wktBuilder.append("(");
            // Going through Polygon coordinates
            for (int j = 0; j < polygon.length; j++) {
                wktBuilder.append(polygon[j][0]);
                wktBuilder.append(" ");
                wktBuilder.append(polygon[j][1]);
                wktBuilder.append(",");
            }
            wktBuilder.deleteCharAt(wktBuilder.length() - 1);
            wktBuilder.append(")");

            wktBuilder.append(")");
            wktList.add(wktBuilder.toString());
        }
        return wktList;
    }

}
