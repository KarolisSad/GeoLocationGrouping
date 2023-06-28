package ks.group.regionscoordinates.SortingServices;

import ks.group.regionscoordinates.Model.Location;
import ks.group.regionscoordinates.Model.LocationRegionRelationship;
import ks.group.regionscoordinates.Model.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyPolygonServiceTest {

    @Autowired
    MyPolygonService underTest;

    ArrayList<Region> regionList;
    ArrayList<Location> locationsList;
    Region suqareRegion;


    @BeforeEach
    void setUp() {
        regionList = new ArrayList<>();
        locationsList = new ArrayList<>();

        suqareRegion = new Region("Square", new double[][][] {
                {
                        {25.47678240050834, 55.15818800439831},
                        {25.67678240050834, 55.15818800439831},
                        {25.67678240050834, 55.0618800439831},
                        {25.47678240050834, 55.0618800439831},
                        {25.47678240050834, 55.15818800439831}
                }
        });
    }

    @Test
    void sortLocationsByRegions_OneLocation_Inside() {
        // setup
        Location testLocation = new Location();
        testLocation.setCoordinates(new double[] {25.54678240050834, 55.12818800439831});
        regionList.add(suqareRegion);
        locationsList.add(testLocation);

        // test
        ArrayList<LocationRegionRelationship> result = underTest.sortLocationsByRegions(regionList, locationsList);

        // verify
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getMatchedLocations().size());
        assertEquals(25.54678240050834,result.get(0).getMatchedLocations().get(0).getCoordinates()[0]);
        assertEquals(55.12818800439831,result.get(0).getMatchedLocations().get(0).getCoordinates()[1]);
    }

    @Test
    void sortLocationsByRegions_OneLocation_Outside_Left() {
        // setup
        Location testLocation = new Location();
        testLocation.setCoordinates(new double[] {25.33678240050834, 55.132818800439831});
        regionList.add(suqareRegion);
        locationsList.add(testLocation);

        // test
        ArrayList<LocationRegionRelationship> result = underTest.sortLocationsByRegions(regionList, locationsList);

        // verify
        assertEquals(1, result.size());
        assertEquals(0, result.get(0).getMatchedLocations().size());
    }

    @Test
    void sortLocationsByRegions_OneLocation_Outside_Top() {
        // setup
        Location testLocation = new Location();
        testLocation.setCoordinates(new double[] {25.63678240050834, 55.032818800439831});
        regionList.add(suqareRegion);
        locationsList.add(testLocation);

        // test
        ArrayList<LocationRegionRelationship> result = underTest.sortLocationsByRegions(regionList, locationsList);

        // verify
        assertEquals(1, result.size());
        assertEquals(0, result.get(0).getMatchedLocations().size());
    }

    @Test
    void sortLocationsByRegions_OneLocation_Outside_Right() {
        // setup
        Location testLocation = new Location();
        testLocation.setCoordinates(new double[] {25.73678240050834, 55.132818800439831});
        regionList.add(suqareRegion);
        locationsList.add(testLocation);

        // test
        ArrayList<LocationRegionRelationship> result = underTest.sortLocationsByRegions(regionList, locationsList);

        // verify
        assertEquals(1, result.size());
        assertEquals(0, result.get(0).getMatchedLocations().size());
    }

    @Test
    void sortLocationsByRegions_OneLocation_Outside_Bottom() {
        // setup
        Location testLocation = new Location();
        testLocation.setCoordinates(new double[] {25.63678240050834, 55.032818800439831});
        regionList.add(suqareRegion);
        locationsList.add(testLocation);

        // test
        ArrayList<LocationRegionRelationship> result = underTest.sortLocationsByRegions(regionList, locationsList);

        // verify
        assertEquals(1, result.size());
        assertEquals(0, result.get(0).getMatchedLocations().size());
    }

    @Test
    void sortLocationsByRegions_OneLocation_OnBorder() {
        // Border corner - 25.47678240050834, 55.15818800439831
        // setup
        Location testLocation = new Location();
        testLocation.setCoordinates(new double[] {25.47678240050834, 55.15818800439831});
        regionList.add(suqareRegion);
        locationsList.add(testLocation);

        // test
        ArrayList<LocationRegionRelationship> result = underTest.sortLocationsByRegions(regionList, locationsList);

        // verify
        assertEquals(1, result.size());
        assertEquals(0, result.get(0).getMatchedLocations().size());

    }

    @Test
    void sortLocationsByRegions_OneLocation_NextToTheBorder_Inside() {
        // Border corner - 25.47678240050834, 55.15818800439831
        // setup
        Location testLocation = new Location();
        testLocation.setCoordinates(new double[] {25.47678240050835, 55.15818800439830});
        regionList.add(suqareRegion);
        locationsList.add(testLocation);

        // test
        ArrayList<LocationRegionRelationship> result = underTest.sortLocationsByRegions(regionList, locationsList);

        // verify
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getMatchedLocations().size());

    }

    @Test
    void sortLocationsByRegions_OneLocation_NextToTheBorder_Outside() {
        // Border corner - 25.47678240050834, 55.15818800439831
        // setup
        Location testLocation = new Location();
        testLocation.setCoordinates(new double[] {25.47678240050834, 55.15818800439832});
        regionList.add(suqareRegion);
        locationsList.add(testLocation);

        // test
        ArrayList<LocationRegionRelationship> result = underTest.sortLocationsByRegions(regionList, locationsList);

        // verify
        assertEquals(1, result.size());
        assertEquals(0, result.get(0).getMatchedLocations().size());
    }

    @Test
    void sortLocationsByRegions_ManyLocation_Inside() {
        // setup
        regionList.add(suqareRegion);
        Location testLocation1 = new Location();
        Location testLocation2 = new Location();
        Location testLocation3 = new Location();
        Location testLocation4 = new Location();
        testLocation1.setCoordinates(new double[] {25.54678240050834, 55.12818800439831});
        testLocation2.setCoordinates(new double[] {25.64678240050834, 55.12818800439831});
        testLocation3.setCoordinates(new double[] {25.64678240050834, 55.109818800439831});
        testLocation4.setCoordinates(new double[] {25.61678240050834, 55.109818800439831});
        locationsList.add(testLocation1);
        locationsList.add(testLocation2);
        locationsList.add(testLocation3);
        locationsList.add(testLocation4);

        // test
        ArrayList<LocationRegionRelationship> result = underTest.sortLocationsByRegions(regionList, locationsList);

        // verify
        assertEquals(1, result.size());
        assertEquals(4, result.get(0).getMatchedLocations().size());
        assertEquals(25.54678240050834,result.get(0).getMatchedLocations().get(0).getCoordinates()[0]);
        assertEquals(55.12818800439831,result.get(0).getMatchedLocations().get(0).getCoordinates()[1]);
        assertEquals(25.64678240050834,result.get(0).getMatchedLocations().get(1).getCoordinates()[0]);
        assertEquals(55.12818800439831,result.get(0).getMatchedLocations().get(1).getCoordinates()[1]);
        assertEquals(25.64678240050834,result.get(0).getMatchedLocations().get(2).getCoordinates()[0]);
        assertEquals(55.109818800439831,result.get(0).getMatchedLocations().get(2).getCoordinates()[1]);
    }

    @Test
    void sortLocationsByRegions_ManyLocation_Outside() {
        // setup
        Location testLocation1 = new Location();
        Location testLocation2 = new Location();
        Location testLocation3 = new Location();
        Location testLocation4 = new Location();
        testLocation1.setCoordinates(new double[] {25.63678240050834, 55.232818800439831});
        testLocation2.setCoordinates(new double[] {25.63678240050834, 55.032818800439831});
        testLocation3.setCoordinates(new double[] {25.33678240050834, 55.132818800439831});
        testLocation4.setCoordinates(new double[] {25.73678240050834, 55.132818800439831});
        regionList.add(suqareRegion);
        locationsList.add(testLocation1);
        locationsList.add(testLocation2);
        locationsList.add(testLocation3);
        locationsList.add(testLocation4);

        // test
        ArrayList<LocationRegionRelationship> result = underTest.sortLocationsByRegions(regionList, locationsList);

        // verify
        assertEquals(0, result.get(0).getMatchedLocations().size());
    }

    @Test
    void sortLocationsByRegions_ManyLocation_Overlapping() {
        // setup
        Location testLocation1 = new Location();
        Location testLocation2 = new Location();
        Location testLocation3 = new Location();
        Location testLocation4 = new Location();
        testLocation1.setCoordinates(new double[] {25.63678240050834, 55.232818800439831});
        testLocation2.setCoordinates(new double[] {25.63678240050834, 55.032818800439831});
        testLocation3.setCoordinates(new double[] {25.33678240050834, 55.132818800439831});
        testLocation4.setCoordinates(new double[] {25.73678240050834, 55.132818800439831});
        regionList.add(suqareRegion);
        locationsList.add(testLocation1);
        locationsList.add(testLocation2);
        locationsList.add(testLocation3);
        locationsList.add(testLocation4);

        // test
        ArrayList<LocationRegionRelationship> result = underTest.sortLocationsByRegions(regionList, locationsList);

        // verify
        assertEquals(0, result.get(0).getMatchedLocations().size());
    }


}