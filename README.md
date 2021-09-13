CACHING GEOCODER SERVICE

Application provides caching proxy-service for Yandex Geocoder API.
It allows for converting address into geographic coordinates and vice-versa.

* API servers:
- url: https://localhost/geocode?addressOrCoordinates={your-response-address/coordinates}
  method: GET
  response: application/json (GeocodeResponse.class)

- url: https://localhost/actuator
  method: GET
  response: application/json (available actuator URIs)

* GeocodeResponse JSON looks as follows:
{
  "query": {
    "text": (text of your request),
    "queryType": (type of your request: COORDINATES or ADDRESS)
  },
    "coordinates": (found coordinates in form "latitude longitude"),
    "fullAddress": (found full address)
}


* How to run the application
(you will need to have jdk, maven and docker installed and added to PATH)

1) Open project root directory in terminal
2) Run "mvnw clean package -DskipTests" to compile application jar in "target" directory
3) Run "docker-compose up -d" to run the Docker containers

Exposed ports can be changed in Dockerfile and docker-compose.yml.