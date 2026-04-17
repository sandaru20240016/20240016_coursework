/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coursework20240016.resource;

import com.mycompany.coursework20240016.exception.LinkedResourceNotFoundException;
import com.mycompany.coursework20240016.model.Room;
import com.mycompany.coursework20240016.model.Sensor;
import com.mycompany.coursework20240016.storage.DataStore;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author sanda
 */
@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    @POST
    public Response createSensor(Sensor sensor) {

        //  Check if room exists
        Room room = DataStore.rooms.get(sensor.getRoomId());

        if (room == null) {
            throw new LinkedResourceNotFoundException(
                "Room with ID " + sensor.getRoomId() + " does not exist"
            );
        }

        // Save sensor
        DataStore.sensors.put(sensor.getId(), sensor);

        //  Link sensor to room
        room.getSensorIds().add(sensor.getId());

        return Response.status(Response.Status.CREATED)
                .entity(sensor)
                .build();
    }
    
    @GET
public Collection<Sensor> getSensors(@QueryParam("type") String type) {

    // If no filter → return all sensors
    if (type == null || type.isEmpty()) {
        return DataStore.sensors.values();
    }

    // Filter by type
    return DataStore.sensors.values().stream()
            .filter(sensor -> sensor.getType().equalsIgnoreCase(type))
            .toList();
    
}

@Path("/{id}/readings")
public SensorReadingResource getSensorReadings(@PathParam("id") String id) {
    return new SensorReadingResource(id);
}
}