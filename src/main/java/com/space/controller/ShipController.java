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

/*
@RestController — говорит спрингу, что данный класс является REST контроллером.
Т.е. в данном классе будет реализована логика обработки клиентских запросов

Params - https://www.baeldung.com/spring-request-param
*/
@RestController
public class ShipController {

    /* Dependency Injection
    @Autowired — говорит спрингу, что в этом месте необходимо внедрить зависимость.
    В конструктор мы передаем интерфейс ShipService. Реализацию данного сервиса мы пометили аннотацией
    @Service ранее, и теперь спринг сможет передать экземпляр этой реализации в конструктор контроллера.
    */
    private ShipService shipService;

    @Autowired
    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    // get
    @GetMapping(value = "/rest/ships")
    public ResponseEntity<List<Ship>> readAll(
            @RequestParam(required = false) Map<String,String> allParams
    ) {
        ShipHelper.printMessage("DEBUG: CONTROLLER GET readAll");
        ShipHelper.printMessage("Parameters are " + allParams.entrySet());
        final List<Ship> ships = shipService.readAll(allParams);
        //
        return ships != null
                ? new ResponseEntity<>(ships, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // create
    @PostMapping(value = "/rest/ships")
    public ResponseEntity<?> create(@RequestBody Ship ship) {
        ShipHelper.printMessage("DEBUG: CONTROLLER CREATE");
        shipService.create(ship);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // count
    @GetMapping(value = "/rest/ships/count")
    public ResponseEntity<Integer> count() {
        ShipHelper.printMessage("DEBUG: CONTROLLER GET count");
        return new ResponseEntity<>(ShipHelper.count, HttpStatus.OK);
    }

    // delete
    @DeleteMapping(value = "/rest/ships/{id}")
    public ResponseEntity<?> delete(@PathVariable(required = true) long id) {
        ShipHelper.printMessage("DEBUG: CONTROLLER DELETE");
        // check ID, if false -> 400
        if (!ShipHelper.isLong(String.valueOf(id))){
            ShipHelper.printMessage("DEBUG: HttpStatus.BAD_REQUEST 400");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // check ship in DB, if not found -> 404
        if (!shipService.isExistByID(id)){
            ShipHelper.printMessage("DEBUG: HttpStatus.NOT_FOUND 404");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // delete ship
        boolean isDeleted = shipService.delete(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
