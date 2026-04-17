/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coursework20240016.resource;

import com.mycompany.coursework20240016.model.Sensor;
import com.mycompany.coursework20240016.model.SensorReading;
import com.mycompany.coursework20240016.storage.DataStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import com.mycompany.coursework20240016.exception.SensorUnavailableException;

/**
 *
 * @author sanda
 */
public class SensorReadingResource {
    private String sensorId;


    //  Stores readings per sensor
    private static Map<String, List<SensorReading>> readings = new HashMap<>();

    // Constructor receives sensor ID from SensorResource
    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    //  GET /api/v1/sensors/{id}/readings
    @GET
    public List<SensorReading> getReadings() {

        // Return readings for this sensor (empty list if none exist)
        return readings.getOrDefault(sensorId, new ArrayList<>());
    }

    // POST /api/v1/sensors/{id}/readings
    @POST
    public Response addReading(SensorReading reading) {

        //  Check if sensor exists
        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        //  Block if sensor is under maintenance
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor is under maintenance");
        }

        //  Add reading to this sensor's list
        readings.computeIfAbsent(sensorId, k -> new ArrayList<>())
                .add(reading);

        //  REQUIRED SIDE EFFECT:
        // Update current value of sensor
        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED)
                .entity(reading)
                .build();
    }
}
    
    

