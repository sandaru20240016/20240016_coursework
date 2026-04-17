/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coursework20240016.resource;
import com.mycompany.coursework20240016.exception.RoomNotEmptyException;
import com.mycompany.coursework20240016.model.Room;
import com.mycompany.coursework20240016.storage.DataStore;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
/**
 *
 * @author sanda
 */
public class RoomResource {
        //  GET /api/v1/rooms
    @GET
    public Collection<Room> getAllRooms() {
        return DataStore.rooms.values();
    }

    //  POST /api/v1/rooms
    @POST
    public Response createRoom(Room room) {

        // Basic validation
        if (room.getId() == null || room.getId().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Room ID is required")
                    .build();
        }

        // Save room
        DataStore.rooms.put(room.getId(), room);

        return Response.status(Response.Status.CREATED)
                .entity(room)
                .build();
    }

    //  GET /api/v1/rooms/{id}
    @GET
    @Path("/{id}")
    public Response getRoomById(@PathParam("id") String id) {

        Room room = DataStore.rooms.get(id);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        return Response.ok(room).build();
    }
    
    @DELETE
@Path("/{id}")
public Response deleteRoom(@PathParam("id") String id) {

    Room room = DataStore.rooms.get(id);

    // Check if room exists
    if (room == null) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Room not found")
                .build();
    }

    // ? BUSINESS RULE: Cannot delete if sensors exist
    if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
        throw new RoomNotEmptyException("Cannot delete room: sensors are still assigned");
    }

    // Delete room
    DataStore.rooms.remove(id);

    return Response.ok("Room deleted successfully").build();
}
}
