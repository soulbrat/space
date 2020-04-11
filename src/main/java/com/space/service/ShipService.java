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
    long count();

    /**
     * Update the ship with the given ID,
     * in accordance with the transferred ship
     * @param ship - the ship according to which you need to update data
     * @param id - id of the ship whose update you want
     * @return - true if the data has been updated, otherwise false
     */
    boolean update(Ship ship, long id);

    /**
     * Delete the ship with the given ID
     * @param id - id of the ship to be deleted
     * @return - true if the ship was deleted, otherwise false
     */
    boolean delete(long id);

}
