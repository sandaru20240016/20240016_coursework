/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coursework20240016.storage;

import com.mycompany.coursework20240016.model.Room;
import com.mycompany.coursework20240016.model.Sensor;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sanda
 */
public class DataStore {
    public static Map<String, Room> rooms = new HashMap<>();
    public static Map<String, Sensor> sensors = new HashMap<>();
}
