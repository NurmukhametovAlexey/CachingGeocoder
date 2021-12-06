# CACHING GEOCODER SERVICE

Application provides caching proxy-service for Yandex Geocoder API. <br />
It allows for converting address into geographic coordinates and vice-versa.

###  API servers:
- url: https://localhost/geocode?addressOrCoordinates={your-response-address-or-coordinates} <br />
  method: GET <br />
  response: application/json (GeocodeResponse.class)

- url: https://localhost/actuator <br />
  method: GET <br />
  response: application/json (available actuator URIs)

###  GeocodeResponse JSON looks as follows:
{<br />
&nbsp;&nbsp;&nbsp;&nbsp;  "query":
&nbsp;&nbsp;&nbsp;&nbsp;  {<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    "text": *(text of your request)*,<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    "queryType": *(type of your request: COORDINATES or ADDRESS)*<br />
&nbsp;&nbsp;&nbsp;&nbsp;  },<br />
&nbsp;&nbsp;&nbsp;&nbsp;    "coordinates": *(found coordinates in form "latitude longitude")*,<br />
&nbsp;&nbsp;&nbsp;&nbsp;    "fullAddress": *(found full address)*<br />
}


###  How to run the application
(you will need to have jdk, maven, docker and docker-compose installed and added to PATH)

1) Open project root directory in terminal
2) On Linux run "chmod 777 mvnw" to allow execution of mvnw
3) Run "mvnw clean package -DskipTests" to compile application jar in "target" directory
4) Run "docker-compose up -d" to run the Docker containers

4.5) If you want to compile the app yourself - you will need to get your own Yandex API key and insert it in application.properties

Exposed ports can be changed in Dockerfile and docker-compose.yml
