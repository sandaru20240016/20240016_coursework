# 20240016_coursework
client-server architecture coursework


## API Overview

This project implements a RESTful API for managing a smart campus environment. The system is built around three main resources.

Rooms represent physical locations on campus.
Sensors are devices assigned to rooms that collect environmental data.
Sensor readings store historical measurements recorded by sensors.

The API follows REST principles with clear and structured endpoints. Sensor readings are implemented using a sub-resource pattern, where readings are accessed through a specific sensor using the path:

/api/v1/sensors/{sensorId}/readings

The system enforces important data integrity rules. A sensor cannot be created unless the specified room exists. A room cannot be deleted if it still has sensors assigned to it.

Filtering is supported through query parameters. For example, sensors can be filtered by type using:

/api/v1/sensors?type=CO2

The API also uses consistent error handling with appropriate HTTP status codes such as 409, 422, and 403.

---

## Build and Run Instructions

Step 1: Clone the repository
git clone <your-repo-url>
cd <your-project-folder>

Step 2: Build the project
mvn clean install

Step 3: Run the server
mvn exec:java

Step 4: Access the API

Base URL:
http://localhost:8080/api/v1

---

## Sample curl Commands

Create a room
curl -X POST http://localhost:8080/api/v1/rooms -H "Content-Type: application/json" -d "{ "id": "ENG-101", "name": "Engineering Lab", "capacity": 40 }"

Get all rooms
curl http://localhost:8080/api/v1/rooms

Create a sensor
curl -X POST http://localhost:8080/api/v1/sensors -H "Content-Type: application/json" -d "{ "id": "TEMP-001", "type": "Temperature", "status": "ACTIVE", "currentValue": 22.5, "roomId": "ENG-101" }"

Add a sensor reading
curl -X POST http://localhost:8080/api/v1/sensors/TEMP-001/readings -H "Content-Type: application/json" -d "{ "id": "r1", "timestamp": 1710000000000, "value": 25.5 }"

Get sensor readings
curl http://localhost:8080/api/v1/sensors/TEMP-001/readings

Filter sensors by type
curl "http://localhost:8080/api/v1/sensors?type=Temperature"

Attempt to delete a room that still has sensors
curl -X DELETE http://localhost:8080/api/v1/rooms/ENG-101

---

## Error Handling

The API uses standard HTTP status codes to indicate errors.

404 is returned when a resource cannot be found.
409 is returned when attempting to delete a room that still contains sensors.
422 is returned when a sensor is created with a room that does not exist.
403 is returned when a sensor is in maintenance mode and cannot accept readings.
500 is returned for unexpected server errors.

---

## Notes

This system uses an in-memory data store, so all data will be lost when the server restarts.
The project is designed to demonstrate RESTful API principles and is not intended for production use.


