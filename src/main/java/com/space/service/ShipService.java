package com.space.service;

import com.space.model.Ship;

import java.util.List;
import java.util.Map;

public interface ShipService {

    /**
     * Create new ship
     * @param ship - ship for creation
     */
    void create(Ship ship);

    /**
     * Return the list of all ships (without filters)
     * @return ships list
     */
    List<Ship> readAll();

    /**
     * Return the list of all ships (with filters)
     * @param allParams obligatory filter parameters
     * @return ships list
     */
    List<Ship> readAll(Map<String, String> allParams);

    /**
     * Return ship by ID
     * @param id - ship ID
     * @return - ship object with given ID
     */
    Ship read(long id);

    /**
     * Check ship by ID in database
     * @param id - ship ID
     * @return - true if exist in DB, false if no
     */
    boolean isExistByID(long id);

    /**
     * Return ships count
     * @return - ships count
     */
    int count();

    /**
     * Update the ship with the given ID
     * @param id - id of the ship whose update you want
     * @param allParams NOT obligatory filter parameters
     */
    boolean update(long id, Map<String, String> allParams);

    /**
     * Delete the ship with the given ID
     * @param id - id of the ship to be deleted
     * @return - true if the ship was deleted, otherwise false
     */
    boolean delete(long id);

}
