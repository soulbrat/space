package com.space.service;

import com.space.model.Ship;

import java.util.List;
import java.util.Map;

public interface ShipService {

    /**
     * Create new ship
     * @param body for ship creation
     * @return new ship id
     */
    long create(Map<String, String> body);

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
     * Return boolean is ID valid
     * @param id - ship ID
     * @return - true if valid, false if not valid
     */
    boolean isIdValid(long id);

    /**
     * Return boolean if ship body is valid
     * @param body body for checking
     * @return - true if body is ok, false if no
     */
    boolean isBodyValid(Map<String, String> body);

    /**
     * Check ship by ID in database
     * @param id - ship ID
     * @return - true if exist in DB, false if no
     */
    boolean isExistByID(long id);


    /**
     * Return ships count
     * @param allParams NOT obligatory filter parameters
     * @return - ships count
     */
    int count(Map<String, String> allParams);

    /**
     * Update the ship with the given ID and provided body parameters
     * @param id - ship ID
     * @param body parameters for update
     */
    boolean update(long id, Map<String, String> body);

    /**
     * Delete the ship with the given ID
     * @param id - id of the ship to be deleted
     * @return - true if the ship was deleted, otherwise false
     */
    boolean delete(long id);

}
