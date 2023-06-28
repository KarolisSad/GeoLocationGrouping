package ks.group.regionscoordinates.Adapters;

import ks.group.regionscoordinates.Model.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JsonToWKTTest {
    private JsonToWKT jsonToWKT;

    @BeforeEach
     void setup() {
        jsonToWKT = new JsonToWKT();
    }


    @Test
    public void testRegionToWKT_ZeroRegions() {
        Region region = new Region("Empty Region", new double[0][0][0]);
        ArrayList<String> wktList = jsonToWKT.regionToWKT(region);
        assertEquals(0, wktList.size());
    }

    @Test
    public void testRegionToWKT_OnePolygon() {
        double[][][] coordinates = {
                {{10.0, 20.0}, {30.0, 40.0}, {50.0, 60.0}, {10.0, 20.0}}
        };
        Region region = new Region("One Polygon", coordinates);
        ArrayList<String> wktList = jsonToWKT.regionToWKT(region);
        assertEquals(1, wktList.size());
        assertEquals("POLYGON((10.0 20.0,30.0 40.0,50.0 60.0,10.0 20.0))", wktList.get(0));
    }

    @Test
    public void testRegionToWKT_ManyPolygons() {
        double[][][] coordinates = {
                {{10.0, 20.0}, {30.0, 40.0}, {50.0, 60.0}, {10.0, 20.0}},
                {{15.0, 25.0}, {35.0, 45.0}, {55.0, 65.0}, {15.0, 25.0}}
        };
        Region region = new Region("Multiple Polygons", coordinates);
        ArrayList<String> wktList = jsonToWKT.regionToWKT(region);
        assertEquals(2, wktList.size());
        assertEquals("POLYGON((10.0 20.0,30.0 40.0,50.0 60.0,10.0 20.0))", wktList.get(0));
        assertEquals("POLYGON((15.0 25.0,35.0 45.0,55.0 65.0,15.0 25.0))", wktList.get(1));
    }


}