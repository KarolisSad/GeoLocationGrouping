# GeoLocationGrouping


## Requirements

* Java SDK
* Apache Maven

## Instructions

To compile (also runs unit tests)

```
mvn package
```

# To run program

### Option 1
```
java -jar ./target/GeoLocationGrouping-1.0.jar <locations_txt_path> <regions_txt_path> <result_txt_path> <MapBox API KEY> 
```

### Option 2
If you prefer not to generate an image of regions and points, you can exclude the MapBox API KEY
```
java -jar ./target/GeoLocationGrouping-1.0.jar <locations_txt_path> <regions_txt_path> <result_txt_path> 
```
### Option 3

You can provide only MapBox API KEY
##### Note: Automatically uses default files in IO folder
```
java -jar ./target/GeoLocationGrouping-1.0.jar <MapBox API KEY>
```

## File structure
### Make sure to use following JSON structure

Regions.txt
```js
[
  {
    "name": "<unique identifier>",
    "coordinates": [
      [[<longitude>, <latitude>], [<longitude>, <latitude>]], 
        ... // more polygons    
    ] - array of polygons, where each polygon is an array of coordinates.
  },
  ... // more regions
]
```


Locations.txt
```js
[
  {
    "name": "<unique identifier>",
    "coordinates": [<longitude>, <latitude>]
  },
  ... // more locations
]
```

