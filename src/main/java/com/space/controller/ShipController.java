package com.space.controller;

import com.space.ShipHelper;
import com.space.model.Ship;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ShipController {

    private ShipService shipService;

    @Autowired
    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    // get
    @GetMapping(value = "/rest/ships")
    public ResponseEntity<List<Ship>> readAll(@RequestParam(required = false) Map<String,String> allParams) {
        ShipHelper.printMessage("DEBUG: CONTROLLER GET readAll");
        ShipHelper.printMessage("Parameters are " + allParams.entrySet());
        final List<Ship> ships = shipService.readAll(allParams);
        return ships != null
                ? new ResponseEntity<>(ships, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // get ship by ID
    @GetMapping(value = "/rest/ships/{id}")
    public ResponseEntity<?> getShipById(@PathVariable(required = true) long id) {
        ShipHelper.printMessage("DEBUG: CONTROLLER GET SHIP BY ID: " + id);
        // check ID, if false -> 400
        if (!shipService.isIdValid(id)){
            ShipHelper.printMessage("DEBUG getShipById: HttpStatus.BAD_REQUEST 400");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // check ship in DB, if not found -> 404
        if (!shipService.isExistByID(id)){
            ShipHelper.printMessage("DEBUG getShipById: HttpStatus.NOT_FOUND 404");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // get ship
        final Ship ship = shipService.read(id);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    // create
    @PostMapping(value = "/rest/ships")
    public ResponseEntity<?> create(@RequestBody(required = true) Map<String, String> body) {
        ShipHelper.printMessage("DEBUG: CONTROLLER CREATE");
        ShipHelper.printMessage("DEBUG create | BODY: " + body.entrySet());
        // if not enough parameters or body is empty or some parameters are invalid -> 400
        if (body.isEmpty() || body.size() < 6 || !shipService.isBodyValid(body)){
            ShipHelper.printMessage("DEBUG create: HttpStatus.BAD_REQUEST 400 -> incorrect body");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            // check each parameter additionally
        } else if (!body.containsKey("name") || !body.containsKey("planet") || !body.containsKey("prodDate") || !body.containsKey("speed") || !body.containsKey("crewSize")){
            ShipHelper.printMessage("DEBUG create: HttpStatus.BAD_REQUEST 400 -> incorrect body");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        long id = shipService.create(body);
        return shipService.isExistByID(id)
                ? new ResponseEntity<>(shipService.read(id), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // update by ID
    @PostMapping(value = "/rest/ships/{id}")
    public ResponseEntity<?> update(@PathVariable(required = true) long id, @RequestBody Map<String, String> body) {
        ShipHelper.printMessage("DEBUG: CONTROLLER UPDATE");
        // check ID, if false -> 400
        if (!shipService.isIdValid(id)){
            ShipHelper.printMessage("DEBUG update: HttpStatus.BAD_REQUEST 400 -> incorrect ID");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ShipHelper.printMessage("DEBUG ID: " + id);
        // check ship in DB, if not found -> 404
        if (!shipService.isExistByID(id)){
            ShipHelper.printMessage("DEBUG update: HttpStatus.NOT_FOUND 404");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ShipHelper.printMessage("DEBUG update | BODY: " + body.entrySet());
        // if ID correct and body is empty -> return ship with code 200 without changes
        if (body.isEmpty()){
            // get with ship old parameters, recalculate rating and save | update
            Ship oldShip = shipService.read(id);
            //shipService.update(id, oldShip);
            ShipHelper.printMessage("DEBUG update: HttpStatus.OK 200 -> Empty body");
            return new ResponseEntity<>(oldShip, HttpStatus.OK);
        }
        // check new ship parameters in body
        if (!shipService.isBodyValid(body)){
            ShipHelper.printMessage("DEBUG update: HttpStatus.BAD_REQUEST 400 -> incorrect body");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // update ship with provided body parameters
        shipService.update(id, body);
        Ship ship = shipService.read(id);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    // count
    @GetMapping(value = "/rest/ships/count")
    public ResponseEntity<Integer> count(@RequestParam(required = false) Map<String,String> allParams) {
        ShipHelper.printMessage("DEBUG: CONTROLLER GET count");
        ShipHelper.printMessage("Parameters are " + allParams.entrySet());
        // need to get ships count without paging | with parameters if they are
        return new ResponseEntity<>(shipService.count(allParams), HttpStatus.OK);
    }

    // delete
    @DeleteMapping(value = "/rest/ships/{id}")
    public ResponseEntity<?> delete(@PathVariable(required = true) long id) {
        ShipHelper.printMessage("DEBUG: CONTROLLER DELETE: {ID} -> " + id);
        // check ID, if false -> 400
        if (!shipService.isIdValid(id)){
            ShipHelper.printMessage("DEBUG delete: HttpStatus.BAD_REQUEST 400");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // check ship in DB, if not found -> 404
        if (!shipService.isExistByID(id)){
            ShipHelper.printMessage("DEBUG delete: HttpStatus.NOT_FOUND 404");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // delete ship
        boolean isDeleted = shipService.delete(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
